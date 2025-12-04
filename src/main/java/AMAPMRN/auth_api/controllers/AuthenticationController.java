package AMAPMRN.auth_api.controllers;

import AMAPMRN.auth_api.domain.user.AuthenticationDTO;
import AMAPMRN.auth_api.domain.user.LoginResponseDTO;
import AMAPMRN.auth_api.domain.user.RegisterDTO;
import AMAPMRN.auth_api.domain.user.User;
import AMAPMRN.auth_api.infra.security.TokenService;
import AMAPMRN.auth_api.repositories.AssociadoRepository;
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
    private AssociadoRepository associadoRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        // 1. Valida login duplicado
        if(this.repository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().body("Erro: Login já existe.");
        }

        // 2. Valida se o associado existe e pega o objeto
        if (data.associadoId() == null) {
            return ResponseEntity.badRequest().body("Erro: O ID do associado é obrigatório.");
        }

        var associadoOptional = associadoRepository.findById(data.associadoId());
        if (associadoOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Erro: Associado não encontrado. Cadastre o associado antes de criar o login.");
        }

        // 3. Criptografa senha
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        // 4. Cria o usuário vinculando ao associado encontrado
        User newUser = new User(
                data.login(),
                encryptedPassword,
                data.role(),
                associadoOptional.get() // Passa o objeto Associado recuperado do banco
        );

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/teste-token")
    public ResponseEntity<String> testeToken(){
        return ResponseEntity.ok("Sucesso! Você tem um token válido e está autenticado.");
    }
}