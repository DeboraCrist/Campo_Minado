package models;

public enum NivelDificuldade {
    FACIL(1, 8, 10),
    MEDIO(2, 10, 30),
    DIFICIL(3, 24, 100);

    private int nivel;
    private int tamanho;
    private int numBombas;

    NivelDificuldade(int nivel, int tamanho, int numBombas) {
        this.nivel = nivel;
        this.tamanho = tamanho;
        this.numBombas = numBombas;
    }

    public int getNivel() {
        return nivel;
    }

    public int getTamanho() {
        return tamanho;
    }

    public int getNumBombas() {
        return numBombas;
    }

    public int getNivelDeDificuldade() {
        return getNivel();
    }
}
