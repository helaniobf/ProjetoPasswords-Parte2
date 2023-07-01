import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

class DateMonthNode {
    String[] password;
    DateMonthNode left, right;

    DateMonthNode(String[] password) {
        this.password = password;
        left = right = null;
    }
}

public class DateMonthSelectionSortArvoreBinaria {
    static DateMonthNode root;
    static int linhas;

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        File arquivoData = new File("data/passwords_formated_data.csv");
        String arquivoPassClass = arquivoData.getAbsolutePath();

        File arquivoMelhor = new File("sort/passwords_data_month_selectionSort_medioCaso.csv");
        String arquivoMelhorCaso = arquivoMelhor.getAbsolutePath();

        File arquivoPior = new File("sort/passwords_data_month_selectionSort_piorCaso.csv");
        String arquivoPiorCaso = arquivoPior.getAbsolutePath();

        long tempoInicial = System.currentTimeMillis();
        medioCaso(arquivoPassClass); // Ordena o aleatório em ordem crescente
        long tempoFinal = System.currentTimeMillis();
        System.out.printf("Medio Caso = %.3fs%n", (tempoFinal - tempoInicial) / 1000f);

        long tempoInicial2 = System.currentTimeMillis();
        melhorCaso(arquivoMelhorCaso); // Verifica o já ordenado
        long tempoFinal2 = System.currentTimeMillis();
        System.out.printf("Melhor Caso = %.3fs%n", (tempoFinal2 - tempoInicial2) / 1000d);

        inverter(arquivoMelhorCaso); // Cria um arquivo em ordem decrescente
        long tempoInicial3 = System.currentTimeMillis();
        piorCaso(arquivoPiorCaso); // Ordena o arquivo que está em ordem decrescente
        long tempoFinal3 = System.currentTimeMillis();
        System.out.printf("Pior Caso = %.3fs%n", (tempoFinal3 - tempoInicial3) / 1000d);
    }

    public static void medioCaso(String arquivoPassClass) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoPassClass));

        linhas = 0;
        scanner.nextLine(); // pular a primeira linha
        while (scanner.hasNextLine()) {
            linhas++;
            scanner.nextLine();
        }

        scanner.close();

        String[][] passwords = new String[linhas][4];
        scanner = new Scanner(new File(arquivoPassClass));
        scanner.nextLine(); // pular a primeira linha
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            passwords[i][0] = valores[1]; // password
            passwords[i][1] = valores[2]; // length
            passwords[i][2] = valores[3]; // data
            passwords[i][3] = valores[4]; // class
            i++;
        }

        scanner.close();

        root = null; // Reinicializar a raiz para cada caso
        // Construir a árvore binária
        for (i = 0; i < linhas; i++) {
            root = insert(root, passwords[i]);
        }

        PrintWriter writer = new PrintWriter("sort/passwords_data_month_selectionSort_medioCaso.csv", "UTF-8");
        writer.println(",password,length,data,class");
        inorderTraversal(root, writer, new int[]{0}); // Inicializar o índice como 0
        writer.close();
    }

    public static DateMonthNode insert(DateMonthNode root, String[] password) {
        if (root == null) {
            return new DateMonthNode(password);
        }

        String data = password[2];
        String rootData = root.password[2];

        if (data.compareTo(rootData) < 0) {
            root.left = insert(root.left, password);
        } else if (data.compareTo(rootData) > 0) {
            root.right = insert(root.right, password);
        } else {
            // Tratar o caso de igualdade das datas
            String month = "";
            if (password[2].length() >= 7) {
                month = password[2].substring(5, 7);
            }
            String rootMonth = "";
            if (root.password[2].length() >= 7) {
                rootMonth = root.password[2].substring(5, 7);
            }
            if (month.compareTo(rootMonth) < 0) {
                root.left = insert(root.left, password);
            } else {
                root.right = insert(root.right, password);
            }
        }

        return root;
    }

    public static void inorderTraversal(DateMonthNode root, PrintWriter writer, int[] index) {
        Stack<DateMonthNode> stack = new Stack<>();
        DateMonthNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            writer.println(index[0] + "," + current.password[0] + "," + current.password[1] + "," + current.password[2] + "," + current.password[3]);
            index[0]++;

            current = current.right;
        }
    }

    public static void melhorCaso(String arquivoMelhorCaso) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoMelhorCaso));

        linhas = 0;
        scanner.nextLine(); // pular a primeira linha
        while (scanner.hasNextLine()) {
            linhas++;
            scanner.nextLine();
        }

        scanner.close();

        PrintWriter writer = new PrintWriter("sort/passwords_data_month_selectionSort_melhorCaso.csv", "UTF-8");
        scanner = new Scanner(new File(arquivoMelhorCaso));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            writer.println(line);
        }
        scanner.close();
        writer.close();
    }

    public static void inverter(String arquivoMelhorCaso) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoMelhorCaso));
        PrintWriter writer = new PrintWriter("sort/passwords_data_month_selectionSort_piorCaso.csv", "UTF-8");

        ArrayList<String> lines = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }

        for (int i = lines.size() - 1; i >= 0; i--) {
            writer.println(lines.get(i));
        }

        scanner.close();
        writer.close();
    }

    public static void piorCaso(String arquivoPiorCaso) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoPiorCaso));

        linhas = 0;
        scanner.nextLine(); // pular a primeira linha
        while (scanner.hasNextLine()) {
            linhas++;
            scanner.nextLine();
        }

        scanner.close();

        String[][] passwords = new String[linhas][4];
        scanner = new Scanner(new File(arquivoPiorCaso));
        scanner.nextLine(); // pular a primeira linha
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            passwords[i][0] = valores[1]; // password
            passwords[i][1] = valores[2]; // length
            passwords[i][2] = valores[3]; // data
            passwords[i][3] = valores[4]; // class
            i++;
        }

        scanner.close();

        // Inverter o array de passwords
        for (int j = 0; j < linhas / 2; j++) {
            String[] temp = passwords[j];
            passwords[j] = passwords[linhas - 1 - j];
            passwords[linhas - 1 - j] = temp;
        }

        root = null; // Reinicializar a raiz para cada caso
        // Construir a árvore binária
        for (i = 0; i < linhas; i++) {
            root = insert(root, passwords[i]);
        }

        PrintWriter writer = new PrintWriter("sort/passwords_data_month_selectionSort_piorCaso.csv", "UTF-8");
        writer.println(",password,length,data,class");
        inorderTraversal(root, writer, new int[]{0}); // Inicializar o índice como 1
        writer.close();
    }
}
