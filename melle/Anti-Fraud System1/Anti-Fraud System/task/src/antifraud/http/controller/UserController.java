package antifraud.http.controller;



import antifraud.http.View;
import antifraud.http.model.AppUser;
import antifraud.http.requests.CreateUserRequest;
import antifraud.http.requests.accessRequest;
import antifraud.http.requests.roleRequest;
import antifraud.http.response.*;
import antifraud.http.service.UserService;
import antifraud.util.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/auth")
@Validated
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreatedResponse register(@Valid @RequestBody CreateUserRequest userRequest) {
        var appUser = new AppUser(userRequest.name(),userRequest.password(),userRequest.username());
        return new UserCreatedResponse(userService.register(appUser));
    }

//    @GetMapping("/list")
//    @ResponseStatus(HttpStatus.OK)
////    @JsonView(View.UserView.class)
//    public ListUsersResponse aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa() {
//        return new ListUsersResponse(userService.findAllUsers());
//    }

    @DeleteMapping("/user/{username}")
    public DeleteUserResponse delete(@NotBlank @PathVariable String username) {

        userService.findUser(username);
            // Unreachable


         return userService.delete(username);

    }

    @PutMapping("/access")

    public accesResponse access(@RequestBody accessRequest req ) {

        var user = userService.findUser(req.getUsername());
        final String msg = switch (req.getOperation()) {
            case "LOCK" -> {
                if (user.getRole() == UserRole.ADMINISTRATOR) {
                    throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant lock ADMINISTRATOR");
                }
                user.setEnabled(false);
                yield "Locked";
            }
            case "UNLOCK" -> {
                user.setEnabled(true);
                yield "Unlocked";
            }
            default -> throw new RuntimeException("Valdation failed");
        };
        userService.update(user);
        return new accesResponse("User " + req.getUsername() + " " + msg + "!");

    }


    @PutMapping("/role")
    @JsonView(View.UserView.class)
    public AppUser changeRole(@Valid @RequestBody roleRequest changeUserRoleRequest) {
        if (changeUserRoleRequest.getRole() == UserRole.ADMINISTRATOR) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cant set another ADMINISTRATOR");
        }
        return userService.changeRole(changeUserRoleRequest);
    }

//    @PutMapping("/access")
//    public ChangeUserStatusResponse changeStatus(@Valid @RequestBody ChangeUserStatusRequest changeUserStatusRequest) {
//        return userService.changeStatus(changeUserStatusRequest);
//    }

//    @ExceptionHandler({ResponseStatusException.class})
//    public ResponseEntity<Object>  handleResponseNotFound() {
//        return new ResponseEntity<>(
//                "Access denied message here111111", new HttpHeaders(), HttpStatus.CONFLICT);
//    }

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Object>  handleResponseStatusException(ResponseStatusException e) {
        return new ResponseEntity<>(
                "Access denied message here111111", new HttpHeaders(), e.getStatusCode());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object>  handleException(Exception e) {
        return new ResponseEntity<>(
                "Access denied message here:\n" + e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}