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
    public void inicializacao() {
        assertFalse(campoMinado.isJogoEncerrado());
        
        char[][] tabuleiro = campoMinado.getTabuleiro();
        for (char[] row : tabuleiro) {
            for (char cell : row) {
                assertEquals('-', cell);
            }
        }
    }

    @Test
    public void colocarBandeira() {
        campoMinado.colocarBandeira(0, 0);
        assertEquals('P', campoMinado.getTabuleiro()[0][0]);
    }

    @Test
    public void removerBandeira() {
        campoMinado.colocarBandeira(0, 0);
        campoMinado.removerBandeira(0, 0);
        assertEquals('-', campoMinado.getTabuleiro()[0][0]);
    }

    @Test
    public void removerBandeiraDeCelulaSemBandeira() {
        campoMinado.removerBandeira(1, 1); 
        assertEquals('-', campoMinado.getTabuleiro()[1][1]);
    }

     @Test
    public void revelarBombas() {
        campoMinado.descobrirZona(4, 4);
        campoMinado.revelarBombas();
        char[][] tabuleiro = campoMinado.getTabuleiro();
        
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[i].length; j++) {
                if (campoMinado.isDescoberta(i, j) && tabuleiro[i][j] == 'X') {
                    assertEquals('X', tabuleiro[i][j]);
                } else if (!campoMinado.isDescoberta(i, j) && campoMinado.getTabuleiro()[i][j] == 'X') {
                    assertEquals('X', tabuleiro[i][j]);
                }
            }
        }
    }
    
    @Test
    public void vitoria() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!campoMinado.isJogoEncerrado() && campoMinado.getTabuleiro()[i][j] != 'X') {
                    campoMinado.descobrirZona(i, j);
                }
            }
        }

        assertTrue(campoMinado.isJogoEncerrado());
    } 
    @Test
    public void testJogadaValida() {
        campoMinado.descobrirZona(0, 0);
        assertTrue(campoMinado.isDescoberta(0, 0));
        assertFalse(campoMinado.isJogoEncerrado());
    }

    @Test
    public void jogoIncompletoAteRevelarTodasAsZonasNaoBombas() {
        campoMinado.descobrirZona(0, 0);
        assertFalse(campoMinado.isJogoEncerrado());
    }
  
}
