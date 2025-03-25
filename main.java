package quiz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Pergunta { //ESTÁ É MINHA CLASSE DE PERGUNTA, CRIEI ELA POIS
    String enunciado;
    List<String> alternativas;
    String respostaCorreta;

    public Pergunta(String enunciado, List<String> alternativas, String respostaCorreta) {
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.respostaCorreta = respostaCorreta;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Pergunta> perguntas = new ArrayList<>();
        Scanner ler = new Scanner(System.in);
        int pontuacao = 0; // Contador de pontos

        try (BufferedReader leitura = new BufferedReader(new FileReader("M:/UNIFUCAMP/Java/quiz/arquivo_quiz.csv"))) {
            String line;

            while ((line = leitura.readLine()) != null) {
                String[] partes = line.split("\\|");

                if (partes.length == 5) {
                    String enunciado = partes[0];
                    String respostaCorreta = partes[1];

                    List<String> alternativas = new ArrayList<>();
                    alternativas.add(partes[1]); // Resposta correta
                    alternativas.add(partes[2]);
                    alternativas.add(partes[3]);
                    alternativas.add(partes[4]);

                    Collections.shuffle(alternativas); // Embaralha as alternativas

                    perguntas.add(new Pergunta(enunciado, alternativas, respostaCorreta));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Embaralha as perguntas
        Collections.shuffle(perguntas);

        // Exibe as perguntas e verifica a resposta do usuário
        System.out.println("🔹 Começando o jogo! Boa sorte!\n");

        for (Pergunta pergunta : perguntas) {
            System.out.println(pergunta.enunciado);

            for (int i = 0; i < pergunta.alternativas.size(); i++) {
                System.out.println((i + 1) + ". " + pergunta.alternativas.get(i));
            }

            System.out.print("\nEscolha uma opção: ");
            int escolha;

            // Verifica se a entrada é válida
            while (true) {
                try {
                    escolha = Integer.parseInt(ler.nextLine());
                    if (escolha >= 1 && escolha <= 4) break;
                    System.out.print("Opção inválida! Escolha entre 1 e 4: ");
                } catch (NumberFormatException e) {
                    System.out.print("Entrada inválida! Digite um número entre 1 e 4: ");
                }
            }

            // Pega a alternativa escolhida
            String respostaEscolhida = pergunta.alternativas.get(escolha - 1);

            // Compara com a resposta correta
            if (respostaEscolhida.equals(pergunta.respostaCorreta)) {
                System.out.println("Resposta correta! 🎉\n");
                pontuacao++;
            } else {
                System.out.println("Resposta errada:\nA resposta correta era: " + pergunta.respostaCorreta + "\n");
            }
        }

        // Exibe a pontuação final
        System.out.println("Jogo encerrado! Você acertou " + pontuacao + " de " + perguntas.size() + " perguntas.");
        ler.close();
    }
}
