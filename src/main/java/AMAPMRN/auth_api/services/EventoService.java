package AMAPMRN.auth_api.services;

import AMAPMRN.auth_api.domain.evento.Evento;
import AMAPMRN.auth_api.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Evento buscarPorId(String id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
    }

    public Evento criar(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Evento atualizar(String id, Evento eventoAtualizado) {
        Evento evento = buscarPorId(id);

        evento.setNomeEvento(eventoAtualizado.getNomeEvento());
        evento.setNomeCoordenador(eventoAtualizado.getNomeCoordenador());
        evento.setLocalEvento(eventoAtualizado.getLocalEvento());
        evento.setDataEvento(eventoAtualizado.getDataEvento());
        evento.setHorarioInicio(eventoAtualizado.getHorarioInicio());
        evento.setHorarioFim(eventoAtualizado.getHorarioFim());
        evento.setDescricao(eventoAtualizado.getDescricao());
        evento.setCapacidadeMaxima(eventoAtualizado.getCapacidadeMaxima());
        evento.setValorInscricao(eventoAtualizado.getValorInscricao());

        return eventoRepository.save(evento);
    }

    public void desativar(String id) {
        Evento evento = buscarPorId(id);
        evento.setAtivo(false);
        eventoRepository.save(evento);
    }
}
