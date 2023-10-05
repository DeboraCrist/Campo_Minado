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
    public void derrotaAoDescobrirBomba() {
        campoMinado.getBombas()[1][1] = true;
        campoMinado.descobrirZona(1, 1);
        assertTrue(campoMinado.isJogoEncerrado());
    }

    @Test
    public void jogoNaoVencidoAposDescobrirBomba() {
        campoMinado.getBombas()[1][1] = true;
        campoMinado.descobrirZona(1, 1);
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
    public void naoPermitirDescobrirComBandeira() {
        campoMinado.colocarBandeira(0, 0);
        campoMinado.descobrirZona(0, 0);
        assertFalse(campoMinado.isDescoberta(0, 0));
 
    }

    @Test
    public void jogoNaoVencidoAoRevelarBomba() {
        campoMinado.getBombas()[1][1] = true;
        campoMinado.descobrirZona(1, 1);
        assertFalse(campoMinado.isJogoVencido());
    }

    @Test
    public void jogoNaoVencidoAoColocarBandeiraEmTodasAsBombas() {
        for (int i = 0; i < campoMinado.getTamanho(); i++) {
            for (int j = 0; j < campoMinado.getTamanho(); j++) {
                if (campoMinado.getBombas()[i][j]) {
                    campoMinado.colocarBandeira(i, j);
                }
            }
        }
        assertFalse(campoMinado.isJogoVencido());
    }

    @Test
    public void desvendarCelulaAposRemoverBandeira() {
        campoMinado.colocarBandeira(1, 1); 
        campoMinado.removerBandeira(1, 1); 
        campoMinado.descobrirZona(1, 1); 
        assertTrue(campoMinado.isDescoberta(1, 1)); 
    }

    @Test
    public void impedirAcaoDeBandeiraEmZonaRevelada() {
        campoMinado.descobrirZona(2, 2);
        boolean resultado = campoMinado.colocarBandeira(2, 2);
        assertFalse(resultado);
    }

    @Test
    public void descobrirZona() {
        campoMinado.descobrirZona(2, 2);
        assertTrue(campoMinado.isDescoberta(2, 2));
    }

    @Test
    public void reiniciarJogo_RedefinirTabuleiroParaEstadoInicial() {
        campoMinado.colocarBandeira(0, 0); 
        campoMinado.reiniciarJogo();
        assertEquals('-', campoMinado.getTabuleiro()[0][0]);
    }

    @Test
    public void reiniciarJogo_JogoEncerradoFalso() {
        campoMinado.colocarBandeira(0, 0); 
        campoMinado.reiniciarJogo();
        assertFalse(campoMinado.isJogoEncerrado());
    }

    @Test
    public void reiniciarJogo_JogoVencidoFalso() {
        campoMinado.colocarBandeira(0, 0); 
        campoMinado.reiniciarJogo();
        assertFalse(campoMinado.isJogoVencido());
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
    public void reiniciarJogoVencidocomDescoberta() {
        campoMinado.descobrirZona(0, 0);
        campoMinado.reiniciarJogo();
        assertFalse(campoMinado.isJogoVencido());
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
                    campoMinado.colocarBandeira(i, j);
                }
            }
        }
        int numeroDeBandeirasColocadas = campoMinado.contarBandeirasColocadas();
        assertEquals(numBombas, numeroDeBandeirasColocadas);
    }

    @Test
    public void testReiniciarJogo() {
        // Execute algumas ações no jogo
        campoMinado.descobrirZona(0, 0);
        campoMinado.colocarBandeira(1, 1);

        // Verifique se o jogo não está em estado inicial
        assertFalse(campoMinado.isJogoEncerrado());

        // Reinicie o jogo
        campoMinado.reiniciarJogo();

        // Verifique se o jogo foi reiniciado
        assertFalse(campoMinado.isJogoEncerrado());
        assertFalse(campoMinado.isJogoVencido());
        assertEquals(0, campoMinado.numBandeirasColocadas());
        // Adicione mais verificações conforme necessário
    }

    @Test
    public void testReiniciarJogoEmMeioAoJogo() {
        // Execute algumas ações no jogo
        campoMinado.descobrirZona(0, 0);
        campoMinado.colocarBandeira(1, 1);

        // Verifique se o jogo não está em estado inicial
        assertFalse(campoMinado.isJogoEncerrado());

        // Reinicie o jogo em meio ao jogo
        campoMinado.reiniciarJogo();

        // Verifique se o jogo foi reiniciado
        assertFalse(campoMinado.isJogoEncerrado());
        assertFalse(campoMinado.isJogoVencido());
        assertEquals(0, campoMinado.numBandeirasColocadas());
        // Adicione mais verificações conforme necessário
    }



}


    


