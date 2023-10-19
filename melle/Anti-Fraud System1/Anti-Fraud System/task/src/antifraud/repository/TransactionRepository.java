package antifraud.repository;


import antifraud.http.model.SusIP;
import antifraud.http.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Optional<Transaction> findByIp(String ip);

    List<Transaction> findByNumber(String number);
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.date >= :date")
    Long countTransactionsInLastHour(@Param("date") Date date);

    @Query("SELECT COUNT(DISTINCT t.ip) FROM Transaction t WHERE t.ip != :ip")
    Long countUniqueIpsExcludingGivenIp(@Param("ip") String ip);

    @Query("SELECT COUNT(DISTINCT t.ip) FROM Transaction t WHERE t.ip != :ip AND t.date BETWEEN :date and :date1  AND t.number = :cardNumber")
    Long countUniqueIpsExcludingGivenIpInLastHour(@Param("ip") String ip, @Param("date") Date date, @Param("date1") Date date1,  @Param("cardNumber") String cardNumber);


    
    @Query("SELECT COUNT(DISTINCT t.region) FROM Transaction t WHERE t.region != :region AND t.date BETWEEN :date and :date1 AND t.number = :cardNumber")
    Long countUniqueRegionExcludingGivenCardNumberInLastHour(@Param("region") String region, @Param("date") Date date, @Param("date1") Date date1, @Param("cardNumber") String cardNumber);

}
