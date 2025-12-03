package AMAPMRN.auth_api.services;

import AMAPMRN.auth_api.domain.financeiro.*;
import AMAPMRN.auth_api.domain.user.User;
import AMAPMRN.auth_api.repositories.TransacaoRepository;
import AMAPMRN.auth_api.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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
    UserRepository userRepository;

    public void criarTransacao(TransacaoDTO dados) {
        User usuario = userRepository.findById(dados.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Transacao novaTransacao = new Transacao(
                dados.valor(),
                dados.descricao(),
                dados.tipo(),
                usuario,
                dados.categoria()
        );
        transacaoRepository.save(novaTransacao);
    }

    public List<TransacaoResponseDTO> listarTransacoes(String usuarioId, String tipo) {
        List<Transacao> transacoes;
        if (tipo == null || tipo.isEmpty()) {
            transacoes = transacaoRepository.findAllByUsuarioId(usuarioId);
        } else {
            transacoes = transacaoRepository.findAllByUsuarioIdAndTipo(usuarioId, TipoTransacao.valueOf(tipo.toUpperCase()));
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

    public List<TransacaoResponseDTO> listarMinhasTransacoes(String status) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        User usuarioLogado = (User) auth.getPrincipal();
        String meuId = usuarioLogado.getId();

        List<Transacao> transacoes;
        if (status == null || status.isEmpty()) {
            transacoes = transacaoRepository.findAllByUsuarioId(meuId);
        } else {
            transacoes = transacaoRepository.findAllByUsuarioIdAndStatus(meuId, StatusTransacao.valueOf(status.toUpperCase()));
        }

        return transacoes.stream().map(TransacaoResponseDTO::new).toList();
    }
}