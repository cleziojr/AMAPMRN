package AMAPMRN.auth_api.domain.financeiro;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoResponseDTO(
        String id,
        BigDecimal valor,
        String descricao,
        TipoTransacao tipo,
        StatusTransacao status,
        LocalDate dataVencimento,
        String categoria,
        String nomeUsuario
) {
    public TransacaoResponseDTO(Transacao entidade){
        this(
                entidade.getId(),
                entidade.getValor(),
                entidade.getDescricao(),
                entidade.getTipo(),
                entidade.getStatus(),
                entidade.getDataVencimento(),
                entidade.getCategoria(),
                entidade.getUsuario().getUsername()
        );
    }
}