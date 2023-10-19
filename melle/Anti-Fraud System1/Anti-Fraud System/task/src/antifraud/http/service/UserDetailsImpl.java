package antifraud.http.service;

import antifraud.http.model.AppUser;
import antifraud.util.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    private final String username;
    private final String password;
    private boolean  accountNonLocked;

    private final boolean  accountNonExpired = true;
    private final boolean  enabled =  true;
    private final boolean credentialsNonExpired = true;


    private UserRole role;
    @Override
    public List<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public UserDetailsImpl(AppUser user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.accountNonLocked = user.isEnabled();
    }


}