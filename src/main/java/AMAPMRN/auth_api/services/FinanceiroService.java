package AMAPMRN.auth_api.services;

import AMAPMRN.auth_api.domain.associado.Associado;
import AMAPMRN.auth_api.domain.financeiro.*;
import AMAPMRN.auth_api.repositories.AssociadoRepository;
import AMAPMRN.auth_api.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class FinanceiroService {

    @Autowired
    TransacaoRepository transacaoRepository;

    @Autowired
    AssociadoRepository associadoRepository;

    public TransacaoResponseDTO criarTransacao(TransacaoDTO dados) {
        Associado associado = associadoRepository.findById(dados.associadoId())
                .orElseThrow(() -> new RuntimeException("Associado não encontrado"));

        Transacao novaTransacao = new Transacao(
                dados.valor(),
                dados.descricao(),
                dados.tipo(),
                associado,
                dados.categoria()
        );

        // Salva e guarda o objeto salvo (que agora tem ID)
        var transacaoSalva = transacaoRepository.save(novaTransacao);

        // Retorna o DTO com os dados
        return new TransacaoResponseDTO(transacaoSalva);
    }

    public List<TransacaoResponseDTO> listarTransacoes(String associadoId, String tipo) {
        List<Transacao> transacoes;

        if (tipo == null || tipo.isEmpty()) {
            transacoes = transacaoRepository.findAllByAssociadoId(associadoId);
        } else {
            transacoes = transacaoRepository.findAllByAssociadoIdAndTipo(associadoId, TipoTransacao.valueOf(tipo.toUpperCase()));
        }
        return converterLista(transacoes);
    }

    public void anularTransacao(String id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        transacao.setValor(BigDecimal.ZERO);
        transacaoRepository.save(transacao);
    }

    public List<TransacaoResponseDTO> listarTudo() {
        return converterLista(transacaoRepository.findAll());
    }

    public List<TransacaoResponseDTO> listarPorTipoGlobal(String tipo) {
        return converterLista(transacaoRepository.findAllByTipo(TipoTransacao.valueOf(tipo.toUpperCase())));
    }

    public List<TransacaoResponseDTO> buscarPorVencimento(LocalDate inicio, LocalDate fim) {
        return converterLista(transacaoRepository.findAllByDataVencimentoBetween(inicio, fim));
    }

    public List<TransacaoResponseDTO> buscarPorCriacao(LocalDate inicio, LocalDate fim) {
        LocalDateTime dataInicio = inicio.atStartOfDay();
        LocalDateTime dataFim = fim.atTime(LocalTime.MAX);

        return converterLista(transacaoRepository.findAllByDataCriacaoBetween(dataInicio, dataFim));
    }

    private List<TransacaoResponseDTO> converterLista(List<Transacao> lista) {
        return lista.stream().map(TransacaoResponseDTO::new).toList();
    }
}