import java.util.ArrayList;

import com.database.DatabaseConnection;
import com.model.Aluno;

public class App {

    public static void main(String[] args) throws Exception {
        clearScreen();
        
        Boolean logon = true;
        while (logon) {
            clearScreen();

            System.out.println("OPCÕES");
            
            System.out.println();
            System.out.println("GERAIS");
            System.out.println("[A] - Testar conexão com o banco de dados");

            System.out.println();
            System.out.println("ALUNOS");
            System.out.println("[B] - Cadastrar Aluno");
            System.out.println("[C] - Listar Alunos");
            System.out.println("[D] - Remover Aluno");
            System.out.println("[E] - Editar Aluno");

            System.out.println();
            System.out.println("[X] - Sair");

            System.out.print("\nDigite uma opção: ");

            String opcao = System.console().readLine();

            clearScreen();
            
            switch (opcao.toUpperCase()) {
                case "A":

                    System.out.println("Testando conexão com o banco de dados...");
                    connectionTest();

                break;
                case "B":
                    System.out.println("Cadastrando novo aluno...");
                    createAluno();
                break;
                case "C":
                case "D":
                case "E":
                    System.out.println("Listando alunos...");
                    listarAlunos();

                    if (!opcao.equals("C")) {
                        System.out.println();
                        System.out.print("Digite o ID do aluno ou aperte ENTER para voltar: ");

                        String id = System.console().readLine();

                        if (!id.isEmpty()) {

                         
                            if(Integer.parseInt(id) > 0) {

                                if(opcao.equals("D")) {
                                    Aluno aluno = Aluno.getById(Integer.parseInt(id));

                                    System.out.println();

                                    System.out.println("DADOS DO ALUNO");

                                    System.out.println("[" + aluno.getId() + "]" + aluno.toString());

                                    //aluno.destroy();

                                    System.out.println("Aluno removido com sucesso!");

                                } else if (opcao.equals("E")) {
                                    
                                }
                            }
                        }

                    }

                break;
                
                case "X":
                    logon = false;
                    System.out.println("Aplicação encerrada...");

                break;
                default:
                    System.err.println("Opção inválida");
                    break;
            }

            System.console().readLine();
        }
        
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    public static void connectionTest() {
        try {
            DatabaseConnection.open(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarAlunos() {
        ArrayList<Aluno> alunos = new ArrayList<>();

        alunos = Aluno.list();

        clearScreen();

        for (Aluno aluno : alunos) {
            System.out.println("[" + aluno.getId() + "] - " + aluno.toString());
        }
    }

    public static void createAluno() {

        String nome = requiredField("Nome");
        String idade = requiredField("Idade");
        String nota = requiredField("Nota");

        Aluno novoAluno = new Aluno();
        novoAluno.setNome(nome);
        novoAluno.setIdade(Integer.parseInt(idade));
        novoAluno.setNota(Double.parseDouble(nota));

        System.out.println();

        System.out.println("Salvano dados...");

        System.out.println(novoAluno.toString());

        novoAluno.insert();


        System.out.println("Aluno inserido com sucesso!");
    }

    public static String requiredField(String fieldName) {
        String val = "";

        while (val.isEmpty()) {
            System.out.print(fieldName + ": ");

            val = System.console().readLine();
        }

        return val;
    }

    public static String removeAluno(String fieldName) {

        listarAlunos();

        System.out.println();
        System.out.print("Digite o ID do aluno para excluir ou aperte ENTER para voltar: ");

        String id = System.console().readLine();

        if (!id.isEmpty()) {
            
        }

        return id;
    }
}
