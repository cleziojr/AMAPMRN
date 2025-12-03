package AMAPMRN.auth_api.infra.security;

import AMAPMRN.auth_api.domain.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers("/error").permitAll() // Importante para ver logs de erro 400/500

                        // 2. ROTAS DO USUÁRIO COMUM
                        // Permite que qualquer um logado veja seus próprios lançamentos
                        .requestMatchers("/financeiro/meus-lancamentos").authenticated()

                        // CONFIGURAÇÃO DO MÓDULO DE EVENTOS
                        // Opção A: Se todos podem ver eventos, mas só Admin cria:
                        .requestMatchers(HttpMethod.GET, "/eventos/**").authenticated()
                        .requestMatchers("/eventos/**").hasAuthority("ROLE_" + UserRole.ADMIN.name())

                        // 3. ROTAS ADMINISTRATIVAS
                        .requestMatchers("/associados/**").hasAuthority("ROLE_" + UserRole.ADMIN.name())
                        .requestMatchers("/financeiro/**").hasAuthority("ROLE_" + UserRole.ADMIN.name())

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}