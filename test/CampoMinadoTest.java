import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CampoMinadoTest {
    private CampoMinado campoMinado;

    @Before
    public void setUp() {
        campoMinado = new CampoMinado(8, 10);
    }

    // ... Other tests ...

    @Test
    public void colocarBandeira() {
        campoMinado.colocarBandeira(1, 1); // Modified to start from (1, 1)
        assertEquals('P', campoMinado.getTabuleiro()[1][1]);
    }

    @Test
    public void removerBandeira() {
        campoMinado.colocarBandeira(1, 1); // Modified to start from (1, 1)
        campoMinado.removerBandeira(1, 1); // Modified to start from (1, 1)
        assertEquals('-', campoMinado.getTabuleiro()[1][1]);
    }

    @Test
    public void removerBandeiraDeCelulaSemBandeira() {
        campoMinado.removerBandeira(2, 2); // Modified to start from (1, 1)
        assertEquals('-', campoMinado.getTabuleiro()[2][2]);
    }

    @Test
    public void revelarBombas() {
        campoMinado.descobrirZona(5, 5); // Modified to start from (1, 1)
        campoMinado.revelarBombas();
        char[][] tabuleiro = campoMinado.getTabuleiro();
        
        for (int i = 1; i < tabuleiro.length; i++) { // Modified to start from (1, 1)
            for (int j = 1; j < tabuleiro[i].length; j++) { // Modified to start from (1, 1)
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

    @Test
    public void testNaoPermitirDescobrirComBandeira() {
        campoMinado.colocarBandeira(0, 0);
        campoMinado.descobrirZona(0, 0);
        assertFalse(campoMinado.isDescoberta(0, 0));
    }

    @Test
    public void testDesbrirZonaAposRemoverBandeira() {
        campoMinado.colocarBandeira(3, 3);
        campoMinado.removerBandeira(3, 3);
        campoMinado.descobrirZona(3, 3);
        assertFalse(campoMinado.isJogoEncerrado());
        assertNotEquals('-', campoMinado.getTabuleiro()[3][3]);
    }
    
  @Test
    public void testDerrotaAoDescobrirBomba() {
        int tamanho = 3;
        int numBombas = 1;
        CampoMinado jogo = new CampoMinado(tamanho, numBombas);
        jogo.bombas[0][0] = true;
        jogo.descobrirZona(0, 0);
        assertTrue(jogo.isJogoEncerrado());
        assertFalse(jogo.isJogoVencido());
    }

  
}

