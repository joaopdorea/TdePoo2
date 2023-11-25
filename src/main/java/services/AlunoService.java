package services;

import entities.Aluno;
import entities.Curso;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class AlunoService {

    public void inserirAluno(EntityManagerFactory emf, Aluno a1) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(a1);
            // Commit da transação. Isso confirmará as operações de persistência realizadas durante a transação.
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            em.close();
        }

    }


    public void mostraAlunosECurso(EntityManagerFactory emf) {


        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query query = em.createQuery("SELECT a FROM Aluno a JOIN a.cursos c");
            List<Aluno> resultList = query.getResultList();

            Query query2 = em.createQuery("SELECT c.nome FROM Aluno a JOIN a.cursos c");
            List<String> resultList2 = query2.getResultList();

            int i = 0;
            for(Aluno x: resultList){

                System.out.print(x);
                System.out.println(" {" + resultList2.get(i) + "}");
                i = i+1;

            }

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }

    }


    public void mostraAlunos(EntityManagerFactory emf) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {

            transaction.begin();

            Query query = em.createQuery("SELECT a FROM Aluno a");
            List<Aluno> resultList = query.getResultList();

            for(Aluno x: resultList){

                System.out.println(x);



            }

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            em.close();
        }

    }


    public Aluno buscaAlunoPelaMatricula(EntityManagerFactory emf, String matricula) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Query query = em.createQuery("SELECT a FROM Aluno a WHERE a.matricula = :matricula");
            query.setParameter("matricula", matricula);

            List<Aluno> resultList = query.getResultList();

            Aluno a1 = resultList.get(0);

            return a1;



        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }
        return null;

    }


    public void matricularAluno(EntityManagerFactory emf, Aluno alunoProcurado, Curso cursoProcurado) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();

            Curso curso = em.createQuery("SELECT c FROM Curso c LEFT JOIN FETCH c.alunos WHERE c = :curso", Curso.class)
                    .setParameter("curso", cursoProcurado)
                    .getSingleResult();

            curso.getAlunos().add(alunoProcurado);
            transaction.commit();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            em.close();
        }
    }






}
