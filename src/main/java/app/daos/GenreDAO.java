package app.daos;

import app.config.HibernateConfig;
import app.entities.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class GenreDAO
{
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public void save (Genre genre){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(genre);
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

    public Genre findById(Long id){
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Genre.class, id);
        } finally {
            em.close();
        }
    }

    public List<Genre> findAll(){
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("SELECT g FROM Genre", Genre.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void delete (Long id) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Genre genre = em.find(Genre.class, id);
            if (genre != null){
                em.remove(genre);
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
