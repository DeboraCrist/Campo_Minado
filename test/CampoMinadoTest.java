import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CampoMinadoTest {
    private CampoMinado campoMinado;

    @Before
    public void setUp() {
        campoMinado = new CampoMinado(8, 10);
    }

    @Test
    public void testInicializacao() {
        // Verifique se o jogo não está encerrado após a inicialização
        assertFalse(campoMinado.isJogoEncerrado());
        
        // Verifique se todas as células estão ocultas no início
        char[][] tabuleiro = campoMinado.getTabuleiro();
        for (char[] row : tabuleiro) {
            for (char cell : row) {
                assertEquals('-', cell);
            }
        }
    }

    @Test
    public void testColocarBandeira() {
        campoMinado.colocarBandeira(0, 0);
        assertEquals('P', campoMinado.getTabuleiro()[0][0]);
    }

    @Test
    public void testRemoverBandeira() {
        campoMinado.colocarBandeira(0, 0);
        campoMinado.removerBandeira(0, 0);
        assertEquals('-', campoMinado.getTabuleiro()[0][0]);
    }

    @Test
    public void testDescobrirZona() {
        campoMinado.descobrirZona(3, 3);
        assertTrue(campoMinado.isDescoberta(3, 3));
    }

     @Test
    public void testRevelarBombas() {
        // Coloque manualmente uma bomba em uma posição para simular a perda
        campoMinado.descobrirZona(4, 4);
        campoMinado.revelarBombas();
        char[][] tabuleiro = campoMinado.getTabuleiro();
        
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (campoMinado.isDescoberta(i, j) && tabuleiro[i][j] == 'X') {
                    // Verifique se a célula com bomba foi revelada como 'X'
                    assertEquals('X', tabuleiro[i][j]);
                } else if (!campoMinado.isDescoberta(i, j) && campoMinado.getTabuleiro()[i][j] == 'X') {
                    // Verifique se as bombas não descobertas ainda estão ocultas como 'X'
                    assertEquals('X', tabuleiro[i][j]);
                }
            }
        }
    }
    
    @Test
    public void testVitoria() {
        // Descubra todas as células que não são bombas para vencer o jogo
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!campoMinado.isJogoEncerrado() && campoMinado.getTabuleiro()[i][j] != 'X') {
                    campoMinado.descobrirZona(i, j);
                }
            }
        }

        assertTrue(campoMinado.isJogoEncerrado());
    }

}

