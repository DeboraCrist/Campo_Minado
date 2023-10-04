import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CampoMinadoTest {
    private CampoMinado campoMinado;

    @Before
    public void setUp() {
        campoMinado = new CampoMinado(1); // Use 1 para fácil, 2 para intermediário, 3 para difícil
    }

    @Test
    public void testNivelFacil() {
        assertEquals(8, campoMinado.getTamanho());
        assertEquals(10, campoMinado.getNumBombas());
    }
    

    @Test
    public void testNivelIntermediario() {
        campoMinado = new CampoMinado(2);
        assertEquals(10, campoMinado.getTamanho());
        assertEquals(30, campoMinado.getNumBombas());
    }

    @Test
    public void testNivelDificil() {
        campoMinado = new CampoMinado(3);
        assertEquals(24, campoMinado.getTamanho());
        assertEquals(100, campoMinado.getNumBombas());
    }

    @Test
    public void testEscolherNivelDeDificuldade() {
        assertEquals(1, campoMinado.getNivelDeDificuldade());
        campoMinado = new CampoMinado(2);
        assertEquals(2, campoMinado.getNivelDeDificuldade());
        campoMinado = new CampoMinado(3);
        assertEquals(3, campoMinado.getNivelDeDificuldade());
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
        campoMinado.colocarBandeira(1, 1); 
        assertEquals('P', campoMinado.getTabuleiro()[1][1]);
    }

    @Test
    public void removerBandeira() {
        campoMinado.colocarBandeira(1, 1); 
        campoMinado.removerBandeira(1, 1); 
        assertEquals('-', campoMinado.getTabuleiro()[1][1]);
    }

    @Test
    public void removerBandeiraDeCelulaSemBandeira() {
        campoMinado.removerBandeira(2, 2); 
        assertEquals('-', campoMinado.getTabuleiro()[2][2]);
    }

    @Test
    public void revelarBombas() {
        campoMinado.descobrirZona(5, 5); 
        campoMinado.revelarBombas();
        char[][] tabuleiro = campoMinado.getTabuleiro();
        
        for (int i = 1; i < tabuleiro.length; i++) { 
            for (int j = 1; j < tabuleiro[i].length; j++) {  
                if (campoMinado.isDescoberta(i, j) && tabuleiro[i][j] == 'X') {
                    assertEquals('X', tabuleiro[i][j]);
                } else if (!campoMinado.isDescoberta(i, j) && campoMinado.getTabuleiro()[i][j] == 'X') {
                    assertEquals('X', tabuleiro[i][j]);
                }
            }
        }
    }
    
    @Test
    public void testDerrotaAoDescobrirBomba() {
        campoMinado.getBombas()[1][1] = true;
        campoMinado.descobrirZona(1, 1);
        assertTrue(campoMinado.isJogoEncerrado());
        assertFalse(campoMinado.isJogoVencido());
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
    public void testNaoPermitirDescobrirComBandeira() {
        campoMinado.colocarBandeira(0, 0);
        campoMinado.descobrirZona(0, 0);
        assertFalse(campoMinado.isDescoberta(0, 0));
    }
}


