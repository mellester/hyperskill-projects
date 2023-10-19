package antifraud;

import antifraud.http.RestAuthenticationEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class AntiFraudApplication {
    public static final Logger logger = LoggerFactory.getLogger(AntiFraudApplication.class);
    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
        }
        SpringApplication.run(AntiFraudApplication.class, args);
    }
}

