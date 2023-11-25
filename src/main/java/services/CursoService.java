package services;

import entities.Aluno;
import entities.Curso;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class CursoService {


    public void inserirCurso(EntityManagerFactory emf, Curso c1) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(c1);
            // Commit da transação. Isso confirmará as operações de persistência realizadas durante a transação.
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }

    }


    public void mostraCursos(EntityManagerFactory emf) {


        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {

            transaction.begin();

            Query query = em.createQuery("select c from Curso c");
            List<Curso> resultList = query.getResultList();

            for(Curso x: resultList){
                System.out.println(x);
            }

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }

    }


    public Curso buscaCursoPeloNome(EntityManagerFactory emf, String nomeCurso) {
        EntityManager em = emf.createEntityManager();

        try {


            Query query = em.createQuery("SELECT c FROM Curso c WHERE c.nome = :nomeCurso");
            query.setParameter("nomeCurso", nomeCurso);

            List<Curso> resultList = query.getResultList();

            Curso c1 = resultList.get(0);

            return c1;



        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }
        return null;

    }


    public void mostraAlunosDeCurso(EntityManagerFactory emf, String nomeCurso) {


        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query query = em.createQuery("SELECT a FROM Aluno a JOIN a.cursos c WHERE c.nome = :nomeCurso");
            query.setParameter("nomeCurso", nomeCurso);
            List<Aluno> resultList = query.getResultList();



            for(Aluno x: resultList){

                System.out.println(x);

            }

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }

    }
}
