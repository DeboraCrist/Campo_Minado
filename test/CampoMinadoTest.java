import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CampoMinadoTest {
    private CampoMinado campoMinado;

    private static final double ACCEPTABLE_ERROR_PERCENTAGE = 5.0;
    @Before
    public void setUp() {
        campoMinado = new CampoMinado(1); // Use 1 para fácil, 2 para intermediário, 3 para difícil
    }

    @Test
    public void nivelFacil() {
        assertEquals(8, campoMinado.getTamanho());
    }

    @Test
    public void nivelFacil2() {
        assertEquals(10, campoMinado.getNumBombas());
    }
    

    @Test
    public void nivelIntermediario() {
        campoMinado = new CampoMinado(2);
        assertEquals(10, campoMinado.getTamanho());
    }

     @Test
    public void nivelIntermediario2() {
        campoMinado = new CampoMinado(2);
        assertEquals(30, campoMinado.getNumBombas());
    }

    @Test
    public void nivelDificil() {
        campoMinado = new CampoMinado(3);
        assertEquals(24, campoMinado.getTamanho());
    }

     @Test
    public void nivelDificil2() {
        campoMinado = new CampoMinado(3);
        assertEquals(100, campoMinado.getNumBombas());
    }

    @Test
    public void escolherNivelDeDificuldade() {
        assertEquals(1, campoMinado.getNivelDeDificuldade());
    }

    @Test
    public void escolherNivelDeDificuldade2() {
        campoMinado = new CampoMinado(2);
        assertEquals(2, campoMinado.getNivelDeDificuldade());
    }

    @Test
    public void escolherNivelDeDificuldade3() {
        campoMinado = new CampoMinado(3);
        assertEquals(3, campoMinado.getNivelDeDificuldade());
    }

    @Test
    public void jogoNaoEncerradoNaInicializacao() {
        assertFalse(campoMinado.isJogoEncerrado());
    }
    
    @Test
    public void tabuleiroInicializadoComHifens() {
        char[][] tabuleiro = campoMinado.getTabuleiro();
        for (char[] row : tabuleiro) {
            for (char cell : row) {
                assertEquals('-', cell);
            }
        }
    }
    


    @Test
    public void zonasCobertasIniciaisSemBandeiras() {
        char[][] tabuleiroInicial = campoMinado.getTabuleiro();

        for (int i = 0; i < campoMinado.getTamanho(); i++) {
            for (int j = 0; j < campoMinado.getTamanho(); j++) {
                assertNotEquals('P', tabuleiroInicial[i][j]);
            }
        }
    }

    @Test
    public void distribuicaoAleatoriaBombasNivelFacil() {
        int numBombasEsperado = campoMinado.getNumBombas();
        double margemErroPercentualAceitavel = ACCEPTABLE_ERROR_PERCENTAGE / 100.0 * numBombasEsperado;

        int bombasColocadas = 0;
        for (int x = 0; x < campoMinado.getTamanho(); x++) {
            for (int y = 0; y < campoMinado.getTamanho(); y++) {
                if (campoMinado.getBombas()[x][y]) {
                    bombasColocadas++;
                }
            }
        }

        assertTrue("Número de bombas fora da margem de erro aceitável para o nível fácil",
            bombasColocadas >= numBombasEsperado - margemErroPercentualAceitavel &&
            bombasColocadas <= numBombasEsperado + margemErroPercentualAceitavel);
    }

    @Test
    public void distribuicaoAleatoriaBombasNivelIntermediario() {
        campoMinado = new CampoMinado(2); 
        int numBombasEsperado = campoMinado.getNumBombas();
        double margemErroPercentualAceitavel = ACCEPTABLE_ERROR_PERCENTAGE / 100.0 * numBombasEsperado;

        int bombasColocadas = 0;
        for (int x = 0; x < campoMinado.getTamanho(); x++) {
            for (int y = 0; y < campoMinado.getTamanho(); y++) {
                if (campoMinado.getBombas()[x][y]) {
                    bombasColocadas++;
                }
            }
        }

        assertTrue("Número de bombas fora da margem de erro aceitável para o nível intermediário",
            bombasColocadas >= numBombasEsperado - margemErroPercentualAceitavel &&
            bombasColocadas <= numBombasEsperado + margemErroPercentualAceitavel);
    }

    @Test
    public void distribuicaoAleatoriaBombasNivelDificil() {
        campoMinado = new CampoMinado(3);
        int numBombasEsperado = campoMinado.getNumBombas();
        double margemErroPercentualAceitavel = ACCEPTABLE_ERROR_PERCENTAGE / 100.0 * numBombasEsperado;

        int bombasColocadas = 0;
        for (int x = 0; x < campoMinado.getTamanho(); x++) {
            for (int y = 0; y < campoMinado.getTamanho(); y++) {
                if (campoMinado.getBombas()[x][y]) {
                    bombasColocadas++;
                }
            }
        }

        assertTrue("Número de bombas fora da margem de erro aceitável para o nível difícil",
            bombasColocadas >= numBombasEsperado - margemErroPercentualAceitavel &&
            bombasColocadas <= numBombasEsperado + margemErroPercentualAceitavel);
    }


    @Test
    public void testNumeroDeBandeirasNaoExcedeTotalDeCelulas() {
        int numBombas = campoMinado.getNumBombas();
        int tamanho = campoMinado.getTamanho();
        assertTrue(numBombas <= tamanho * tamanho);
    }

}
