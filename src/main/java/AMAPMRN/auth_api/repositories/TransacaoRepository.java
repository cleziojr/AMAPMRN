package AMAPMRN.auth_api.repositories;

import AMAPMRN.auth_api.domain.financeiro.StatusTransacao;
import AMAPMRN.auth_api.domain.financeiro.TipoTransacao;
import AMAPMRN.auth_api.domain.financeiro.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, String> {

    List<Transacao> findAllByUsuarioId(String usuarioId);
    List<Transacao> findAllByUsuarioIdAndTipo(String usuarioId, TipoTransacao tipo);
    List<Transacao> findAllByTipo(TipoTransacao tipo);
    List<Transacao> findAllByDataVencimentoBetween(LocalDate inicio, LocalDate fim);
    List<Transacao> findAllByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Transacao> findAllByUsuarioIdAndDataVencimentoBetween(String usuarioId, LocalDate inicio, LocalDate fim);
    List<Transacao> findAllByUsuarioIdAndStatus(String usuarioId, StatusTransacao status);
}