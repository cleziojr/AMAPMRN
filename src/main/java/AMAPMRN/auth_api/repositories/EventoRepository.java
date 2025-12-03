package AMAPMRN.auth_api.repositories;

import AMAPMRN.auth_api.domain.evento.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, String> {
}
