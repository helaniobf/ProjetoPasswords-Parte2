import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class LengthSelectionSortArvoreBinaria {

    // Node class for Binary Search Tree (BST)
    static class NodeLBT {
        String index;
        String password;
        String length;
        String data;
        String className;
        NodeLBT left;
        NodeLBT right;

        public NodeLBT(String index, String password, String length, String data, String className) {
            this.index = index;
            this.password = password;
            this.length = length;
            this.data = data;
            this.className = className;
            left = null;
            right = null;
        }
    }

    // Root node of the Binary Search Tree
    static NodeLBT root;

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // Replace the file paths with your actual file paths
        String arquivoPassClass = "data/passwords_formated_data.csv";
        String arquivoMelhorCaso = "sort/passwords_length_selectionSort_medioCaso.csv";
        String arquivoPiorCaso = "sort/passwords_length_selectionSort_invertido.csv";

        long tempoInicial = System.currentTimeMillis();
        medioCaso(arquivoPassClass);
        long tempoFinal = System.currentTimeMillis();
        System.out.printf("Medio Caso = %.3fs%n", (tempoFinal - tempoInicial) / 1000f);

        long tempoInicial2 = System.currentTimeMillis();
        melhorCaso(arquivoMelhorCaso);
        long tempoFinal2 = System.currentTimeMillis();
        System.out.printf("Melhor Caso = %.3fs%n", (tempoFinal2 - tempoInicial2) / 1000f);

        criarArquivoOrdenadoDecrescente(arquivoPassClass, "sort/passwords_length_selectionSort_invertido.csv");

        long tempoInicial3 = System.currentTimeMillis();
        piorCaso(arquivoPiorCaso);
        long tempoFinal3 = System.currentTimeMillis();
        System.out.printf("Pior Caso = %.3fs%n", (tempoFinal3 - tempoInicial3) / 1000f);
    }

    public static void medioCaso(String arquivoPassClass) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoPassClass));

        // Read the header line and skip it
        scanner.nextLine();

        // Replace the while loop to build the BST
        root = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            root = insert(root, valores[0], valores[1], valores[2], valores[3], valores[4]);
        }
        scanner.close();

        // Open the PrintWriter here
        PrintWriter writer = new PrintWriter("sort/passwords_length_selectionSort_medioCaso.csv", "UTF-8");
        writer.println(",password,length,data,class");
        int[] index = {0};
        inOrderTraversal(root, writer, index);
        writer.close();
    }

    // Method to insert a node into the BST based on password length
    public static NodeLBT insert(NodeLBT root, String index, String password, String length, String data, String className) {
        if (root == null) {
            return new NodeLBT(index, password, length, data, className);
        }

        int len1 = Integer.parseInt(length);
        int len2 = Integer.parseInt(root.length);

        if (len1 < len2) {
            root.left = insert(root.left, index, password, length, data, className);
        } else if (len1 > len2) {
            root.right = insert(root.right, index, password, length, data, className);
        } else {
            // Handle case where lengths are equal, sort by password lexicographically
            if (password.compareTo(root.password) < 0) {
                root.left = insert(root.left, index, password, length, data, className);
            } else {
                root.right = insert(root.right, index, password, length, data, className);
            }
        }
        return root;
    }

    // Method to perform in-order traversal of the BST and print the sorted passwords
    public static void inOrderTraversal(NodeLBT root, PrintWriter writer, int[] index) {
        if (root != null) {
            inOrderTraversal(root.left, writer, index);
            writer.println(index[0] + "," + root.password + "," + root.length + "," + root.data + "," + root.className);
            index[0]++;
            inOrderTraversal(root.right, writer, index);
        }
    }

    public static void melhorCaso(String arquivoMelhorCaso) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoMelhorCaso));

        // Read the header line and skip it
        scanner.nextLine();

        int numPasswords = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            numPasswords++;
        }
        scanner.close();

        String[] passwords = new String[numPasswords];

        Scanner scanner2 = new Scanner(new File(arquivoMelhorCaso));
        scanner2.nextLine();
        int i = 0;
        while (scanner2.hasNextLine()) {
            String line = scanner2.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            passwords[i] = valores[1];
            i++;
        }
        scanner2.close();

        // Call the selectionSort method to sort passwords
        selectionSort(passwords);

        // Open the PrintWriter here
        PrintWriter writer = new PrintWriter("sort/passwords_length_selectionSort_melhorCaso.csv", "UTF-8");
        writer.println(",password,length,data,class");
        int[] index = {0};
        inOrderTraversal(root, writer, index);
        writer.close();
    }

    public static void piorCaso(String arquivoPassClass) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoPassClass));

        // Read the header line and skip it
        scanner.nextLine();

        int numPasswords = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            numPasswords++;
        }
        scanner.close();

        String[] passwords = new String[numPasswords];

        Scanner scanner2 = new Scanner(new File(arquivoPassClass));
        scanner2.nextLine();
        int i = 0;
        while (scanner2.hasNextLine()) {
            String line = scanner2.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            passwords[i] = valores[1];
            i++;
        }
        scanner2.close();

        // Call the selectionSort method to sort passwords
        selectionSortWorstCase(passwords);

        // Open the PrintWriter here
        PrintWriter writer = new PrintWriter("sort/passwords_length_selectionSort_piorCaso.csv", "UTF-8");
        writer.println(",password,length,data,class");
        int[] index = {0};
        inOrderTraversal(root, writer, index);
        writer.close();
    }
    public static void criarArquivoOrdenadoDecrescente(String arquivoOriginal, String arquivoOrdenado) throws FileNotFoundException, UnsupportedEncodingException {
        // Ler o arquivo original e armazenar as senhas em um Binary Search Tree (BST)
        Scanner scanner = new Scanner(new File(arquivoOriginal));
        scanner.nextLine(); // Pular a linha do cabeçalho
        NodeLBT root = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            root = insert(root, valores[0], valores[1], valores[2], valores[3], valores[4]);
        }
        scanner.close();

        // Criar o arquivo ordenado de forma decrescente
        PrintWriter writer = new PrintWriter(arquivoOrdenado, "UTF-8");
        writer.println(",password,length,data,class");
        int[] index = {0};
        inOrderTraversalDesc(root, writer, index);
        writer.close();
    }

    // Método para percorrer em ordem decrescente a BST e escrever as senhas ordenadas no arquivo
    public static void inOrderTraversalDesc(NodeLBT root, PrintWriter writer, int[] index) {
        if (root != null) {
            inOrderTraversalDesc(root.right, writer, index);
            writer.println(index[0] + "," + root.password + "," + root.length + "," + root.data + "," + root.className);
            index[0]++;
            inOrderTraversalDesc(root.left, writer, index);
        }
    }

    // Selection Sort for passwords
    public static void selectionSort(String[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            String temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    // Selection Sort for worst case scenario
    public static void selectionSortWorstCase(String[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(arr[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }
            String temp = arr[maxIndex];
            arr[maxIndex] = arr[i];
            arr[i] = temp;
        }
    }
}
