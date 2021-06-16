package br.edu.unoesc.java.servicos;

import br.edu.unoesc.java.entidades.Filme;
import br.edu.unoesc.java.entidades.Locacao;
import br.edu.unoesc.java.entidades.Usuario;
import br.edu.unoesc.java.repository.LocacaoServiceRepository;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class LocacaoServiceTest {

    LocacaoService locacaoService = null;
    Usuario usuario = null;
    List<Filme> filmes = new ArrayList<>();
    LocacaoServiceRepository locacaoServiceRepository;

    @Before
    public void iniciarTeste() {
        locacaoService = new LocacaoService();

        locacaoServiceRepository = Mockito.mock(LocacaoServiceRepository.class);
        locacaoService.setLocacaoServiceRepository(locacaoServiceRepository);

        usuario = new Usuario("Usuário");

        filmes.add(new Filme("Star wars", 5, 4.00 ));
        filmes.add(new Filme("Star Track", 1, 1.00 ));
        filmes.add(new Filme("Esqueceram de mim 3", 8, 5.00 )); //0.5 = 4.5
        filmes.add(new Filme("Cruela", 6, 5.00 )); // 1 = 4
        filmes.add(new Filme("The flash", 88, 5.00 )); //1,5 = 3.5
        filmes.add(new Filme("Aquaman", 56, 5.00 ));//2.5 = 2.5
        filmes.add(new Filme("Ciborg", 1, 5.00 )); //3.5 = 1.5
        filmes.add(new Filme("Filme Outro", 42, 5.00 ));
        filmes.add(new Filme("Titanic", 324, 5.00 ));
        filmes.add(new Filme("Awake", 234, 5.00 ));
        filmes.add(new Filme("Jurassic world", 11, 5.00 ));
        filmes.add(new Filme("Space Jam", 55, 5.00 ));

    }

    @After
    public void finalizarTeste() {
        locacaoService = null;
        locacaoServiceRepository = null;
        usuario = null;
    }

    @SneakyThrows
    @Test
    public void testeErroAoSalvar() {
        Mockito
                .doThrow(new Exception("Erro ao Salvar!"))
                .when(locacaoServiceRepository)
                .salvar(Mockito.any());

        Assert.assertThrows("Erro ao Salvar!", Exception.class, () -> locacaoService.alugarFilmes(usuario, filmes));
    }

    @Test
    public void testeAlugarFilmes() throws Exception {
        //cenário
        Usuario expectedUsuario = new Usuario("Usuário");

        Double expectedResult = 26.0;

        //execução
        Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);

        //validação
        Assert.assertEquals(expectedUsuario, locacao.getUsuario());
        Assert.assertEquals(filmes, locacao.getFilmes());
        Assert.assertEquals(expectedResult, locacao.getValor());
        Assert.assertNotNull(locacao.getDataLocacao());
        Assert.assertTrue(locacao.getDataRetorno().after(locacao.getDataLocacao()));
    }



    @Test
    public void TestAlugarFilme_SemUsuario() {
        Assert.assertThrows("Usuário não informado!", Exception.class, () -> locacaoService.alugarFilmes(null, filmes));
    }

    @Test
    public void TestAlugarFilme_SemFilme() {
        Assert.assertThrows("Lista de filmes não informada!", Exception.class, () -> locacaoService.alugarFilmes(usuario, null));
    }

    @Test
    public void TestAlugarFilme_FilmeSemEstoque() {

        filmes.get(0).setEstoque(0);

        Assert.assertThrows("Não deveria ter gerado a locação!", Exception.class, () -> locacaoService.alugarFilmes(usuario, filmes));
    }

    @Test
    public void TestDeveBaixarEstoque() throws Exception {
        Integer expectedEstoque = 4;

        locacaoService.alugarFilmes(usuario, filmes);

        Assert.assertEquals(expectedEstoque, filmes.get(0).getEstoque());
    }


    @SneakyThrows
    @Test
    public void DevolverLocacao(){
        Integer expectedEstoque = 5;

        Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);

        locacaoService.devolverLocacao(locacao);
        Assert.assertEquals(expectedEstoque, filmes.get(0).getEstoque());
    }






}

