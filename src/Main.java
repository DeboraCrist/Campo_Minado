import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Bem-vindo ao Campo Minado!");
            System.out.print("Informe o tamanho do tabuleiro: ");
            int tamanho = scanner.nextInt();

            System.out.print("Informe o número de bombas: ");
            int numBombas = scanner.nextInt();

            CampoMinado jogo = new CampoMinado(tamanho, numBombas);

            while (!jogo.isJogoEncerrado()) {
                mostrarTabuleiro(jogo.getTabuleiro());

                System.out.print("Digite a linha: ");
                int linha = scanner.nextInt();
                System.out.print("Digite a coluna: ");
                int coluna = scanner.nextInt();

                System.out.print("Escolha uma ação (D para descobrir, P para colocar bandeira, R para remover bandeira): ");
                String acao = scanner.next();

                if (acao.equalsIgnoreCase("D")) {
                    jogo.descobrirZona(linha, coluna);
                } else if (acao.equalsIgnoreCase("P")) {
                    jogo.colocarBandeira(linha, coluna);
                } else if (acao.equalsIgnoreCase("R")) {
                    jogo.removerBandeira(linha, coluna);
                } else {
                    System.out.println("Ação inválida. Use D, P ou R.");
                }
            }

            if (jogo.isJogoEncerrado() && !jogo.isDescoberta(0, 0)) {
                System.out.println("Você perdeu! Fim de jogo.");
                mostrarTabuleiro(jogo.getTabuleiro());
            } else {
                System.out.println("Parabéns! Você venceu!");
            }
        }
    }

    private static void mostrarTabuleiro(char[][] tabuleiro) {
        System.out.println("Tabuleiro:");
        for (char[] linha : tabuleiro) {
            for (char celula : linha) {
                System.out.print(celula + " ");
            }
            System.out.println();
        }
    }
}
