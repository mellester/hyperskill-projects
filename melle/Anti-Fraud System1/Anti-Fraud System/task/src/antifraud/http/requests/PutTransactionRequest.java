package antifraud.http.requests;

import antifraud.util.enums.Feedback;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;



@Validated
@Getter
@Setter
public class PutTransactionRequest {
       Long transactionId;

        Feedback feedback;
}
