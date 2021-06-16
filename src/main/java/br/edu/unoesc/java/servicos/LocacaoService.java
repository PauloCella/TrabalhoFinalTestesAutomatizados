package br.edu.unoesc.java.servicos;

import br.edu.unoesc.java.entidades.Filme;
import br.edu.unoesc.java.entidades.Locacao;
import br.edu.unoesc.java.entidades.Usuario;
import br.edu.unoesc.java.repository.LocacaoServiceRepository;
import br.edu.unoesc.java.utils.DataUtils;

import java.util.Date;
import java.util.List;

public class LocacaoService {

    LocacaoServiceRepository locacaoServiceRepository;

    public Locacao alugarFilmes(Usuario usuario, List<Filme> filmes) throws Exception {

        if (filmes == null)
            throw new Exception("Lista de filmes não informada!");

        if (usuario == null)
            throw new Exception("Usuário não informado!");

        double precoTotalLocacao = 0.0;
        int countFilmes = 0;

        for (Filme filme : filmes) {
            if (filme.getEstoque().equals(0))
                throw new Exception("Filme sem Estoque! -> " + filme.getNome());


            if(countFilmes <= 2){
                precoTotalLocacao = precoTotalLocacao + filme.getPrecoLocacao();
            }else if(countFilmes <= 7) {
                double valorComDesconto = 0.0;
                if(countFilmes == 3){
                    valorComDesconto = filme.getPrecoLocacao() - (filme.getPrecoLocacao() * 10) / 100;
                }else if(countFilmes == 4){
                    valorComDesconto = filme.getPrecoLocacao() - (filme.getPrecoLocacao() * 20) / 100;
                }if(countFilmes == 5){
                    valorComDesconto = filme.getPrecoLocacao() - (filme.getPrecoLocacao() * 30) / 100;
                }if(countFilmes == 6){
                    valorComDesconto = filme.getPrecoLocacao() - (filme.getPrecoLocacao() * 50) / 100;
                }if(countFilmes == 7){
                    valorComDesconto = filme.getPrecoLocacao() - (filme.getPrecoLocacao() * 70) / 100;
                }
                precoTotalLocacao = precoTotalLocacao + valorComDesconto;
            }

            countFilmes++;

        }

        Locacao locacao = new Locacao();
        locacao.setFilmes(filmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        locacao.setValor(precoTotalLocacao);

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = DataUtils.incDays(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        filmes.forEach(filme -> {
                filme.setEstoque(filme.getEstoque() - 1);
            }
        );

        locacaoServiceRepository.salvar(locacao);

        return locacao;
    }


    public void devolverLocacao(Locacao locacao) throws Exception {

        if (locacao == null)
            throw new Exception("Locação não informada!");

        if (locacao.getFilmes().size() <= 0)
            throw new Exception("Nenhum filme para ser devolvido");

        locacao.getFilmes().forEach(filme -> {
                filme.setEstoque(filme.getEstoque() + 1);
            }
        );

        locacaoServiceRepository.remover(locacao);

    }

    public void setLocacaoServiceRepository(LocacaoServiceRepository locacaoServiceRepository) {
        this.locacaoServiceRepository = locacaoServiceRepository;
    }

}