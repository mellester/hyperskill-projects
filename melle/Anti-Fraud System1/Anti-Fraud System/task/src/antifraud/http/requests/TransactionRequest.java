package antifraud.http.requests;

import antifraud.http.model.Card;
import antifraud.http.model.SusIP;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Validated
@Getter
@Setter
public class TransactionRequest {
    public TransactionRequest() {
    }


    @Min(1)
    private long amount;

    @Pattern(regexp = SusIP.regex)
    private String ip;
    private String number;
    private String region;
    private Date date;


    @AssertTrue
    public boolean checkIfValid() {
        return SusIP.checkIp(ip) && Card.checkIfValid(number);
    }
}
