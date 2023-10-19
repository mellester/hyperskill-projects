package antifraud.http.controller;

import antifraud.http.model.SusIP;
import antifraud.http.response.accesResponse;
import antifraud.repository.IpRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@RestController
@RequestMapping("/api/antifraud/suspicious-ip")
public class IPController {

    @Autowired
    private IpRepository ipRepository;

    @GetMapping
    public List<SusIP> findAllUsers() {
        return (List<SusIP>) ipRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SusIP> findIPById(@PathVariable(value = "id") long id) {
        // Implement
        Optional<SusIP> susIP = ipRepository.findById(id);

        if (susIP.isPresent()) {
            return ResponseEntity.ok().body(susIP.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{ip}")
    @Validated
    public ResponseEntity<accesResponse> delleteIPById(
            @PathVariable(value = "ip") String ip) {

        if (!SusIP.checkIp(ip))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not valid IP");

        Optional<SusIP> susIP = ipRepository.findSusIPByIp(ip);
        String status = "IP " + ip + " successfully removed!";
        if (susIP.isPresent()) {
            ipRepository.delete(susIP.get());
            return ResponseEntity.ok().body(new accesResponse(status));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<SusIP> saveUser(@Validated @RequestBody SusIP ip) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ipRepository.save(ip));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


}