package AMAPMRN.auth_api.controllers;

import AMAPMRN.auth_api.domain.evento.Evento;
import AMAPMRN.auth_api.services.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // LISTAR TODOS OS EVENTOS
    @GetMapping
    public ResponseEntity<List<Evento>> listarTodos() {
        var eventos = eventoService.listarTodos();
        return ResponseEntity.ok(eventos);
    }

    // BUSCAR EVENTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscarPorId(@PathVariable String id) {
        var evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }

    // CRIAR EVENTO
    @PostMapping
    public ResponseEntity<Evento> criar(@RequestBody @Valid Evento evento) {
        var novoEvento = eventoService.criar(evento);
        return ResponseEntity.ok(novoEvento);
    }

    // ATUALIZAR EVENTO
    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizar(
            @PathVariable String id,
            @RequestBody Evento eventoAtualizado
    ) {
        var evento = eventoService.atualizar(id, eventoAtualizado);
        return ResponseEntity.ok(evento);
    }

    // DESATIVAR / DELETAR EVENTO (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable String id) {
        eventoService.desativar(id);
        return ResponseEntity.ok("Evento desativado com sucesso.");
    }
}
