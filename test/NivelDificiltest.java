import org.junit.Before;
import org.junit.Test;

import models.NivelDificuldade;

import static org.junit.Assert.*;

public class NivelDificiltest {
    @Before
    public void setUp() {
        new CampoMinado(NivelDificuldade.FACIL);
    }

    @Test
    public void escolherNivelDeDificuldade() {
        assertEquals(1, NivelDificuldade.FACIL.getNivel());
    }

    @Test
    public void escolherNivelDeDificuldade2() {
        new CampoMinado(NivelDificuldade.MEDIO);
        assertEquals(2, NivelDificuldade.MEDIO.getNivel());
    }

    @Test
    public void escolherNivelDeDificuldade3() {
        new CampoMinado(NivelDificuldade.DIFICIL);
        assertEquals(3, NivelDificuldade.DIFICIL.getNivel());
    }
}
