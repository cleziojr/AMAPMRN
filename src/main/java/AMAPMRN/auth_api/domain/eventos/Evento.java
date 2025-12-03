package AMAPMRN.auth_api.domain.evento;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Table(name = "eventos")
@Entity(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nomeEvento;

    private String nomeCoordenador;

    private String localEvento;

    private LocalDate dataEvento;

    private LocalTime horarioInicio;

    private LocalTime horarioFim;

    @Column(length = 500)
    private String descricao;

    private Integer capacidadeMaxima;

    private Double valorInscricao;

    private boolean ativo = true;

    public Evento() {
    }

    public Evento(
            String id,
            String nomeEvento,
            String nomeCoordenador,
            String localEvento,
            LocalDate dataEvento,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            String descricao,
            Integer capacidadeMaxima,
            Double valorInscricao,
            boolean ativo
    ) {
        this.id = id;
        this.nomeEvento = nomeEvento;
        this.nomeCoordenador = nomeCoordenador;
        this.localEvento = localEvento;
        this.dataEvento = dataEvento;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.descricao = descricao;
        this.capacidadeMaxima = capacidadeMaxima;
        this.valorInscricao = valorInscricao;
        this.ativo = ativo;
    }

    // GETTERS E SETTERS

    public String getId() {
        return id;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getNomeCoordenador() {
        return nomeCoordenador;
    }

    public void setNomeCoordenador(String nomeCoordenador) {
        this.nomeCoordenador = nomeCoordenador;
    }

    public String getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(String localEvento) {
        this.localEvento = localEvento;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(Integer capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public Double getValorInscricao() {
        return valorInscricao;
    }

    public void setValorInscricao(Double valorInscricao) {
        this.valorInscricao = valorInscricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // equals & hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evento)) return false;
        Evento evento = (Evento) o;
        return Objects.equals(id, evento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
