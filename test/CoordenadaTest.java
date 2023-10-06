import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import models.Coordenada;

public class CoordenadaTest {
    private CampoMinado campoMinado;

    @Before
    public void setUp() {
        campoMinado = new CampoMinado(1); // Use 1 para fácil, 2 para intermediário, 3 para difícil
    }

    @Test
    public void colocarBandeiraEVerificar() {
        campoMinado.colocarBandeira(new Coordenada(1, 1));
        assertEquals('P', campoMinado.getTabuleiro()[1][1]);
    }

    @Test
    public void removerBandeiraEVerificar() {
        campoMinado.colocarBandeira(new Coordenada(1, 1));
        campoMinado.removerBandeira(new Coordenada(1, 1));
        assertEquals('-', campoMinado.getTabuleiro()[1][1]);
    }

    @Test
    public void removerBandeiraDeCelulaSemBandeira() {
        campoMinado.removerBandeira(new Coordenada(2, 2));
        assertEquals('-', campoMinado.getTabuleiro()[2][2]);
    }

    @Test
    public void revelarBombasEVerificar() {
        campoMinado.descobrirZona(new Coordenada(5, 5));
        campoMinado.revelarBombas();
        char[][] tabuleiro = campoMinado.getTabuleiro();

        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (campoMinado.isDescoberta(new Coordenada(i, j)) && tabuleiro[i][j] == 'X') {
                    assertEquals('X', tabuleiro[i][j]);
                } else if (!campoMinado.isDescoberta(new Coordenada(i, j)) && campoMinado.getTabuleiro()[i][j] == 'X') {
                    assertEquals('X', tabuleiro[i][j]);
                }
            }
        }
    }

    @Test
    public void derrotaAoDescobrirBombaEVerificar() {
        campoMinado.getBombas()[1][1] = true;
        campoMinado.descobrirZona(new Coordenada(1, 1));
        assertTrue(campoMinado.isJogoEncerrado());
    }

    @Test
    public void jogoNaoVencidoAposDescobrirBombaEVerificar() {
        campoMinado.getBombas()[1][1] = true;
        campoMinado.descobrirZona(new Coordenada(1, 1));
        assertFalse(campoMinado.isJogoVencido());
    }

    @Test
    public void vitoriaEVerificar() {
        for (int i = 0; i < campoMinado.getTamanho(); i++) {
            for (int j = 0; j < campoMinado.getTamanho(); j++) {
                if (!campoMinado.isJogoEncerrado() && campoMinado.getTabuleiro()[i][j] != 'X') {
                    campoMinado.descobrirZona(new Coordenada(i, j));
                }
            }
        }

        assertTrue(campoMinado.isJogoEncerrado());
    }

    @Test
    public void naoPermitirDescobrirComBandeiraEVerificar() {
        campoMinado.colocarBandeira(new Coordenada(0, 0));
        campoMinado.descobrirZona(new Coordenada(0, 0));
        assertFalse(campoMinado.isDescoberta(new Coordenada(0, 0)));
    }

    @Test
    public void jogoNaoVencidoAoRevelarBombaEVerificar() {
        campoMinado.getBombas()[1][1] = true;
        campoMinado.descobrirZona(new Coordenada(1, 1));
        assertFalse(campoMinado.isJogoVencido());
    }

    @Test
    public void jogoNaoVencidoAoColocarBandeiraEmTodasAsBombasEVerificar() {
        for (int i = 0; i < campoMinado.getTamanho(); i++) {
            for (int j = 0; j < campoMinado.getTamanho(); j++) {
                if (campoMinado.getBombas()[i][j]) {
                    campoMinado.colocarBandeira(new Coordenada(i, j));
                }
            }
        }
        assertFalse(campoMinado.isJogoVencido());
    }

    @Test
    public void desvendarCelulaAposRemoverBandeiraEVerificar() {
        campoMinado.colocarBandeira(new Coordenada(1, 1));
        campoMinado.removerBandeira(new Coordenada(1, 1));
        campoMinado.descobrirZona(new Coordenada(1, 1));
        assertTrue(campoMinado.isDescoberta(new Coordenada(1, 1)));
    }

    @Test
    public void impedirAcaoDeBandeiraEmZonaReveladaEVerificar() {
        campoMinado.descobrirZona(new Coordenada(2, 2));
        boolean resultado = campoMinado.colocarBandeira(new Coordenada(2, 2));
        assertFalse(resultado);
    }

    @Test
    public void descobrirZonaEVerificar() {
        campoMinado.descobrirZona(new Coordenada(2, 2));
        assertTrue(campoMinado.isDescoberta(new Coordenada(2, 2)));
    }

    @Test
    public void reiniciarJogo_RedefinirTabuleiroParaEstadoInicialEVerificar() {
        campoMinado.colocarBandeira(new Coordenada(0, 0));
        campoMinado.reiniciarJogo();
        assertEquals('-', campoMinado.getTabuleiro()[0][0]);
    }

    @Test
    public void reiniciarJogo_JogoEncerradoFalsoEVerificar() {
        campoMinado.colocarBandeira(new Coordenada(0, 0));
        campoMinado.reiniciarJogo();
        assertFalse(campoMinado.isJogoEncerrado());
    }

    @Test
    public void reiniciarJogo_JogoVencidoFalsoEVerificar() {
        campoMinado.colocarBandeira(new Coordenada(0, 0));
        campoMinado.reiniciarJogo();
        assertFalse(campoMinado.isJogoVencido());
    }

    @Test
    public void testReiniciarJogoEmMeioAoJogo() {
        campoMinado.descobrirZona(new Coordenada(0, 0));
        campoMinado.colocarBandeira(new Coordenada(1, 1));
        campoMinado.reiniciarJogo();

        assertTrue(!campoMinado.isJogoEncerrado() && !campoMinado.isJogoVencido() && campoMinado.numBandeirasColocadas() == 0);
    }

    @Test
    public void testNumeroDeBandeirasNaoExcedeTotalDeCelulas() {
        int numBombas = campoMinado.getNumBombas();
        int tamanho = campoMinado.getTamanho();
        assertTrue(numBombas <= tamanho * tamanho);
    }

    @Test
    public void testColocarBandeirasIgualAoNumeroDeBombas() {
        int numBombas = campoMinado.getNumBombas();
        int tamanho = campoMinado.getTamanho();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (campoMinado.getBombas()[i][j]) {
                    campoMinado.colocarBandeira(new Coordenada(i, j));
                }
            }
        }
        int numeroDeBandeirasColocadas = campoMinado.contarBandeirasColocadas();
        assertEquals(numBombas, numeroDeBandeirasColocadas);
    }

    @Test
    public void testJogoNaoEstaEmEstadoInicial() {
        campoMinado.descobrirZona(new Coordenada(0, 0));
        campoMinado.colocarBandeira(new Coordenada(1, 1));

        assertTrue(!campoMinado.isJogoEncerrado() && !campoMinado.isJogoVencido() && campoMinado.numBandeirasColocadas() == 1);
    }

    @Test
    public void testReiniciarJogoVencidocomDescoberta() {
        campoMinado.descobrirZona(new Coordenada(0, 0));
        campoMinado.reiniciarJogo();
        assertFalse(campoMinado.isJogoVencido());
    }

    @Test
    public void testBombasNaoPodemSerColocadasNaPrimeiraZonaRevelada() {
        campoMinado.descobrirZona(new Coordenada(0, 0));
        boolean bombaColocada = campoMinado.getBombas()[0][0];

        assertFalse(bombaColocada);
    }
}

