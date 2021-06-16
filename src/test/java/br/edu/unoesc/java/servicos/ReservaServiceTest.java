package br.edu.unoesc.java.servicos;

import br.edu.unoesc.java.entidades.Filme;
import br.edu.unoesc.java.entidades.Reserva;
import br.edu.unoesc.java.entidades.Usuario;
import br.edu.unoesc.java.repository.ReservaServiceRepository;
import br.edu.unoesc.java.utils.DataUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

public class ReservaServiceTest {

    private ReservaService reservaService;
    private ReservaServiceRepository reservaServiceRepository;
    private Usuario usuario;
    private Filme filme;
    private Date dataReserva;

    @Before
    public void iniciarTeste() {
        reservaService = new ReservaService();
        reservaServiceRepository = Mockito.mock(ReservaServiceRepository.class);
        reservaService.setReservaServiceRepository(reservaServiceRepository);

        usuario = new Usuario("Usuario");
        dataReserva = DataUtils.getDate(3);
        filme = new Filme("Filme", 2, 4.00);
    }

    @Test
    public void TesteValidarUsuarioNaoInformado() {
        //cenário
        Reserva reserva = null;

        //execução
        try {
            reserva = reservaService.reservaFilme(null, filme, dataReserva);
        } catch (Exception exception) {
            Assert.assertEquals("Usuário não informado!", exception.getMessage());
        }

        //Validação
        Assert.assertNull(reserva);
    }

    @Test
    public void TestValidarFilmeNaoInformado() {
        //cenario
        Reserva reserva = null;

        //execução
        try {
            reserva = reservaService.reservaFilme(usuario, null, dataReserva);
        } catch (Exception exception) {
            Assert.assertEquals("Filme não informado!", exception.getMessage());
        }

        //Validação
        Assert.assertNull(reserva);
    }

    @Test
    public void TestReservaFilmeComSucesso() throws Exception {
        //Cenário
        Mockito
                .doReturn(false)
                .when(reservaServiceRepository)
                .consultaLocacaoFilme(filme, dataReserva);

        //Execução
        Reserva reserva = reservaService.reservaFilme(usuario, filme, dataReserva);

        //Validação
        Assert.assertNotNull("Reserva não pode ser nula", reserva);
        Assert.assertEquals(usuario, reserva.getUsuario());
        Assert.assertEquals(filme, reserva.getFilme());
        Assert.assertEquals(dataReserva, reserva.getDataReserva());

    }

}
