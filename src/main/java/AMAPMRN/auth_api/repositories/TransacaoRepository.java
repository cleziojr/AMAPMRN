package AMAPMRN.auth_api.repositories;

import AMAPMRN.auth_api.domain.financeiro.TipoTransacao;
import AMAPMRN.auth_api.domain.financeiro.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, String> {

    // Busca pelo ID do Associado (Novo padrão)
    List<Transacao> findAllByAssociadoId(String associadoId);

    // Busca por Associado + Tipo
    List<Transacao> findAllByAssociadoIdAndTipo(String associadoId, TipoTransacao tipo);

    // Buscas Globais (Relatórios)
    List<Transacao> findAllByTipo(TipoTransacao tipo);
    List<Transacao> findAllByDataVencimentoBetween(LocalDate inicio, LocalDate fim);
    List<Transacao> findAllByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);
}