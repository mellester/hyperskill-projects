package antifraud.http.model;

import antifraud.http.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Entity
@Getter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(View.UserView.class)
    private long id;

    @Column(unique=true)
    @JsonView(View.UserView.class)
    @NotEmpty
    private String number;

    @AssertTrue
    public boolean isValid() {
        return checkIfValid(number);
    }
    public static boolean checkIfValid(String number) {
        int nDigits = number.length();
        int sum = 0;
        boolean isSecond = false;

        for (int i = nDigits - 1; i >= 0; i--) {
            int d = number.charAt(i) - '0';

            if (isSecond) d = d * 2;

            sum += d / 10;
            sum += d % 10;

            isSecond = !isSecond;
        }
        return (sum % 10 == 0);
    }
}