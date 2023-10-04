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
    
    @Test
    public void testJogadaValida() {

        assertFalse(campoMinado.isDescoberta(5, 2));

        campoMinado.descobrirZona(5, 2);

        assertTrue(campoMinado.isDescoberta(5, 2));
        assertFalse(campoMinado.isJogoEncerrado());
    }
    
   
}

