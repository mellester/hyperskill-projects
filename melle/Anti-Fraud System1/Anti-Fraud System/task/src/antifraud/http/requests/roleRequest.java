package antifraud.http.requests;

import antifraud.util.enums.UserRole;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
public class roleRequest {
    @NotEmpty
    private String username;

    private UserRole role;

}
