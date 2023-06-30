import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        File arquivo = new File("data/passwords.csv");
        String arquivoCsv = arquivo.getAbsolutePath(); //encontra o arquivo passwords.csv

        File arquivoData = new File("data/password_classifier.csv");
        String arquivoPassClass = arquivoData.getAbsolutePath();

        File arquivoFiltro = new File("data/password_classifier.csv");
        String arquivoFiltrado = arquivoFiltro.getAbsolutePath();

        classificarSenha(arquivoCsv);
        mudarData(arquivoPassClass);
        filtrarClass(arquivoFiltrado);

    }
    //Classificar senhas
    public static void classificarSenha(String arquivoCsv) throws IOException {
        Scanner scanner = new Scanner(new File(arquivoCsv));
        //Cria novo arquivo,o UTF-8 serve para armazenar qualquer tipo de caractere
        PrintWriter writer = new PrintWriter("data/password_classifier.csv", "UTF-8"); // Criar o novo arquivo para classificar as senhas
        String classificacao;

        writer.print(",password,length,data,class\n"); //Colunas
        scanner.nextLine(); //evita que a primeira linha entre no while
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"); // Expressão regular para separar apenas vírgulas fora de aspas
            String numero = valores[0];
            String senha = valores[1];
            String tam = valores[2];
            String data = valores[3];

            int tamanhoSenha = senha.length();
            int tiposCaracteres = contarTiposCaracteres(senha);

            if (tamanhoSenha < 5 && tiposCaracteres == 1) {
                classificacao = "Muito Ruim";
            }
            else if (tamanhoSenha == 5 && tiposCaracteres == 1) {
                classificacao = "Ruim";
            }
            else if (tamanhoSenha <= 6 && tiposCaracteres == 2) {
                classificacao = "Fraca";
            }
            else if (tamanhoSenha <= 7 && tiposCaracteres == 3) {
                classificacao = "Boa";
            }
            else if (tamanhoSenha > 8 && tiposCaracteres == 3) {
                classificacao = "Muito Boa";
            }
            else {
                classificacao = "Sem Classificação";
            }

            writer.println(numero + "," + senha + "," + tam + "," + data + "," + classificacao);
        }

        writer.close();
    }

    //Contar caracteres especiais
    public static int contarTiposCaracteres(String senha) {
        int contemLetrasMinusculas = 0;
        int contemLetrasMaiusculas = 0;
        int contemNumeros = 0;
        int contemCaracteresEspeciais = 0;

        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);
            if (c >= 'a' && c <= 'z') {
                contemLetrasMinusculas = 1;
            } else {
                if (c >= 'A' && c <= 'Z') {
                    contemLetrasMaiusculas = 1;
                } else {
                    if (c >= '0' && c <= '9') {
                        contemNumeros = 1;
                    } else {
                        contemCaracteresEspeciais = 1;
                    }
                }
            }
        }
        int tiposCaracteres = contemLetrasMinusculas + contemLetrasMaiusculas + contemNumeros + contemCaracteresEspeciais;
        return tiposCaracteres;
    }

    //Converte a data para o modelo DD/MM/AAAA
    public static void mudarData(String arquivoPassClass) throws IOException {
        Scanner scanner = new Scanner(new File(arquivoPassClass));
        //Cria novo arquivo,o UTF-8 serve para armazenar qualquer tipo de caractere
        PrintWriter writer = new PrintWriter("data/passwords_formated_data.csv", "UTF-8");

        scanner.nextLine(); //evita que a primeira linha entre no while
        writer.print(",password,length,data,class\n"); //Colunas

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
            String numero = valores[0];
            String senha = valores[1];
            String tam = valores[2];
            String data = valores[3];
            String classificacao = valores[4];

            String[] partes = data.split(" "); //Separa a data do horario
            String[] partesData = partes[0].split("-"); //Separa AAAA MM DD
            String novaData = partesData[2] + "/" + partesData[1] + "/" + partesData[0] + " " + partes[1];

            writer.println(numero + "," + senha + "," + tam + "," + novaData + "," + classificacao);
        }
        writer.close();
    }

    //Filtra as senhas classificadas como "Boa" ou "Muito Boa"
    public static void filtrarClass(String arquivoFiltrado) throws IOException {
        Scanner scanner = new Scanner(new File(arquivoFiltrado));
        //Cria novo arquivo,o UTF-8 serve para armazenar qualquer tipo de caractere
        PrintWriter writer = new PrintWriter("data/passwords_classifier.csv", "UTF-8");

        scanner.nextLine(); //evita que a primeira linha entre no while
        writer.print(",password,length,data,class\n"); //Colunas

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] valores = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"); // Expressão regular para separar apenas vírgulas fora de aspas
            String numero = valores[0];
            String senha = valores[1];
            String tam = valores[2];
            String data = valores[3];
            String classificacao = valores[4];
            //Filtrar apenas classificações "Muito boa" e "Boa"
            if (valores[4].equals("Muito Boa") || valores[4].equals("Boa")){
                writer.println(numero + "," + senha + "," + tam + "," + data + "," + classificacao);
            }
        }
        writer.close();
    }
}