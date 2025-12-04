package AMAPMRN.auth_api.domain.financeiro;

import java.math.BigDecimal;

public record TransacaoDTO(
        BigDecimal valor,
        String descricao,
        TipoTransacao tipo,
        String categoria,
        String associadoId
) {}