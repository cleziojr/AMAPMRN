package AMAPMRN.auth_api.controllers;

import AMAPMRN.auth_api.domain.financeiro.TransacaoDTO;
import AMAPMRN.auth_api.domain.financeiro.TransacaoResponseDTO;
import AMAPMRN.auth_api.services.FinanceiroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("financeiro")
public class FinanceiroController {

    @Autowired
    FinanceiroService financeiroService;

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> criar(@RequestBody @Valid TransacaoDTO dados) {
        // Recebe a transação criada
        var novaTransacao = financeiroService.criarTransacao(dados);

        // Retorna 200 (ou 201) com o JSON no corpo
        return ResponseEntity.ok(novaTransacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity anular(@PathVariable String id) {
        financeiroService.anularTransacao(id);
        return ResponseEntity.ok().body("Transação anulada (valor atualizado para zero).");
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TransacaoResponseDTO>> listarPorUsuario(
            @PathVariable String usuarioId,
            @RequestParam(required = false) String tipo
    ) {
        var transacoes = financeiroService.listarTransacoes(usuarioId, tipo);
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TransacaoResponseDTO>> listarGeral() {
        return ResponseEntity.ok(financeiroService.listarTudo());
    }

    // GET /financeiro/tipo?valor=CREDITO
    @GetMapping("/tipo")
    public ResponseEntity<List<TransacaoResponseDTO>> listarPorTipo(@RequestParam String valor) {
        return ResponseEntity.ok(financeiroService.listarPorTipoGlobal(valor));
    }

    // GET /financeiro/vencimento?inicio=2025-01-01&fim=2025-01-31
    @GetMapping("/vencimento")
    public ResponseEntity<List<TransacaoResponseDTO>> buscarPorVencimento(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        return ResponseEntity.ok(financeiroService.buscarPorVencimento(inicio, fim));
    }

    // GET /financeiro/criacao?inicio=2025-01-01&fim=2025-01-31
    @GetMapping("/criacao")
    public ResponseEntity<List<TransacaoResponseDTO>> buscarPorCriacao(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        return ResponseEntity.ok(financeiroService.buscarPorCriacao(inicio, fim));
    }
}