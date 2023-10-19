package antifraud.repository;


import antifraud.http.model.Card;
import antifraud.http.model.SusIP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
    Optional<Card> findByNumber(String number);

}