package br.edu.unoesc.java.repository;

import br.edu.unoesc.java.entidades.Filme;

import java.util.Date;

public interface ReservaServiceRepository {

    public boolean consultaLocacaoFilme(Filme filme, Date dataLocacao);

}
