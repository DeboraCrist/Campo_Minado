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
}

