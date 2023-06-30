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

            while (current != null && newNode.data.isAfter(current.data)) {
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

        // Inverter Melhor Caso
        passwordListMelhorCaso.reverse();

        // Caso Pior
        startTime = System.currentTimeMillis();
        LinkedList passwordListPiorCaso = piorCaso(passwordListMelhorCaso);
        imprimirLista(passwordListPiorCaso, arquivoPiorCasoPath);
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

        int index = 0;
        int count = 0;
        while (scanner.hasNextLine()) {
            index++;
            String line = scanner.nextLine();
            String[] values = parseCSVLine(line);

            String password = values[1];
            int length = Integer.parseInt(values[2]);
            LocalDateTime data = LocalDateTime.parse(values[3], dateFormatter);
            String passwordClass = values[4];

            Node newNode = new Node(password, length, data, passwordClass);
            passwordList.insert(newNode);
            count++;
        }

        scanner.close();

        // Retirar metade dos elementos para simular um caso médio
        int removeCount = count / 2;
        Node current = passwordList.getHead();
        for (int i = 0; i < removeCount; i++) {
            if (current == null) {
                break;
            }
            current = current.next;
        }
        if (current != null) {
            current.next = null;
        }

        return passwordList;
    }

    public static void imprimirLista(LinkedList list, String filePath) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filePath, "UTF-8");

        writer.println("index,password,length,data,class");

        Node current = list.getHead();
        int index = 0;
        while (current != null) {
            writer.printf("%d,%s,%d,%s,%s%n",
                    ++index,
                    current.password,
                    current.length,
                    current.data.format(dateFormatter),
                    current.passwordClass);
            current = current.next;
        }

        writer.close();
    }

    public static String[] parseCSVLine(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }
}
