package antifraud.http.requests;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

@Validated
public class TransactionRequest {
    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public TransactionRequest() {
    }
    @Min(1)
    private long amount;
}
