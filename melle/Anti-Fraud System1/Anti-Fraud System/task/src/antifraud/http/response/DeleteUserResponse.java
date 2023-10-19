package antifraud.http.response;

import lombok.Getter;

@Getter
public class DeleteUserResponse {
    private final String username;
    private final String status =  "Deleted successfully!";
    public DeleteUserResponse(String username) {
        this.username = username;
    }
}
