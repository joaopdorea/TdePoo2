package controller;

import entities.Aluno;
import entities.Curso;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JpaBootstrap {

    public static void main(String[] args) {
        // Criação de uma 'EntityManagerFactory'. Este é um passo caro, normalmente feito apenas uma vez por aplicação.
        // "exemplo-jpa" é o nome da unidade de persistência definida no arquivo "persistence.xml".
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TDE2");

        // O 'EntityManager' é responsável por gerenciar as entidades e suas transações.
        // É criado a partir da 'EntityManagerFactory' e é uma instância leve que deve ser usada e descartada para cada transação ou série de transações.
        EntityManager em = emf.createEntityManager();

        try {
            // Iniciar uma transação. É necessário para realizar operações de persistência, como salvar um objeto no banco de dados.

            int option = 1;

            while (option != 0) {
                System.out.println("Digite 1 para adicionar um aluno");
                System.out.println("Digite 2 para adicionar um curso");
                System.out.println("Digite 3 para mostrar todos os cursos");
                System.out.println("Digite 4 para mostrar todos os alunos");
                System.out.println("Digite 5 para adicionar um aluno a determinado curso");


                System.out.println("Digite 0 para sair");

                Scanner scan = new Scanner(System.in);
                option = scan.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("Digite o número da matrícula do aluno:");
                        String matricula = scan.next();

                        System.out.println("Digite o nome do aluno:");
                        String nome = scan.next();


                        Aluno aluno = new Aluno(matricula, nome);

                        inserirAluno(em, aluno);


                        break;


                    case 2:

                        System.out.println("Digite o código do curso:");
                        int codigoCurso = scan.nextInt();

                        System.out.println("Digite o nome do curso:");
                        String nomeCurso = scan.next();

                        System.out.println("Digite a carga horária do curso:");
                        int cargaHoraria = scan.nextInt();

                        Curso curso = new Curso(codigoCurso, nomeCurso, cargaHoraria);

                        inserirCurso(em, curso);


                        break;

                    case 3:

                        try {
                            em.getTransaction().begin();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        mostraCursos(em);

                        break;


                    case 4:

                        try {
                            em.getTransaction().begin();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        mostraAlunos(em);

                        break;


                    case 5:

                        try {
                            em.getTransaction().begin();

                            String nomeDoCurso;
                            String matriculaAluno;

                            Scanner scan2 = new Scanner(System.in);

                            System.out.println("Escreva a matrícula do aluno que você deseja alocar");
                            mostraAlunos(em);
                            matriculaAluno = scan2.next();

                            Aluno a1 = buscaAlunoPelaMatricula(em, matriculaAluno);

                            System.out.println("Selecione o curso");
                            mostraCursos(em);
                            nomeDoCurso = scan2.next();

                            Curso c1 = buscaCursoPeloNome(em, nomeDoCurso);

                            c1.getAlunos().add(a1);
                            em.persist(c1);
                            em.getTransaction().commit();

                        }catch(Exception e){
                            e.printStackTrace();
                        }


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

    public static void inserirAluno(EntityManager em, Aluno a1) {

        try {
            em.getTransaction().begin();
            em.persist(a1);
            // Commit da transação. Isso confirmará as operações de persistência realizadas durante a transação.
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void inserirCurso(EntityManager em, Curso c1) {

        try {
            em.getTransaction().begin();
            em.persist(c1);
            // Commit da transação. Isso confirmará as operações de persistência realizadas durante a transação.
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void mostraCursos(EntityManager em) {

        try {


            Query query = em.createQuery("select c from Curso c");
            List<Curso> resultList = query.getResultList();

            for(Curso x: resultList){
                System.out.println(x);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void mostraAlunos(EntityManager em) {

        try {

            Query query = em.createQuery("select a from Aluno a");
            List<Aluno> resultList = query.getResultList();

            for(Aluno x: resultList){
                System.out.println(x);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static Aluno buscaAlunoPelaMatricula(EntityManager em, String matricula) {

        try {
            em.getTransaction().begin();

            Query query = em.createQuery("SELECT a FROM Aluno a WHERE a.matricula = :matricula");
            query.setParameter("matricula", matricula);

            List<Aluno> resultList = query.getResultList();

            Aluno a1 = resultList.get(0);

            return a1;



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public static Curso buscaCursoPeloNome(EntityManager em, String nomeCurso) {

        try {
            em.getTransaction().begin();

            Query query = em.createQuery("SELECT c FROM Curso c WHERE c.nome = :nomeCurso");
            query.setParameter("nomeCurso", nomeCurso);

            List<Curso> resultList = query.getResultList();

            Curso c1 = resultList.get(0);

            return c1;



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }





}






























