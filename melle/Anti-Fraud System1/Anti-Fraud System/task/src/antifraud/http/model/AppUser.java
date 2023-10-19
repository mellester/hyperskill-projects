package antifraud.http.model;

import antifraud.http.View;
import antifraud.util.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue
    @JsonView(View.UserView.class)
    int id;

    @NotEmpty
    @JsonView(View.UserView.class)
    private String name;

    @JsonView(View.UserView.class)
    @Column(unique=true)
    private String username;

    private boolean  enabled =  false;

    @JsonView(View.UserView.class)
    private UserRole role = UserRole.MERCHANT;

    @NotEmpty
    private String password;

    public AppUser(String name, String password, String username) {

        this.name = name;
        this.password = password;
        this.username = username;

    }




    public void setIsAccountLocked(boolean b) {
        enabled = !b;
    }


}