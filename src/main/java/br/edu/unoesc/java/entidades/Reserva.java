package br.edu.unoesc.java.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class Reserva {

    private Usuario usuario;
    private Filme filme;
    private Date dataReserva;

}
