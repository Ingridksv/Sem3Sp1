package app;

import app.config.HibernateConfig;
import app.daos.ActorDAO;
import app.daos.MovieDAO;
import app.dtos.MovieDTO;
import app.services.MovieService;
import jakarta.persistence.EntityManagerFactory;

public class Main
{
    public static void main(String[] args)
    {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        MovieDAO movieDAO = new MovieDAO(emf);
        ActorDAO actorDAO = new ActorDAO(emf);


        MovieService movieService = new MovieService();

      //  MovieDTO movie = movieService.fetchMovieById(550);
      //  System.out.println("Film: " + movie);

        //Hent danske film.
        System.out.println("Danske film fra de sidste 5 år");
        for (MovieDTO m : movieService.fetchDanishMoviesFromTheLastFiveYears()){
            System.out.println(m);
        }


    }
}