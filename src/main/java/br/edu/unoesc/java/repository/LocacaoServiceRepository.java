package br.edu.unoesc.java.repository;

import br.edu.unoesc.java.entidades.Locacao;

public interface LocacaoServiceRepository {

    public void salvar(Locacao locacao) throws Exception;

    public void remover(Locacao locacao) throws Exception;

}
