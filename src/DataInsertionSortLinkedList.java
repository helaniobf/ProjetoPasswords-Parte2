import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class Node {
    String password;
    int length;
    LocalDateTime data;
    String passwordClass;
    Node next;

    public Node(String password, int length, LocalDateTime data, String passwordClass) {
        this.password = password;
        this.length = length;
        this.data = data;
        this.passwordClass = passwordClass;
        this.next = null;
    }
}

class LinkedList {
    private Node head;
    private Node tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    public Node getHead() {
        return head;
    }

    public void insert(Node newNode) {
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            Node current = head;
            Node prev = null;

            while (current != null && (newNode.data == null || newNode.data.isAfter(current.data))) {
                prev = current;
                current = current.next;
            }

            if (prev == null) {
                newNode.next = head;
                head = newNode;
            } else {
                prev.next = newNode;
                newNode.next = current;
            }

            if (current == null) {
                tail = newNode;
            }
        }
    }

    public void reverse() {
        Node prev = null;
        Node current = head;
        Node next = null;

        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        tail = head;
        head = prev;
    }
}

public class DataInsertionSortLinkedList {
    static class Node {
        String password;
        int length;
        LocalDateTime data;
        String passwordClass;
        Node next;

        public Node(String password, int length, LocalDateTime data, String passwordClass) {
            this.password = password;
            this.length = length;
            this.data = data;
            this.passwordClass = passwordClass;
            this.next = null;
        }
    }

    static class LinkedList {
        private Node head;
        private Node tail;

        public LinkedList() {
            this.head = null;
            this.tail = null;
        }

        public Node getHead() {
            return head;
        }

        public void insert(Node newNode) {
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                Node current = head;
                Node prev = null;

                while (current != null && (newNode.data == null || newNode.data.isAfter(current.data))) {
                    prev = current;
                    current = current.next;
                }

                if (prev == null) {
                    newNode.next = head;
                    head = newNode;
                } else {
                    prev.next = newNode;
                    newNode.next = current;
                }

                if (current == null) {
                    tail = newNode;
                }
            }
        }

        public void reverse() {
            Node prev = null;
            Node current = head;
            Node next = null;

            while (current != null) {
                next = current.next;
                current.next = prev;
                prev = current;
                current = next;
            }

            tail = head;
            head = prev;
        }
    }
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        File arquivoData = new File("data/passwords_formated_data.csv");
        String arquivoPassClass = arquivoData.getAbsolutePath();

        File arquivoMelhorCaso = new File("sort/passwords_data_insertionSort_melhorCaso.csv");
        String arquivoMelhorCasoPath = arquivoMelhorCaso.getAbsolutePath();

        File arquivoPiorCaso = new File("sort/passwords_data_insertionSort_piorCaso.csv");
        String arquivoPiorCasoPath = arquivoPiorCaso.getAbsolutePath();

        File arquivoMedioCaso = new File("sort/passwords_data_insertionSort_medioCaso.csv");
        String arquivoMedioCasoPath = arquivoMedioCaso.getAbsolutePath();

        long startTime;
        long endTime;

        // Caso Médio
        startTime = System.currentTimeMillis();
        LinkedList passwordListMedioCaso = medioCaso(arquivoPassClass);
        imprimirLista(passwordListMedioCaso, arquivoMedioCasoPath);
        endTime = System.currentTimeMillis();
        double executionTimeMedio = (endTime - startTime) / 1000.0;
        System.out.printf("Tempo de execução (Caso Médio): %.3fs%n", executionTimeMedio);

        // Caso Melhor
        startTime = System.currentTimeMillis();
        LinkedList passwordListMelhorCaso = melhorCaso(passwordListMedioCaso);
        imprimirLista(passwordListMelhorCaso, arquivoMelhorCasoPath);
        endTime = System.currentTimeMillis();
        double executionTimeMelhor = (endTime - startTime) / 1000.0;
        System.out.printf("Tempo de execução (Melhor Caso): %.3fs%n", executionTimeMelhor);

        // Gerar arquivo invertido do caso médio
        String arquivoMedioCasoInvertidoPath = "sort/passwords_data_insertionSort_medioCaso_invertido.csv";
        gerarArquivoInvertido(arquivoMedioCasoPath, arquivoMedioCasoInvertidoPath);

        // Caso Pior
        startTime = System.currentTimeMillis();
        LinkedList passwordListPiorCasoInvertido = piorCaso(medioCaso(arquivoMedioCasoInvertidoPath));
        imprimirLista(passwordListPiorCasoInvertido, "sort/passwords_data_insertionSort_piorCaso.csv");
        endTime = System.currentTimeMillis();
        double executionTimePior = (endTime - startTime) / 1000.0;
        System.out.printf("Tempo de execução (Pior Caso): %.3fs%n", executionTimePior);

    }

    public static LinkedList melhorCaso(LinkedList medioCasoList) {
        LinkedList passwordList = new LinkedList();
        Node current = medioCasoList.getHead();
        while (current != null) {
            Node newNode = new Node(current.password, current.length, current.data, current.passwordClass);
            passwordList.insert(newNode);
            current = current.next;
        }
        return passwordList;
    }

    public static LinkedList piorCaso(LinkedList melhorCasoList) {
        LinkedList passwordList = new LinkedList();
        Node current = melhorCasoList.getHead();
        while (current != null) {
            Node newNode = new Node(current.password, current.length, current.data, current.passwordClass);
            passwordList.insert(newNode);
            current = current.next;
        }
        return passwordList;
    }

    public static LinkedList medioCaso(String arquivoPassClass) throws FileNotFoundException {
        LinkedList passwordList = new LinkedList();
        Scanner scanner = new Scanner(new File(arquivoPassClass));
        scanner.nextLine(); // Ignorar o cabeçalho

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = parseCSVLine(line);

            String password = values[1];
            int length = Integer.parseInt(values[2]);
            LocalDateTime data = LocalDateTime.parse(values[3], dateFormatter);
            String passwordClass = values[4];

            Node newNode = new Node(password, length, data, passwordClass);
            passwordList.insert(newNode);
        }

        scanner.close();
        return passwordList;
    }

    public static void imprimirLista(LinkedList list, String filePath) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filePath, "UTF-8");

        writer.println(",password,length,data,class");

        Node current = list.getHead();
        int index = 0;
        while (current != null) {
            writer.printf("%d,%s,%d,%s,%s%n",
                    index++,
                    current.password,
                    current.length,
                    current.data.format(dateFormatter),
                    current.passwordClass);
            current = current.next;
        }

        writer.close();
    }

    public static void gerarArquivoInvertido(String arquivoOriginalPath, String arquivoInvertidoPath) throws FileNotFoundException, UnsupportedEncodingException {
        File arquivoOriginal = new File(arquivoOriginalPath);
        File arquivoInvertido = new File(arquivoInvertidoPath);

        Scanner scanner = new Scanner(arquivoOriginal);
        PrintWriter writer = new PrintWriter(arquivoInvertido, "UTF-8");

        // Copiar o cabeçalho
        if (scanner.hasNextLine()) {
            writer.println(scanner.nextLine());
        }

        LinkedList lines = new LinkedList();
        int index = 0; // Manter o índice original

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (index > -1) {
                lines.insert(new Node(line, 0, null, ""));
            }
            index++;
        }

        scanner.close();

        lines.reverse(); // Inverter a ordem das linhas

        Node current = lines.getHead();

        while (current != null) {
            writer.println(current.password);
            current = current.next;
        }

        writer.close();
    }

    public static String[] parseCSVLine(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }
}
