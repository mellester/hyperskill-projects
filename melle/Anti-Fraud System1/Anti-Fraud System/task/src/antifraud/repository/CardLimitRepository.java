package antifraud.repository;


import antifraud.http.model.Card;
import antifraud.http.model.CardLimit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardLimitRepository extends CrudRepository<CardLimit, Long> {
    Optional<CardLimit> findByNumber(String number);

}