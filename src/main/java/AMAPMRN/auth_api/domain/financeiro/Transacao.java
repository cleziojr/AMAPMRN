package AMAPMRN.auth_api.domain.financeiro;

import AMAPMRN.auth_api.domain.associado.Associado;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "transacoes")
@Table(name = "transacoes")
@NoArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private BigDecimal valor;
    private String descricao;
    private LocalDate dataVencimento;
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @Enumerated(EnumType.STRING)
    private StatusTransacao status;

    // MUDANÇA CRUCIAL: Aponta para o Associado (Voluntário)
    @ManyToOne
    @JoinColumn(name = "associado_id")
    private Associado associado;

    private String categoria;

    public Transacao(BigDecimal valor, String descricao, TipoTransacao tipo, Associado associado, String categoria) {
        this.valor = valor;
        this.descricao = descricao;
        this.tipo = tipo;
        this.associado = associado;
        this.categoria = categoria;
        this.status = StatusTransacao.PENDENTE;
        this.dataCriacao = LocalDateTime.now();
        this.dataVencimento = LocalDate.now().plusDays(7);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public TipoTransacao getTipo() { return tipo; }
    public void setTipo(TipoTransacao tipo) { this.tipo = tipo; }
    public StatusTransacao getStatus() { return status; }
    public void setStatus(StatusTransacao status) { this.status = status; }

    public Associado getAssociado() { return associado; }
    public void setAssociado(Associado associado) { this.associado = associado; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Objects.equals(id, transacao.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}