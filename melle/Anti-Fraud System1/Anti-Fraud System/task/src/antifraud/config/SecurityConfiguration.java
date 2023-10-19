package antifraud.config;



import antifraud.http.RestAuthenticationEntryPoint;
import antifraud.util.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    public SecurityConfiguration(RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)                           // For modifying requests via Postman
                .exceptionHandling(handing -> handing
                        .authenticationEntryPoint(restAuthenticationEntryPoint) // Handles auth error
                )
                .headers(headers -> headers.frameOptions().disable())           // for Postman, the H2 console
                .authorizeHttpRequests(requests -> requests                     // manage access
                                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/h2/**")).permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/auth/user").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
                                .requestMatchers(HttpMethod.DELETE , "/api/auth/user/*").hasRole(UserRole.ADMINISTRATOR.toString())
                                .requestMatchers(HttpMethod.GET , "/api/auth/list").hasAnyRole(UserRole.ADMINISTRATOR.toString(), UserRole.SUPPORT.toString())
                                .requestMatchers(HttpMethod.POST, "/api/antifraud/transaction").hasRole(UserRole.MERCHANT.toString())
                                .requestMatchers(HttpMethod.PUT , "/api/auth/access").hasRole(UserRole.ADMINISTRATOR.toString())
                                .requestMatchers(HttpMethod.PUT , "/api/auth/role").hasRole(UserRole.ADMINISTRATOR.toString())
                                .requestMatchers("/api/antifraud/**").hasRole(UserRole.SUPPORT.toString())


                                .requestMatchers("/actuator/shutdown").permitAll()      // needs to run test
                                .requestMatchers("/errors/**").permitAll()
                                .requestMatchers("/error**").permitAll()

                        // other matchers
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
                )
                // other configurations
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}