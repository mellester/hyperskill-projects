package antifraud.http.controller;

import antifraud.AntiFraudApplication;
import antifraud.http.model.Card;
import antifraud.http.model.CardLimit;
import antifraud.http.model.Transaction;
import antifraud.http.requests.PutTransactionRequest;
import antifraud.http.requests.TransactionRequest;
import antifraud.http.response.PutTransactionResponse;
import antifraud.http.response.TransactionResponse;
import antifraud.repository.CardLimitRepository;
import antifraud.repository.CardRepository;
import antifraud.repository.IpRepository;
import antifraud.repository.TransactionRepository;
import antifraud.util.enums.Feedback;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Validated
@RequestMapping("api/antifraud")
public class TransactionSupportController {

    @Autowired
    private IpRepository ipRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardLimitRepository cardLimitRepository;
    @Autowired
    private TransactionRepository transRepository;

    @PutMapping("/transaction")
    public Transaction transaction(@Valid @RequestBody Transaction newTransaction) {
        var oldTransaction = transRepository.findById(newTransaction.getTransactionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")
        );

          if (!Objects.equals(oldTransaction.getFeedback(), null)) {
              throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflict" + oldTransaction.toString() + newTransaction.toString());
          }
        if (oldTransaction.getResult() == newTransaction.getFeedback()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "");
        }
        oldTransaction.setFeedback(newTransaction.getFeedback());
        transRepository.save(oldTransaction);
        long value = oldTransaction.getAmount();
        var card = cardLimitRepository.findByNumber(oldTransaction.getNumber()).orElse(new CardLimit(oldTransaction.getNumber()));


        switch (oldTransaction.getFeedback()) {
            case ALLOWED -> {
                card.increaseAllowed(value);
                if (oldTransaction.getResult() == Feedback.PROHIBITED) {
                    card.increaseManual(value);
                }
            }
            case MANUAL_PROCESSING -> {
                if (oldTransaction.getResult() == Feedback.PROHIBITED) {
                    card.increaseManual(value);
                } else {
                    card.decreaseAllowed(value);
                }
            }
            case PROHIBITED -> {
                card.decreaseManual(value);
                if (oldTransaction.getResult() == Feedback.ALLOWED) {
                    card.decreaseAllowed(value);
                }
            }
            default -> {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"");
            }
        }
        cardLimitRepository.save(card);
        return oldTransaction;
    }

    @GetMapping("/history")
    public Iterable<Transaction> transactionHistory() {
        return transRepository.findAll();

    }

    @GetMapping("/history/{number}")
    public Iterable<Transaction> transactionHistory(@PathVariable String number) {
        if (!Card.checkIfValid(number)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not a number");
        }
        var list =  transRepository.findByNumber(number);
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return list;

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
@ExceptionHandler(MethodArgumentNotValidException.class)
public Map<String, String> handleValidationExceptions(
    MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;

}
}
