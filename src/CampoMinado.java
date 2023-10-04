import java.util.Random;

public class CampoMinado {
    private int tamanho;
    private int numBombas;
    private char[][] tabuleiro;
    boolean[][] bombas;
    private boolean[][] descobertas;
    private int bombasRestantes;
    private boolean jogoEncerrado;
    private boolean jogoVencido;

    public CampoMinado(int tamanho, int numBombas) {
        this.tamanho = tamanho;
        this.numBombas = numBombas;
        this.tabuleiro = new char[tamanho][tamanho];
        this.bombas = new boolean[tamanho][tamanho];
        this.descobertas = new boolean[tamanho][tamanho];
        this.bombasRestantes = numBombas;
        this.jogoEncerrado = false;
        this.jogoVencido = false;
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                tabuleiro[i][j] = '-';
                bombas[i][j] = false;
                descobertas[i][j] = false;
            }
        }

        Random random = new Random();
        int bombasColocadas = 0;
        while (bombasColocadas < numBombas) {
            int x = random.nextInt(tamanho);
            int y = random.nextInt(tamanho);
            if (!bombas[x][y]) {
                bombas[x][y] = true;
                bombasColocadas++;
            }
        }
    }

    public void colocarBandeira(int x, int y) {
        if (!descobertas[x][y] && !jogoEncerrado) {
            tabuleiro[x][y] = 'P';
        }
    }

    public void removerBandeira(int x, int y) {
        if (tabuleiro[x][y] == 'P' && !jogoEncerrado) {
            tabuleiro[x][y] = '-';
        }
    }

    public void descobrirZona(int x, int y) {
        if (!descobertas[x][y] && !jogoEncerrado && tabuleiro[x][y] != 'P') {
            if (bombas[x][y]) {
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
        int bombasAdjacentes = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;
                if (newX >= 0 && newX < tamanho && newY >= 0 && newY < tamanho && bombas[newX][newY]) {
                    bombasAdjacentes++;
                }
            }
        }
        return bombasAdjacentes;
    }

    private boolean todasZonasNaoBombasDescobertas() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (!bombas[i][j] && !descobertas[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    void revelarBombas() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (bombas[i][j]) {
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
        return bombas[x][y];
    }
}


