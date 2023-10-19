package antifraud.http.requests;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

@Validated
public record CreateUserRequest( @NotEmpty String username, @NotEmpty String password, @NotEmpty String name) {

}
