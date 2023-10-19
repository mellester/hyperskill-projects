package antifraud.http.controller;

import antifraud.http.View;
import antifraud.http.model.AppUser;
import antifraud.http.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class ListController {

    private final UserService userService;

    @Autowired
    public ListController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.UserView.class)
    public List<AppUser> listUsers() {
        return userService.findAllUsers();
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception e) {
        return new ResponseEntity<>(
                "Access denied message here:\n" + e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
