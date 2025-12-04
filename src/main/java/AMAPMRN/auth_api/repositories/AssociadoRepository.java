package AMAPMRN.auth_api.repositories;

import AMAPMRN.auth_api.domain.associado.Associado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssociadoRepository extends JpaRepository<Associado, String> {

    // Listar associados ativos
    List<Associado> findByAtivoTrue();

    // Listar associados inativos
    List<Associado> findByAtivoFalse();

    // Verificar se já existe CPF cadastrado
    boolean existsByCpf(String cpf);

    // Verificar se já existe email cadastrado
    boolean existsByEmail(String email);
}