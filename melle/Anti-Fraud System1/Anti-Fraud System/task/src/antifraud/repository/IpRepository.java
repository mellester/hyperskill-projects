package antifraud.repository;


import antifraud.http.model.AppUser;
import antifraud.http.model.SusIP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpRepository extends CrudRepository<SusIP, Long> {
    Optional<SusIP> findSusIPByIp(String ip);

}