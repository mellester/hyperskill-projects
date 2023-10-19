package antifraud.http.model;

import antifraud.http.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CardLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(View.UserView.class)
    private long id;

    @Column(unique=true)
    @JsonView(View.UserView.class)
    @NotEmpty
    private String number;

    private long numberAllowed = 200;
    private long numberManual = 1500;

    public CardLimit(String number) {
        this.number = number;
    }

    @AssertTrue
    public boolean isValid() {
        return Card.checkIfValid(number);
    }

    public void increaseAllowed(long value_from_transaction) {
        numberAllowed = (long) Math.ceil(0.8 * numberAllowed + 0.2 * value_from_transaction);
    }

    public void increaseManual(long value_from_transaction) {
        numberManual = (long) Math.ceil(0.8 * numberManual + 0.2 * value_from_transaction);
    }

    public void decreaseAllowed(long value_from_transaction) {
        numberAllowed = (long) Math.ceil(0.8 * numberAllowed - 0.2 * value_from_transaction);
    }

    public void decreaseManual(long value_from_transaction) {
        numberManual = (long) Math.ceil(0.8 * numberManual - 0.2 * value_from_transaction);
    }

    @Override
    public String toString() {
        return "CardLimit{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", numberAllowed=" + numberAllowed +
                ", numberManual=" + numberManual +
                '}';
    }

}