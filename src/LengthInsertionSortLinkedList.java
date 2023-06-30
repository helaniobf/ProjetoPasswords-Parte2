import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class LengthInsertionSortLinkedList {
    public static void main(String[] args) {
        try {
            medioCaso("data/passwords_formated_data.csv");
            melhorCaso("sort/passwords_length_insertionSort_medioCaso.csv");
            inverter("sort/passwords_length_insertionSort_melhorCaso.csv");
            piorCaso("sort/passwords_length_insertionSort_inverter.csv");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void medioCaso(String arquivoMedioCaso) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoMedioCaso));

        int linhas = 0;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            linhas++;
        }

        LinkedListNode head = null;
        scanner = new Scanner(new File(arquivoMedioCaso));
        scanner.nextLine(); // pular a primeira linha
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            String password = valores[1];
            int length = Integer.parseInt(valores[2]);
            String data = valores[3];
            String passClass = valores[4];
            LinkedListNode newNode = new LinkedListNode(password, length, data, passClass);
            if (head == null) {
                head = newNode;
            } else {
                LinkedListNode current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
        }

        head = insertionSort(head);

        // Impressão das senhas ordenadas
        PrintWriter writer = new PrintWriter("sort/passwords_length_insertionSort_medioCaso.csv", "UTF-8");
        writer.print(",password,length,data,class\n");
        int i = 0;
        LinkedListNode current = head;
        while (current != null) {
            writer.println(i + "," + current.password + "," + current.length + "," + current.data + "," + current.passClass);
            current = current.next;
            i++;
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

        LinkedListNode head = null;
        scanner = new Scanner(new File(arquivoMelhorCaso));
        scanner.nextLine(); // pular a primeira linha
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            String password = valores[1];
            int length = Integer.parseInt(valores[2]);
            String data = valores[3];
            String passClass = valores[4];
            LinkedListNode newNode = new LinkedListNode(password, length, data, passClass);
            if (head == null) {
                head = newNode;
            } else {
                LinkedListNode current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
        }

        head = insertionSort(head);

        // Impressão das senhas ordenadas
        PrintWriter writer = new PrintWriter("sort/passwords_length_insertionSort_melhorCaso.csv", "UTF-8");
        writer.print(",password,length,data,class\n");
        int i = 0;
        LinkedListNode current = head;
        while (current != null) {
            writer.println(i + "," + current.password + "," + current.length + "," + current.data + "," + current.passClass);
            current = current.next;
            i++;
        }
        writer.close();
    }

    public static void inverter(String arquivoInverter) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scanner = new Scanner(new File(arquivoInverter));

        int linhas = 0;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            linhas++;
        }

        LinkedListNode head = null;
        scanner = new Scanner(new File(arquivoInverter));
        scanner.nextLine(); // pular a primeira linha
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            String password = valores[1];
            int length = Integer.parseInt(valores[2]);
            String data = valores[3];
            String passClass = valores[4];
            LinkedListNode newNode = new LinkedListNode(password, length, data, passClass);

            // Inserir o novo nó na posição correta da lista
            if (head == null || length >= head.length) {
                newNode.next = head;
                head = newNode;
            } else {
                LinkedListNode current = head;
                while (current.next != null && length < current.next.length) {
                    current = current.next;
                }
                newNode.next = current.next;
                current.next = newNode;
            }
        }

        // Impressão das senhas ordenadas
        PrintWriter writer = new PrintWriter("sort/passwords_length_insertionSort_inverter.csv", "UTF-8");
        writer.print(",password,length,data,class\n");
        int i = 0;
        LinkedListNode current = head;
        while (current != null) {
            writer.println(i + "," + current.password + "," + current.length + "," + current.data + "," + current.passClass);
            current = current.next;
            i++;
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

        LinkedListNode head = null;
        scanner = new Scanner(new File(arquivoPiorCaso));
        scanner.nextLine(); // pular a primeira linha
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            String password = valores[1];
            int length = Integer.parseInt(valores[2]);
            String data = valores[3];
            String passClass = valores[4];
            LinkedListNode newNode = new LinkedListNode(password, length, data, passClass);
            if (head == null) {
                head = newNode;
            } else {
                LinkedListNode current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
        }

        head = insertionSort(head);

        // Impressão das senhas ordenadas
        PrintWriter writer = new PrintWriter("sort/passwords_length_insertionSort_piorCaso.csv", "UTF-8");
        writer.print(",password,length,data,class\n");
        int i = 0;
        LinkedListNode current = head;
        while (current != null) {
            writer.println(i + "," + current.password + "," + current.length + "," + current.data + "," + current.passClass);
            current = current.next;
            i++;
        }
        writer.close();
    }

    public static LinkedListNode insertionSort(LinkedListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        LinkedListNode sortedHead = null;
        LinkedListNode unsorted = head;

        while (unsorted != null) {
            LinkedListNode current = unsorted;
            unsorted = unsorted.next;

            if (sortedHead == null || current.length < sortedHead.length) {
                current.next = sortedHead;
                sortedHead = current;
            } else {
                LinkedListNode search = sortedHead;
                while (search.next != null && current.length >= search.next.length) {
                    search = search.next;
                }
                current.next = search.next;
                search.next = current;
            }
        }

        return sortedHead;
    }

    private static class LinkedListNode {
        String password;
        int length;
        String data;
        String passClass;
        LinkedListNode next;

        public LinkedListNode(String password, int length, String data, String passClass) {
            this.password = password;
            this.length = length;
            this.data = data;
            this.passClass = passClass;
            this.next = null;
        }
    }
}