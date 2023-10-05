import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean jogarNovamente = true;

        while (jogarNovamente) {
            System.out.println("Bem-vindo ao Campo Minado!");
            System.out.println("Escolha o nível de dificuldade:");
            System.out.println("1 - Fácil (8x8 - 10 bombas)");
            System.out.println("2 - Intermediário (10x16 - 30 bombas)");
            System.out.println("3 - Difícil (24x24 - 100 bombas)");

            int nivelDificuldade = scanner.nextInt();

            CampoMinado jogo = new CampoMinado(nivelDificuldade);

            while (!jogo.isJogoEncerrado()) {
                mostrarTabuleiro(jogo.getTabuleiro());

                System.out.print("Digite a linha: ");
                int linha = scanner.nextInt() - 1;
                System.out.print("Digite a coluna: ");
                int coluna = scanner.nextInt() - 1;

                System.out.print("Escolha uma ação (D para descobrir, P para colocar bandeira, R para remover bandeira): ");
                String acao = scanner.next();

                try {
                    if (acao.equalsIgnoreCase("D")) {
                        jogo.descobrirZona(linha, coluna);
                    } else if (acao.equalsIgnoreCase("P")) {
                        jogo.colocarBandeira(linha, coluna);
                    } else if (acao.equalsIgnoreCase("R")) {
                        jogo.removerBandeira(linha, coluna);
                    } else {
                        System.out.println("Ação inválida. Use D, P ou R.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (jogo.isJogoEncerrado() && !jogo.isJogoVencido()) {
                System.out.println("Você perdeu! Fim de jogo.");
                mostrarTabuleiro(jogo.getTabuleiro());
            } else {
                System.out.println("Parabéns! Você venceu!");
            }

            System.out.print("Deseja jogar novamente? (S para Sim, qualquer outra tecla para sair): ");
            String resposta = scanner.next();
            jogarNovamente = resposta.equalsIgnoreCase("S");

            if (jogarNovamente) {
                System.out.println("Reiniciando o jogo...");
            }
        }

        System.out.println("Obrigado por jogar!");
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
