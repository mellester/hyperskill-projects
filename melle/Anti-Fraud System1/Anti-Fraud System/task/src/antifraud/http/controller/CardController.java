package antifraud.http.controller;

import antifraud.http.View;
import antifraud.http.model.Card;
import antifraud.http.model.SusIP;
import antifraud.http.response.accesResponse;
import antifraud.repository.CardRepository;
import antifraud.repository.IpRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/antifraud/stolencard")
public class CardController {

    @Autowired
    private CardRepository repository;

    @GetMapping
    @JsonView(View.UserView.class)
    public List<Card> findAllCards() {
        return (List<Card>) repository.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(View.UserView.class)
    public ResponseEntity<Card> findIPById(@PathVariable(value = "id") String number) {
        // Implement
        Optional<Card> card = repository.findByNumber(number);

        return card.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{number}")
    @Validated
    public ResponseEntity<accesResponse> deleteCard(
            @PathVariable(value = "number") String number) {

       if (!Card.checkIfValid(number)) {
           throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid Number");     }



        Optional<Card> card = repository.findByNumber(number);
        String status = "Card " + number + " successfully removed!";
        if (card.isPresent()) {
            repository.delete(card.get());
            return ResponseEntity.ok().body(new accesResponse(status));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @JsonView(View.UserView.class)
    public ResponseEntity<Card> saveUser(@Validated @RequestBody Card card) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repository.save(card));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


}