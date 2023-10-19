package antifraud.repository;

import antifraud.http.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {


    Boolean existsByUsername(String username);
    Optional<AppUser> findAppUserByUsername(String username);
}