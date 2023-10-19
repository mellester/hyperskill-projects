package antifraud.http.response;

import antifraud.util.enums.Feedback;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public class TransactionResponse {
    public Feedback result;
    public String info;

    public TransactionResponse(Feedback str, String info) {
        result = str;
        this.info = info;
    }
}
