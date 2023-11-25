package controller;

import entities.Aluno;
import entities.Curso;
import services.AlunoService;
import services.CursoService;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class JpaBootstrap {

    public static void main(String[] args) {
        // Criação de uma 'EntityManagerFactory'. Este é um passo caro, normalmente feito apenas uma vez por aplicação.
        // "exemplo-jpa" é o nome da unidade de persistência definida no arquivo "persistence.xml".
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TDE2");
        AlunoService alunoService = new AlunoService();
        CursoService cursoService = new CursoService();

        // O 'EntityManager' é responsável por gerenciar as entidades e suas transações.
        // É criado a partir da 'EntityManagerFactory' e é uma instância leve que deve ser usada e descartada para cada transação ou série de transações.
        EntityManager em = emf.createEntityManager();

        try {
            // Iniciar uma transação. É necessário para realizar operações de persistência, como salvar um objeto no banco de dados.

            int option = 1;

            //Loop com um menu de opções, que só é encerrado se o usuário aperta 0
            while (option != 0) {
                System.out.println("Digite 1 para adicionar um aluno");
                System.out.println("Digite 2 para adicionar um curso");
                System.out.println("Digite 3 para mostrar todos os cursos");
                System.out.println("Digite 4 para mostrar todos os alunos");
                System.out.println("Digite 5 para adicionar um aluno a determinado curso");
                System.out.println("Digite 6 para mostrar todos os alunos e seus respectivos cursos");
                System.out.println("Digite 7 para mostrar todos os alunos de determinado curso");


                System.out.println("Digite 0 para sair");

                Scanner scan = new Scanner(System.in);
                option = scan.nextInt();
                switch (option) {

                    //Inserindo aluno
                    case 1:
                        System.out.println("Digite o número da matrícula do aluno:");
                        String matricula = scan.next();

                        System.out.println("Digite o nome do aluno:");
                        String nome = scan.next();


                        Aluno aluno = new Aluno(matricula, nome);

                        alunoService.inserirAluno(emf, aluno);


                        break;

                    //Inserindo curso
                    case 2:

                        System.out.println("Digite o código do curso:");
                        int codigoCurso = scan.nextInt();

                        System.out.println("Digite o nome do curso:");
                        String nomeCurso = scan.next();

                        System.out.println("Digite a carga horária do curso:");
                        int cargaHoraria = scan.nextInt();

                        Curso curso = new Curso(codigoCurso, nomeCurso, cargaHoraria);

                        cursoService.inserirCurso(emf, curso);


                        break;

                    case 3:
                        //Mostrando todos os cursos do banco de dados

                        try {
                            cursoService.mostraCursos(emf);
                        }catch(Exception e){
                            e.printStackTrace();
                        }


                        break;


                    case 4:
                        //Mostrando todos os alunos do banco de dados

                        try {
                            alunoService.mostraAlunos(emf);
                        }catch(Exception e){
                            e.printStackTrace();
                        }


                        break;

                        //Alocando aluno em curso
                    case 5:

                        try {


                            String nomeDoCurso;
                            String matriculaAluno;

                            Scanner scan2 = new Scanner(System.in);

                            System.out.println("Escreva a matrícula do aluno que você deseja alocar");
                            alunoService.mostraAlunos(emf);
                            matriculaAluno = scan2.next();

                            Aluno a1 = alunoService.buscaAlunoPelaMatricula(emf, matriculaAluno);

                            System.out.println("Selecione o nome do curso");
                            cursoService.mostraCursos(emf);
                            nomeDoCurso = scan2.next();

                            Curso c1 = cursoService.buscaCursoPeloNome(emf, nomeDoCurso);

                            alunoService.matricularAluno(emf, a1, c1);




                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        break;



                    case 6:
                        //Mostrando todos os alunos e seus cursos

                        try {
                            alunoService.mostraAlunosECurso(emf);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        break;


                    case 7:
                        //Mostrando todos os alunos de determinado curso

                        try {
                            Scanner scan4 = new Scanner(System.in);
                            System.out.println("Digite o nome do curso");
                            String cursoEscolhido = scan4.next();
                            cursoService.mostraAlunosDeCurso(emf, cursoEscolhido);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        break;

                }

            }
        } catch (Exception e) {
            // Se houver alguma exceção durante a transação, faz o rollback para evitar um estado inconsistente no banco de dados.
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            // Fechamento do 'EntityManager'. É importante fechar para liberar os recursos que ele está consumindo.
            em.close();
        }

        // Fechamento da 'EntityManagerFactory'. Uma vez fechado, nenhum 'EntityManager' pode ser criado. Deve ser fechado ao final do programa para liberar recursos.
        emf.close();
    }





}






























