package antifraud.http.controller;

import antifraud.AntiFraudApplication;
import antifraud.http.model.CardLimit;
import antifraud.http.model.Transaction;
import antifraud.http.requests.TransactionRequest;
import antifraud.repository.CardLimitRepository;
import antifraud.repository.CardRepository;
import antifraud.repository.IpRepository;
import antifraud.repository.TransactionRepository;
import antifraud.util.enums.Feedback;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import antifraud.http.response.TransactionResponse;

import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@Validated
@RequestMapping("api/antifraud")
public class TransactionController {

    @Autowired
    private IpRepository ipRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardLimitRepository cardLimitRepository;

    @Autowired
    private TransactionRepository transRepository;

    @PostMapping("/transaction")
    public TransactionResponse transaction(@Valid @RequestBody TransactionRequest request) {
        var t = new Transaction(request);
        var response = validateRequest(request, t);
        t.setResult(response.result);
        transRepository.save(t);
        return response;
    }

    protected TransactionResponse validateRequest(TransactionRequest request, Transaction transaction) {
        ArrayList<String> fatalErrors = new ArrayList<String>(3);
        ArrayList<String> manuaulErrors = new ArrayList<String>(3);

        checkUniqueIpsExcludingGivenIpInLastHour(transaction, fatalErrors, manuaulErrors);
        checkcountUniqueRegionExcludingGivenCardNumberInLastHour(transaction, fatalErrors, manuaulErrors);

        var opt = cardLimitRepository.findByNumber(request.getNumber());
        AntiFraudApplication.logger.trace("asfasdf");

        long limitManual = 1500L;
        long limitAllowed = 200L;
        if (opt.isPresent()) {
            limitManual = opt.get().getNumberManual();
            limitAllowed = opt.get().getNumberAllowed();
            AntiFraudApplication.logger.trace(opt.toString());
        }
        else {
            AntiFraudApplication.logger.trace(transaction.toString());

        }

        if (request.getAmount() > limitManual)
            fatalErrors.add("amount");
        if (cardRepository.findByNumber(request.getNumber()).isPresent())
            fatalErrors.add("card-number");
        if (ipRepository.findSusIPByIp(request.getIp()).isPresent())
            fatalErrors.add("ip");
        if (request.getAmount() > limitAllowed)
            manuaulErrors.add("amount");

        String fatalErrorsMsg = join(fatalErrors);
        String manuaulErrorsMsg = join(manuaulErrors);
        if (!fatalErrors.isEmpty())
            return new TransactionResponse(Feedback.PROHIBITED, fatalErrorsMsg);
        if (!manuaulErrors.isEmpty())
            return new TransactionResponse(Feedback.MANUAL_PROCESSING, manuaulErrorsMsg);

        return new TransactionResponse(Feedback.ALLOWED, "none");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                "Access denied message here", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private void checkUniqueIpsExcludingGivenIpInLastHour(Transaction t, ArrayList<String> fatalErrors,
                                                          ArrayList<String> manuaulErrors) {
        Date timestampOneHourAgo = getDateOneHourAgo(t.getDate());

        long count = transRepository.countUniqueIpsExcludingGivenIpInLastHour(t.getIp(), timestampOneHourAgo, t.getDate() , t.getNumber());
        if (count > 2) {
            fatalErrors.add("ip-correlation");
        } else if (count > 1) {
            manuaulErrors.add("ip-correlation");
        }

    }

    private void checkcountUniqueRegionExcludingGivenCardNumberInLastHour(Transaction t, ArrayList<String> fatalErrors,
                                                                          ArrayList<String> manualErrors) {
        Date timestampOneHourAgo = getDateOneHourAgo(t.getDate());
        long count = transRepository.countUniqueRegionExcludingGivenCardNumberInLastHour(t.getRegion(),  timestampOneHourAgo, t.getDate(), t.getNumber());
        if (count > 2) {
            fatalErrors.add("region-correlation");
        } else if (count > 1) {
            manualErrors.add("region-correlation");
        }

    }

    // Date a hour ago
    private static Date getDateOneHourAgo(Date timestamp) {
        return new Date(timestamp.getTime() - 3600 * 1000);
    }

    // Join a arralist of strings
    private static String join(ArrayList<String> arr) {
        arr.sort(String::compareTo);
        String[] a = arr.toArray(String[]::new);
        return String.join(", ", a);
    }

}
