package antifraud.http.model;

import java.util.Date;

import antifraud.http.requests.TransactionRequest;
import antifraud.util.enums.Feedback;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Entity
@NoArgsConstructor
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;

    private  long amount;
    private  String ip;
    private  String number;
    private  String region;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private  Date date;
    @Setter
    @JsonSetter(nulls= Nulls.AS_EMPTY)
    private Feedback result = null;
    @Setter
    private Feedback feedback = null;

    public Transaction(TransactionRequest request) {
        amount = request.getAmount();
        ip = request.getIp();
        number = request.getNumber();
        region = request.getRegion();
        date = request.getDate();
    }

    @Override 
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", ip='" + ip + '\'' +
                ", number='" + number + '\'' +
                ", region='" + region + '\'' +
                ", date=" + date +
                ", result=" + result +
                ", feedback=" + feedback +
                '}';
    }
}
