package app.daos;

import app.config.HibernateConfig;
import app.entities.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ActorDAO
{
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public ActorDAO(EntityManagerFactory emf){super();
    }

    public void save (Actor actor){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(actor);
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

    public Actor findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Actor.class, id);
        } finally {
            em.close();
        }
    }

    public List<Actor> findAll()
    {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("SELECT a FROM Actor a", Actor.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Actor actor = em.find(Actor.class, id);
            if (actor != null) {
                em.remove(actor);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
