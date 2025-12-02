package AMAPMRN.auth_api.controllers;

import AMAPMRN.auth_api.domain.associado.Associado;
import AMAPMRN.auth_api.services.AssociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/associados")
@CrossOrigin(origins = "*") // permite acesso do front
public class AssociadoController {

    @Autowired
    private AssociadoService associadoService;

    //  Cadastrar um novo associado
    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, Object>> cadastrar(@RequestBody Associado associado) {
        Map<String, Object> resposta = new HashMap<>();

        try {
            Associado novo = associadoService.salvar(associado);

            resposta.put("mensagem", "Associado cadastrado com sucesso!");
            resposta.put("associado", novo);

            return ResponseEntity.status(201).body(resposta); 

        } catch (RuntimeException e) {
            resposta.put("mensagem", "Erro ao cadastrar associado!");
            resposta.put("erro", e.getMessage());

            return ResponseEntity.badRequest().body(resposta);
        }
    }

    // Listar TODOS (ativos + inativos)
    @GetMapping
    public ResponseEntity<List<Associado>> listarTodos() {
        List<Associado> associados = associadoService.listarTodos();
        return ResponseEntity.ok(associados);
    }

    // Listar apenas ATIVOS
    @GetMapping("/ativos")
    public ResponseEntity<List<Associado>> listarAtivos() {
        List<Associado> associados = associadoService.listarAtivos();
        return ResponseEntity.ok(associados);
    }

    //  Listar apenas INATIVOS
    @GetMapping("/inativos")
    public ResponseEntity<List<Associado>> listarInativos() {
        List<Associado> associados = associadoService.listarInativos();
        return ResponseEntity.ok(associados);
    }

    //  Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        Optional<Associado> associado = associadoService.buscarPorId(id);

        if (associado.isPresent()) {
            return ResponseEntity.ok(associado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // "Deletar" (na verdade: desativar)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable String id) {
        try {
            associadoService.desativar(id);
            return ResponseEntity.ok("Associado desativado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Reativar associado
    @PutMapping("/reativar/{id}")
    public ResponseEntity<?> reativar(@PathVariable String id) {
        try {
            associadoService.reativar(id);
            return ResponseEntity.ok("Associado reativado com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //  Listar aniversariantes por mês (1 a 12)
    @GetMapping("/aniversariantes/{mes}")
    public ResponseEntity<?> listarAniversariantesPorMes(@PathVariable int mes) {

        if (mes < 1 || mes > 12) {
            return ResponseEntity.badRequest().body("Mês inválido. Informe um valor entre 1 e 12.");
        }

        List<Associado> aniversariantes = associadoService.buscarAniversariantesPorMes(mes);

        if (aniversariantes.isEmpty()) {
            return ResponseEntity.ok("Nenhum aniversariante encontrado para este mês.");
        }

        return ResponseEntity.ok(aniversariantes);
    }


}
