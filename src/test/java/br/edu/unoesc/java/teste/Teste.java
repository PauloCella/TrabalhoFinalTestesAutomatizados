package br.edu.unoesc.java.teste;

import br.edu.unoesc.java.entidades.Usuario;
import org.junit.*;
import org.junit.runners.MethodSorters;

public class Teste {

    @Before
    public void init() {
        System.out.println("Before Test");
    }

    @After
    public void finalizarTeste() {
        System.out.println("After Test");
    }

    @Test
    public void teste() {
        System.out.println("Teste 1");
        Assert.assertTrue(true);
        Assert.assertFalse(false);

        int expected = 1;
        Integer expected2 = 1;
        Assert.assertEquals(Integer.valueOf(expected), expected2);
        Assert.assertEquals(expected, expected2.intValue());

        Usuario user = new Usuario("Usuário");
        Usuario user2 = new Usuario("Usuário");

        Assert.assertEquals(user, user2);

        Usuario user3 = null;

        Assert.assertTrue(user3 == null);
        Assert.assertNull(user3);

        user3 = user;

        Assert.assertSame("Usuários diferentes", user, user3);
    }

    @Test
    public void teste2() {
        System.out.println("Teste 2");
    }

    @Test
    public void teste3() {
        System.out.println("Teste 3");
    }

}
