import java.util.Random;

public class CampoMinado {
    private int tamanho;
    private int numBombas;
    private char[][] tabuleiro;
    private boolean[][] bombas;
    private boolean[][] descobertas;
    private int bombasRestantes;
    private boolean jogoEncerrado;
    private boolean jogoVencido;
    private int nivelDeDificuldade; 

    public CampoMinado(int nivelDificuldade) {
        switch (nivelDificuldade) {
            case 1:
                this.tamanho = 8;
                this.numBombas = 10;
                break;
            case 2:
                this.tamanho = 10;
                this.numBombas = 30;
                break;
            case 3:
                this.tamanho = 24;
                this.numBombas = 100;
                break;
            default:
                throw new IllegalArgumentException("Nível de dificuldade inválido.");
        }
        this.tabuleiro = new char[tamanho][tamanho];
        setBombas(new boolean[tamanho][tamanho]);
        this.descobertas = new boolean[tamanho][tamanho];
        this.bombasRestantes = numBombas;
        this.jogoEncerrado = false;
        this.jogoVencido = false;
        this.nivelDeDificuldade = nivelDificuldade; 
        inicializarTabuleiro();
    }

    public boolean[][] getBombas() {
        return bombas;
    }

    public void setBombas(boolean[][] bombas) {
        this.bombas = bombas;
    }

    private void inicializarTabuleiro() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro[i][j] = '-';
                getBombas()[i][j] = false;
                descobertas[i][j] = false;
            }
        }

        Random random = new Random();
        int bombasColocadas = 0;
        while (bombasColocadas < numBombas) {
            int x = random.nextInt(tamanho);
            int y = random.nextInt(tamanho);
            if (!getBombas()[x][y]) {
                getBombas()[x][y] = true;
                bombasColocadas++;
            }
        }
    }

    public boolean colocarBandeira(int x, int y) {
        if (!descobertas[x][y] && !jogoEncerrado) {
            tabuleiro[x][y] = 'P';
            return true; 
        }
        return false; 
    }

    public void removerBandeira(int x, int y) {
        if (tabuleiro[x][y] == 'P' && !jogoEncerrado) {
            tabuleiro[x][y] = '-';
        }
    }

    public void descobrirZona(int x, int y) {
        if (!descobertas[x][y] && !jogoEncerrado && tabuleiro[x][y] != 'P') {
            if (getBombas()[x][y]) {
                jogoEncerrado = true;
                revelarBombas();
            } else {
                descobertas[x][y] = true;
                int bombasAdjacentes = contarBombasAdjacentes(x, y);
                tabuleiro[x][y] = Character.forDigit(bombasAdjacentes, 10);
    
                if (bombasRestantes == 0 && todasZonasNaoBombasDescobertas()) {
                    jogoEncerrado = true;
                    jogoVencido = true;
                }
            }
        }
    }
    

    private int contarBombasAdjacentes(int x, int y) {
        return 0;
    }

    public int contarBandeirasColocadas(CampoMinado campoMinado) {
        int numeroDeBandeirasColocadas = 0;
        for (int i = 0; i < campoMinado.getTamanho(); i++) {
            for (int j = 0; j < campoMinado.getTamanho(); j++) {
                if (campoMinado.getTabuleiro()[i][j] == 'P') {
                    numeroDeBandeirasColocadas++;
                }
            }
        }
        return numeroDeBandeirasColocadas;
    }
    

    private boolean todasZonasNaoBombasDescobertas() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (!getBombas()[i][j] && !descobertas[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    void revelarBombas() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (getBombas()[i][j]) {
                    tabuleiro[i][j] = 'X';
                }
            }
        }
    }

    public boolean isJogoEncerrado() {
        return jogoEncerrado;
    }

    public boolean isJogoVencido() {
        return jogoVencido;
    }

    public char[][] getTabuleiro() {
        return tabuleiro;
    }

    public boolean isDescoberta(int x, int y) {
        return descobertas[x][y];
    }

    public boolean ehBomba(int x, int y) {
        return getBombas()[x][y];
    }

    public int getNumBombas() {
        return numBombas;
    }

    public int getTamanho() {
        return tamanho;
    }
    public int getNivelDeDificuldade() {
        return nivelDeDificuldade;
    }

    public void reiniciarJogo() {
    this.tabuleiro = new char[tamanho][tamanho];
    setBombas(new boolean[tamanho][tamanho]);
    this.descobertas = new boolean[tamanho][tamanho];
    this.bombasRestantes = numBombas;
    this.jogoEncerrado = false;
    this.jogoVencido = false;
    inicializarTabuleiro();
}

    

 
    
}
