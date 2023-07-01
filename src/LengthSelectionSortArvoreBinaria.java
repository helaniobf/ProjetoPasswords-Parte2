import java.io.*;
import java.util.Scanner;

class ArvoreBinariaNode {
    String[] value;
    ArvoreBinariaNode left;
    ArvoreBinariaNode right;

    ArvoreBinariaNode(String[] value) {
        this.value = value;
        left = null;
        right = null;
    }
}

public class LengthSelectionSortArvoreBinaria {
    static ArvoreBinariaNode insertNode(ArvoreBinariaNode root, String[] value) {
        if (root == null) {
            return new ArvoreBinariaNode(value);
        }

        int tam1 = Integer.parseInt(value[2]);
        int tam2 = Integer.parseInt(root.value[2]);

        if (tam1 > tam2) {
            root.right = insertNode(root.right, value);
        } else {
            root.left = insertNode(root.left, value);
        }

        return root;
    }

    static void inOrderTraversal(ArvoreBinariaNode root, PrintWriter writer) {
        if (root == null) {
            return;
        }

        inOrderTraversal(root.left, writer);
        writer.println(root.value[1] + "," + root.value[2] + "," + root.value[3] + "," + root.value[4]);
        inOrderTraversal(root.right, writer);
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        File arquivoData = new File("data/passwords_formated_data.csv");
        String arquivoPassClass = arquivoData.getAbsolutePath();

        File arquivoMelhor = new File("sort/passwords_length_selectionSort_medioCaso.csv");
        String arquivoMelhorCaso = arquivoMelhor.getAbsolutePath();

        File arquivoPior = new File("sort/passwords_length_selectionSort_melhorCasoCrescente.csv");
        String arquivoPiorCaso = arquivoPior.getAbsolutePath();

        long tempoInicial = System.currentTimeMillis();
        medioCaso(arquivoPassClass); // Ordena o aleatório em ordem crescente
        long tempoFinal = System.currentTimeMillis();
        System.out.printf("%.3fs%n", (tempoFinal - tempoInicial) / 1000f);

        long tempoInicial2 = System.currentTimeMillis();
        melhorCaso(arquivoMelhorCaso); // Verifica o já ordenado
        long tempoFinal2 = System.currentTimeMillis();
        System.out.printf("%.3fs%n", (tempoFinal2 - tempoInicial2) / 1000d);

        inverter(arquivoMelhorCaso); // Cria um arquivo em ordem Crescente
        long tempoInicial3 = System.currentTimeMillis();
        piorCaso(arquivoPiorCaso); // Ordena o arquivo que está em ordem decrescente
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

        String[][] passwords = new String[linhas][5];
        scanner = new Scanner(new File(arquivoPassClass));
        scanner.nextLine(); // pular a primeira linha
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // expressão regular para separar apenas vírgulas fora de aspas
            passwords[i][0] = valores[0];
            passwords[i][1] = valores[1];
            passwords[i][2] = valores[2];
            passwords[i][3] = valores[3];
            passwords[i][4] = valores[4];
            i++;
        }

        ArvoreBinariaNode root = null;

        for (i = 0; i < linhas; i++) {
            root = insertNode(root, passwords[i]);
        }

        PrintWriter writer = new PrintWriter("sort/passwords_length_selectionSort_medioCaso.csv", "UTF-8");
        writer.print("password,length,data,class\n");
        inOrderTraversal(root, writer);
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

        String[][] passwords = new String[linhas][5];
        scanner = new Scanner(new File(arquivoMelhorCaso));
        scanner.nextLine(); // pular a primeira linha
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // expressão regular para separar apenas vírgulas fora de aspas
            passwords[i][0] = valores[0];
            passwords[i][1] = valores[1];
            passwords[i][2] = valores[2];
            passwords[i][3] = valores[3];
            passwords[i][4] = valores[4];
            i++;
        }

        ArvoreBinariaNode root = null;

        for (i = 0; i < linhas; i++) {
            root = insertNode(root, passwords[i]);
        }

        PrintWriter writer = new PrintWriter("sort/passwords_length_selectionSort_melhorCaso.csv", "UTF-8");
        writer.print("password,length,data,class\n");
        inOrderTraversal(root, writer);
        writer.close();
    }

    public static void inverter(String arquivoMelhorCaso) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoMelhorCaso));

        int linhas = 0;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            linhas++;
        }

        String[][] passwords = new String[linhas][5];
        scanner = new Scanner(new File(arquivoMelhorCaso));
        scanner.nextLine(); // pular a primeira linha
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // expressão regular para separar apenas vírgulas fora de aspas
            passwords[i][0] = valores[0];
            passwords[i][1] = valores[1];
            passwords[i][2] = valores[2];
            passwords[i][3] = valores[3];
            passwords[i][4] = valores[4];
            i++;
        }

        ArvoreBinariaNode root = null;

        for (i = 0; i < linhas; i++) {
            root = insertNode(root, passwords[i]);
        }

        PrintWriter writer = new PrintWriter("sort/passwords_length_selectionSort_melhorCasoCrescente.csv", "UTF-8");
        writer.print("password,length,data,class\n");
        inOrderTraversal(root, writer);
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

        String[][] passwords = new String[linhas][5];
        scanner = new Scanner(new File(arquivoPiorCaso));
        scanner.nextLine(); // pular a primeira linha
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // expressão regular para separar apenas vírgulas fora de aspas
            passwords[i][0] = valores[0];
            passwords[i][1] = valores[1];
            passwords[i][2] = valores[2];
            passwords[i][3] = valores[3];
            passwords[i][4] = valores[4];
            i++;
        }

        ArvoreBinariaNode root = null;

        for (i = 0; i < linhas; i++) {
            root = insertNode(root, passwords[i]);
        }

        PrintWriter writer = new PrintWriter("sort/passwords_length_selectionSort_piorCaso.csv", "UTF-8");
        writer.print("password,length,data,class\n");
        inOrderTraversal(root, writer);
        writer.close();
    }
}
