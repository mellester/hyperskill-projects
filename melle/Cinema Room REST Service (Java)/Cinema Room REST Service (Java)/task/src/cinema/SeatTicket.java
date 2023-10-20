package cinema;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public class SeatTicket {

    public Seat ticket;

    public String token = UUID.randomUUID().toString();
    SeatTicket(Seat seat) {
        ticket = seat;
    }

}
