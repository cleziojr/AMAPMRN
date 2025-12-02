package AMAPMRN.auth_api.domain.associado;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Table(name = "associados")
@Entity(name = "associados")
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    private String identidade;

    private String email;

    private String telefone;

    private String profissao;

    private String religiao;

    private LocalDate dataNascimento;

    private Double valorContribuicaoMensal;

    private boolean ativo = true;

    public Associado() {
    }

    public Associado(String id,
                     String nome,
                     String cpf,
                     String identidade,
                     String email,
                     String telefone,
                     String profissao,
                     String religiao,
                     LocalDate dataNascimento,
                     Double valorContribuicaoMensal,
                     boolean ativo) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.identidade = identidade;
        this.email = email;
        this.telefone = telefone;
        this.profissao = profissao;
        this.religiao = religiao;
        this.dataNascimento = dataNascimento;
        this.valorContribuicaoMensal = valorContribuicaoMensal;
        this.ativo = ativo;
    }

    // GETTERS E SETTERS 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getReligiao() {
        return religiao;
    }

    public void setReligiao(String religiao) {
        this.religiao = religiao;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Double getValorContribuicaoMensal() {
        return valorContribuicaoMensal;
    }

    public void setValorContribuicaoMensal(Double valorContribuicaoMensal) {
        this.valorContribuicaoMensal = valorContribuicaoMensal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    // EQUALS & HASHCODE 

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Associado)) return false;
        Associado associado = (Associado) o;
        return Objects.equals(id, associado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
