package app.daos;

import app.config.HibernateConfig;
import app.entities.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DirectorDAO
{
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public void save (Director director){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(director);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

    public Director findById(Long id){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Director.class, id);
        } finally {
            em.close();
        }
    }

    public List<Director> findAll(){
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("SELECT d FROM Director d", Director.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void delete (Long id) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Director director = em.find(Director.class, id);
            if (director != null) {
                em.remove(director);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
