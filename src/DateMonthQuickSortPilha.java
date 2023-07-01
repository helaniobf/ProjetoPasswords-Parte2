import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Stack;

public class DateMonthQuickSortPilha {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        File arquivoData = new File("data/passwords_formated_data.csv");
        String arquivoPassClass = arquivoData.getAbsolutePath();

        File arquivoMelhor = new File("sort/passwords_data_month_quickSort_medioCaso.csv");
        String arquivoMelhorCaso = arquivoMelhor.getAbsolutePath();

        File arquivoPior = new File("sort/passwords_data_month_quickSort_invertido.csv");
        String arquivoPiorCaso = arquivoPior.getAbsolutePath();

        // Caso Médio
        long tempoInicialMedio = System.currentTimeMillis();
        medioCaso(arquivoPassClass); // Ordena o arquivo em ordem crescente de data
        long tempoFinalMedio = System.currentTimeMillis();
        System.out.printf("Tempo de execução (Caso Médio): %.3fs%n", (tempoFinalMedio - tempoInicialMedio) / 1000f);

        // Melhor Caso
        long tempoInicialMelhor = System.currentTimeMillis();
        melhorCaso(arquivoMelhorCaso); // Ordena o arquivo já ordenado em ordem crescente de data
        long tempoFinalMelhor = System.currentTimeMillis();
        System.out.printf("Tempo de execução (Melhor Caso): %.3fs%n", (tempoFinalMelhor - tempoInicialMelhor) / 1000f);

        inverter(arquivoPassClass);
        // Pior Caso
        long tempoInicialPior = System.currentTimeMillis();
        piorCaso(arquivoPiorCaso); // Ordena o arquivo já ordenado em ordem decrescente de data
        long tempoFinalPior = System.currentTimeMillis();
        System.out.printf("Tempo de execução (Pior Caso): %.3fs%n", (tempoFinalPior - tempoInicialPior) / 1000f);
    }

    public static void medioCaso(String arquivoPassClass) throws FileNotFoundException, UnsupportedEncodingException {
        String[][] senhas = lerSenhas(arquivoPassClass);
        quicksortStack(senhas, 0, senhas.length - 1);

        // Impressão das senhas ordenadas
        PrintWriter writer = new PrintWriter("sort/passwords_data_month_quicksort_medioCaso.csv", "UTF-8");
        writer.println("password,length,data,class");
        for (int i = 0; i < senhas.length; i++) {
            writer.println(i + "," + senhas[i][0] + "," + senhas[i][1] + "," + senhas[i][2] + "," + senhas[i][3]);
        }
        writer.close();
    }

    public static void melhorCaso(String arquivoMelhorCaso) throws FileNotFoundException, UnsupportedEncodingException {
        String[][] senhas = lerSenhas(arquivoMelhorCaso);
        quicksortStack(senhas, 0, senhas.length - 1);

        // Impressão das senhas ordenadas
        PrintWriter writer = new PrintWriter("sort/passwords_data_month_quicksort_melhorCaso.csv", "UTF-8");
        writer.println("password,length,data,class");
        for (int i = 0; i < senhas.length; i++) {
            writer.println(i + "," + senhas[i][0] + "," + senhas[i][1] + "," + senhas[i][2] + "," + senhas[i][3]);
        }
        writer.close();
    }

    public static void piorCaso(String arquivoPiorCaso) throws FileNotFoundException, UnsupportedEncodingException {
        String[][] senhas = lerSenhas(arquivoPiorCaso);
        quicksortStack(senhas, 0, senhas.length - 1);

        // Impressão das senhas ordenadas
        PrintWriter writer = new PrintWriter("sort/passwords_data_month_quicksort_piorCaso.csv", "UTF-8");
        writer.println("password,length,data,class");
        for (int i = 0; i < senhas.length; i++) {
            writer.println(i + "," + senhas[i][0] + "," + senhas[i][1] + "," + senhas[i][2] + "," + senhas[i][3]);
        }
        writer.close();
    }

    public static void inverter(String arquivoPassClass) throws FileNotFoundException, UnsupportedEncodingException {
        String[][] senhas = lerSenhas(arquivoPassClass);
        quicksortStack(senhas, 0, senhas.length - 1);

        // Reverter a ordem das senhas
        for (int i = 0; i < senhas.length / 2; i++) {
            String[] temp = senhas[i];
            senhas[i] = senhas[senhas.length - 1 - i];
            senhas[senhas.length - 1 - i] = temp;
        }

        // Impressão das senhas ordenadas
        PrintWriter writer = new PrintWriter("sort/passwords_data_month_quicksort_invertido.csv", "UTF-8");
        writer.println("password,length,data,class");
        for (int i = 0; i < senhas.length; i++) {
            writer.println(i + "," + senhas[i][0] + "," + senhas[i][1] + "," + senhas[i][2] + "," + senhas[i][3]);
        }
        writer.close();
    }

    public static String[][] lerSenhas(String arquivoPassClass) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(arquivoPassClass));

        int linhas = 0;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            linhas++;
        }

        String[][] senhas = new String[linhas][4];
        scanner = new Scanner(new File(arquivoPassClass));
        scanner.nextLine(); // pular a primeira linha
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // expressão regular para separar apenas vírgulas fora de aspas
            senhas[i][0] = valores[1];
            senhas[i][1] = valores[2];
            senhas[i][2] = valores[3];
            senhas[i][3] = valores[4];
            i++;
        }

        return senhas;
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
        String pivo = senhas[fim][2];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (compararData(senhas[j][2], pivo) <= 0) {
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

    public static int compararData(String data1, String data2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date date1 = sdf.parse(data1);
            Date date2 = sdf.parse(data2);
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
