package antifraud.http.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
public class accessRequest {
    @NotEmpty
    private String username;

    @Pattern(regexp = "LOCK|UNLOCK")
    private String operation;
}
