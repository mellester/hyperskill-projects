package antifraud.http.response;

import antifraud.http.model.AppUser;
import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
@Getter
public class ListUsersResponse extends HttpEntity<String> {
    private final List<AppUser> allUsers;

    public ListUsersResponse(List<AppUser> allUsers) {
        super(" ");
        this.allUsers = allUsers;
    }
}
