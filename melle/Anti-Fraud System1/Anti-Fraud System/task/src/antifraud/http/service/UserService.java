package antifraud.http.service;

import antifraud.http.model.AppUser;
import antifraud.http.requests.roleRequest;
import antifraud.http.response.DeleteUserResponse;
import antifraud.repository.AppUserRepository;
import antifraud.util.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser register(AppUser user) {
        var oldUser = userRepository.findAppUserByUsername(user.getUsername());
        if (oldUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }


        if (userRepository.count() == 0) {
            user.setRole(UserRole.ADMINISTRATOR);
            user.setIsAccountLocked(false);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername());

        return userRepository.save(user);
    }

    public DeleteUserResponse delete(String username) {
        AppUser user = findUser(username);

        userRepository.delete(user);

        return new DeleteUserResponse(username);
    }

    public List<AppUser> findAllUsers() {
        System.out.println("UserService.findAllUsers");
        System.err.println("hi");
        return userRepository.findAll();
    }

    public AppUser findUser(String username) {
        return userRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void update(AppUser user) {
        userRepository.save(user);
    }

    public AppUser changeRole(roleRequest changeUserRoleRequest) {
        var user = findUser(changeUserRoleRequest.getUsername());
        if (user.getRole() == changeUserRoleRequest.getRole()) {
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Already has the role");
        }
        user.setRole(changeUserRoleRequest.getRole());
        userRepository.save(user);
        return user;

    }
}
