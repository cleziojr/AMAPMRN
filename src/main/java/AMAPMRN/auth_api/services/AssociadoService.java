package AMAPMRN.auth_api.services;

import AMAPMRN.auth_api.domain.associado.Associado;
import AMAPMRN.auth_api.repositories.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository repository;

    // Listar todos os associados (ativos + inativos)
    public List<Associado> listarTodos() {
        return repository.findAll();
    }

    // Listar apenas associados ativos
    public List<Associado> listarAtivos() {
        return repository.findByAtivoTrue();
    }

    // Listar apenas associados inativos
    public List<Associado> listarInativos() {
        return repository.findByAtivoFalse();
    }

    // Buscar associado por ID
    public Optional<Associado> buscarPorId(String id) {
        return repository.findById(id);
    }

    // Cadastrar novo associado
    public Associado salvar(Associado associado) {

        if (repository.existsByCpf(associado.getCpf())) {
            throw new RuntimeException("Já existe um associado com este CPF.");
        }

        if (repository.existsByEmail(associado.getEmail())) {
            throw new RuntimeException("Já existe um associado com este email.");
        }

        return repository.save(associado);
    }

    // Atualizar associado
    public Associado atualizar(Associado associado) {
        return repository.save(associado);
    }

    // Desativar associado (exclusão lógica)
    public void desativar(String id) {
        Optional<Associado> associado = repository.findById(id);

        if (associado.isPresent()) {
            Associado a = associado.get();
            a.setAtivo(false);
            repository.save(a);
        } else {
            throw new RuntimeException("Associado não encontrado.");
        }
    }

    public void reativar(String id) {
    Optional<Associado> associadoOpt = repository.findById(id);

    if (associadoOpt.isPresent()) {
        Associado associado = associadoOpt.get();
        associado.setAtivo(true);
        repository.save(associado);
    } else {
        throw new RuntimeException("Associado não encontrado.");
    }
    }

    public List<Associado> buscarAniversariantesPorMes(int mes) {
    List<Associado> todos = repository.findAll();

    return todos.stream()
        .filter(a -> a.getDataNascimento() != null)
        .filter(a -> a.getDataNascimento().getMonthValue() == mes)
        .collect(Collectors.toList());
    }

}