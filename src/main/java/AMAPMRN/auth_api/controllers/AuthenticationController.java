package AMAPMRN.auth_api.controllers;

import AMAPMRN.auth_api.domain.user.AuthenticationDTO;
import AMAPMRN.auth_api.domain.user.LoginResponseDTO;
import AMAPMRN.auth_api.domain.user.RegisterDTO;
import AMAPMRN.auth_api.domain.user.User;
import AMAPMRN.auth_api.infra.security.TokenService;
import AMAPMRN.auth_api.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        // 1. Encapsula as credenciais
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        // 2. O Spring tenta fazer o login (checa senha e se está ativo)
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // 3. Se passar, gera o token
        var token = tokenService.generateToken((User) auth.getPrincipal());

        // 4. Retorna o JSON com o token
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    // POST /auth/register (Aberto para criar o primeiro Admin)
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        // 1. Verifica se o login já existe
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        // 2. Criptografa a senha antes de salvar
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        // 3. Cria o usuário (Lembre-se: o construtor do User define active=true por padrão)
        User newUser = new User(data.login(), encryptedPassword, data.role());

        // 4. Salva no banco
        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    // GET /auth/teste-token (Apenas para verificar se o token funciona)
    @GetMapping("/teste-token")
    public ResponseEntity<String> testeToken(){
        return ResponseEntity.ok("Sucesso! Você tem um token válido e está autenticado.");
    }
}