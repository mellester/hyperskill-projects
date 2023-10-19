package antifraud.http.model;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Validated
public class SusIP{
    public static final String regex = "((\\d{1,2}|1\\d{1,2}|2([0-4]\\d|5[0-5]))(\\.(?!$)|$)){4}";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique=true)
    @Pattern(regexp = regex)
    private String ip;


    public static boolean checkIp(String ip) {
        return java.util.regex.Pattern.matches(regex, ip);
    }
}