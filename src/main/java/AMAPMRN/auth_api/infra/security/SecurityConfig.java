package AMAPMRN.auth_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desativa o CSRF (importante para APIs REST)
            .csrf(csrf -> csrf.disable())

            // Configura as permissões
            .authorizeHttpRequests(auth -> auth
                // LIBERAR TODAS AS ROTAS DE ASSOCIADOS
                .requestMatchers("/associados/**").permitAll()
                .requestMatchers("/eventos/**").permitAll()

                // Todo o resto continua protegido
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
