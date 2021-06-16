package br.edu.unoesc.java.servicos;

import br.edu.unoesc.java.entidades.Filme;
import br.edu.unoesc.java.entidades.Reserva;
import br.edu.unoesc.java.entidades.Usuario;
import br.edu.unoesc.java.repository.ReservaServiceRepository;
import lombok.Setter;

import java.util.Date;

@Setter
public class ReservaService {

    private ReservaServiceRepository reservaServiceRepository;

    public Reserva reservaFilme(Usuario usuario, Filme filme, Date dataReserva) throws Exception {

        if (usuario == null)
            throw new Exception("Usuário não informado!");

        if (filme == null)
            throw new Exception("Filme não informado!");

        if (reservaServiceRepository.consultaLocacaoFilme(filme, dataReserva))
            return null;

        return new Reserva(usuario, filme, dataReserva);
    }

}
