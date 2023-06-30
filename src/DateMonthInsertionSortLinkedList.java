import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class DateMonthInsertionSortLinkedList {
    static class Node {
        String password;
        String length;
        String data;
        String passClass;
        Node next;

        Node(String password, String length, String data, String passClass) {
            this.password = password;
            this.length = length;
            this.data = data;
            this.passClass = passClass;
            this.next = null;
        }
    }

    static class LinkedList {
        Node head;

        LinkedList() {
            this.head = null;
        }

        void insert(String password, String length, String data, String passClass) {
            Node newNode = new Node(password, length, data, passClass);
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
        }

        void insertionSort() {
            if (head == null || head.next == null) {
                return;
            }

            Node sortedList = null;
            Node current = head;

            while (current != null) {
                Node nextNode = current.next;
                sortedList = sortedInsert(sortedList, current);
                current = nextNode;
            }

            head = sortedList;
        }

        Node sortedInsert(Node sortedList, Node newNode) {
            if (sortedList == null || sortedList.data.compareTo(newNode.data) >= 0) {
                newNode.next = sortedList;
                return newNode;
            }

            Node current = sortedList;
            while (current.next != null && current.next.data.compareTo(newNode.data) < 0) {
                current = current.next;
            }

            newNode.next = current.next;
            current.next = newNode;

            return sortedList;
        }

        void printToFile(String filename) throws FileNotFoundException, UnsupportedEncodingException {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println(",password,length,data,class");

            Node current = head;
            int i = 0;
            while (current != null) {
                writer.println(i + "," + current.password + "," + current.length + "," + current.data + "," + current.passClass);
                current = current.next;
                i++;
            }

            writer.close();
        }
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        File arquivoData = new File("data/passwords_formated_data.csv");
        String arquivoPassClass = arquivoData.getAbsolutePath();

        File arquivoMedio = new File("sort/passwords_data_month_insertionSort_medioCaso.csv");
        String arquivoMedioCaso = arquivoMedio.getAbsolutePath();

        File arquivoMelhor = new File("sort/passwords_data_month_insertionSort_melhorCaso.csv");
        String arquivoMelhorCaso = arquivoMelhor.getAbsolutePath();

        File arquivoPior = new File("sort/passwords_data_month_insertionSort_piorCaso.csv");
        String arquivoPiorCaso = arquivoPior.getAbsolutePath();

        LinkedList linkedList = new LinkedList();
        readPasswordsFromFile(arquivoPassClass, linkedList);

        // Caso Médio
        LinkedList medioLinkedList = cloneLinkedList(linkedList);
        long medioTempoInicial = System.currentTimeMillis();
        medioLinkedList.insertionSort(); // Ordena o arquivo
        medioLinkedList.printToFile(arquivoMedioCaso); // Salva o arquivo ordenado
        long medioTempoFinal = System.currentTimeMillis();
        System.out.printf("Tempo de execução (Caso Médio): %.3fs%n", (medioTempoFinal - medioTempoInicial) / 1000d);

        // Melhor Caso
        LinkedList melhorLinkedList = cloneLinkedList(linkedList);
        long melhorTempoInicial = System.currentTimeMillis();
        melhorLinkedList.insertionSort(); // Verifica o já ordenado
        melhorLinkedList.printToFile(arquivoMelhorCaso); // Salva o arquivo ordenado
        long melhorTempoFinal = System.currentTimeMillis();
        System.out.printf("Tempo de execução (Melhor Caso): %.3fs%n", (melhorTempoFinal - melhorTempoInicial) / 1000d);

        // Gerar arquivo invertido do caso médio
        String arquivoMedioCasoInvertidoPath = "sort/passwords_data_month_insertionSort_medioCaso_invertido.csv";
        gerarArquivoInvertido(arquivoMedioCaso, arquivoMedioCasoInvertidoPath);

        // Pior Caso
        invertList(linkedList);
        LinkedList piorLinkedList = cloneLinkedList(linkedList);
        long piorTempoInicial = System.currentTimeMillis();
        piorLinkedList.insertionSort(); // Ordena o arquivo que está em ordem decrescente
        piorLinkedList.printToFile(arquivoPiorCaso); // Salva o arquivo ordenado no mesmo arquivo invertido do caso médio
        long piorTempoFinal = System.currentTimeMillis();
        System.out.printf("Tempo de execução (Pior Caso): %.3fs%n", (piorTempoFinal - piorTempoInicial) / 1000d);
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

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",");
            lines.insert(values[1], values[2], values[3], values[4]);
        }

        scanner.close();

        invertList(lines); // Inverter a ordem das linhas

        Node current = lines.head;
        int index = 0;

        while (current != null) {
            writer.println(index + "," + current.password + "," + current.length + "," + current.data + "," + current.passClass);
            current = current.next;
            index++;
        }

        writer.close();
    }
    public static void readPasswordsFromFile(String filename, LinkedList linkedList) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            scanner.nextLine(); // Skip the header line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                String password = values[1];
                String length = values[2];
                String data = values[3];
                String passClass = values[4];

                linkedList.insert(password, length, data, passClass);
            }
        }
    }

    public static void invertList(LinkedList linkedList) {
        Node prev = null;
        Node current = linkedList.head;
        Node next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        linkedList.head = prev;
    }

    public static LinkedList cloneLinkedList(LinkedList linkedList) {
        LinkedList clone = new LinkedList();
        Node current = linkedList.head;
        while (current != null) {
            clone.insert(current.password, current.length, current.data, current.passClass);
            current = current.next;
        }
        return clone;
    }
}
