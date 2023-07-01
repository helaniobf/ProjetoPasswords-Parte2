import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class LengthQuickSortPilha {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        File arquivoData = new File("data/passwords_formated_data.csv");
        String arquivoPassClass = arquivoData.getAbsolutePath();

        File arquivoMelhor = new File("sort/passwords_length_quickSort_medioCaso.csv");
        String arquivoMelhorCaso = arquivoMelhor.getAbsolutePath();

        File arquivoPior = new File("sort/passwords_length_quickSort_melhorCasoCrescente.csv");
        String arquivoPiorCaso = arquivoPior.getAbsolutePath();

        long tempoInicial = System.currentTimeMillis();
        medioCaso(arquivoPassClass); // Ordena o aleatório em ordem decrescente
        long tempoFinal = System.currentTimeMillis();
        System.out.printf("%.3fs%n", (tempoFinal - tempoInicial) / 1000f);

        long tempoInicial2 = System.currentTimeMillis();
        melhorCaso(arquivoMelhorCaso); // Verifica o já ordenado
        long tempoFinal2 = System.currentTimeMillis();
        System.out.printf("%.3fs%n", (tempoFinal2 - tempoInicial2) / 1000d);

        inverter(arquivoMelhorCaso); // Cria um arquivo em ordem crescente
        long tempoInicial3 = System.currentTimeMillis();
        piorCaso(arquivoPiorCaso); // Ordena o arquivo que está em ordem crescente
        long tempoFinal3 = System.currentTimeMillis();
        System.out.printf("%.3fs%n", (tempoFinal3 - tempoInicial3) / 1000d);
    }

    public static void medioCaso(String arquivoPassClass) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoPassClass));

        int linhas = 0;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            linhas++;
        }

        String[][] senhas = new String[linhas][5];
        scanner = new Scanner(new File(arquivoPassClass));
        scanner.nextLine(); // pular a primeira linha
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // expressão regular para separar apenas vírgulas fora de aspas
            senhas[i][0] = valores[0];
            senhas[i][1] = valores[1];
            senhas[i][2] = valores[2];
            senhas[i][3] = valores[3];
            senhas[i][4] = valores[4];
            i++;
        }

        quicksortStack(senhas, 0, linhas - 1);

        // Impressão das senhas ordenadas
        PrintWriter writer = new PrintWriter("sort/passwords_length_quicksort_medioCaso.csv", "UTF-8");
        writer.print(",password,length,data,class\n");
        for (i = 0; i < linhas; i++) {
            writer.println(i + "," + senhas[i][1] + "," + senhas[i][2] + "," + senhas[i][3] + "," + senhas[i][4]);
        }
        writer.close();
    }

    public static void melhorCaso(String arquivoMelhorCaso) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoMelhorCaso));

        int linhas = 0;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            linhas++;
        }

        String[][] senhas = new String[linhas][5];
        scanner = new Scanner(new File(arquivoMelhorCaso));
        scanner.nextLine(); // pular a primeira linha
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // expressão regular para separar apenas vírgulas fora de aspas
            senhas[i][0] = valores[0];
            senhas[i][1] = valores[1];
            senhas[i][2] = valores[2];
            senhas[i][3] = valores[3];
            senhas[i][4] = valores[4];
            i++;
        }

        quicksortStack(senhas, 0, linhas - 1);

        // Impressão das senhas ordenadas
        PrintWriter writer = new PrintWriter("sort/passwords_length_quicksort_melhorCaso.csv", "UTF-8");
        writer.print(",password,length,data,class\n");
        for (i = 0; i < linhas; i++) {
            writer.println(i + "," + senhas[i][1] + "," + senhas[i][2] + "," + senhas[i][3] + "," + senhas[i][4]);
        }
        writer.close();
    }

    public static void piorCaso(String arquivoPiorCaso) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoPiorCaso));

        int linhas = 0;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            linhas++;
        }

        String[][] senhas = new String[linhas][5];
        scanner = new Scanner(new File(arquivoPiorCaso));
        scanner.nextLine(); // pular a primeira linha
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // expressão regular para separar apenas vírgulas fora de aspas
            senhas[i][0] = valores[0];
            senhas[i][1] = valores[1];
            senhas[i][2] = valores[2];
            senhas[i][3] = valores[3];
            senhas[i][4] = valores[4];
            i++;
        }

        quicksortStack(senhas, 0, linhas - 1);

        // Impressão das senhas ordenadas
        PrintWriter writer = new PrintWriter("sort/passwords_length_quicksort_piorCaso.csv", "UTF-8");
        writer.print(",password,length,data,class\n");
        for (i = 0; i < linhas; i++) {
            writer.println(i + "," + senhas[i][1] + "," + senhas[i][2] + "," + senhas[i][3] + "," + senhas[i][4]);
        }
        writer.close();
    }

    public static void quicksortStack(String[][] senhas, int inicio, int fim) {
        Stack<Integer> stack = new Stack<>();
        stack.push(inicio);
        stack.push(fim);

        while (!stack.isEmpty()) {
            fim = stack.pop();
            inicio = stack.pop();

            int pivo = particiona(senhas, inicio, fim);

            if (pivo - 1 > inicio) {
                stack.push(inicio);
                stack.push(pivo - 1);
            }

            if (pivo + 1 < fim) {
                stack.push(pivo + 1);
                stack.push(fim);
            }
        }
    }

    public static int particiona(String[][] senhas, int inicio, int fim) {
        String pivo = senhas[fim][1];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (senhas[j][1].length() <= pivo.length()) {
                i++;
                troca(senhas, i, j);
            }
        }

        troca(senhas, i + 1, fim);
        return i + 1;
    }

    public static void troca(String[][] senhas, int i, int j) {
        String[] temp = senhas[i];
        senhas[i] = senhas[j];
        senhas[j] = temp;
    }

    public static void inverter(String arquivoMelhorCaso) throws FileNotFoundException {
        File arquivoOriginal = new File(arquivoMelhorCaso);
        Scanner scanner = new Scanner(arquivoOriginal);

        int linhas = 0;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            linhas++;
        }

        String[][] senhas = new String[linhas][5];
        scanner = new Scanner(arquivoOriginal);
        scanner.nextLine(); // pular a primeira linha
        int i = linhas - 1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // expressão regular para separar apenas vírgulas fora de aspas
            senhas[i][0] = valores[0];
            senhas[i][1] = valores[1];
            senhas[i][2] = valores[2];
            senhas[i][3] = valores[3];
            senhas[i][4] = valores[4];
            i--;
        }

        // Impressão das senhas invertidas
        PrintWriter writer = new PrintWriter("sort/passwords_length_quicksort_melhorCasoCrescente.csv");
        writer.print(",password,length,data,class\n");
        for (i = 0; i < linhas; i++) {
            writer.println(i + "," + senhas[i][1] + "," + senhas[i][2] + "," + senhas[i][3] + "," + senhas[i][4]);
        }
        writer.close();
    }
}
