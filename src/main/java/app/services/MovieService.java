package app.services;

import app.dtos.MovieDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieService
{
    private static final String API_KEY = System.getenv("api_key"); //miljøvariabel
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public MovieService()
    {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); //Understøtter LocalDate, JSON bruger normalt String til datoer.
    }


    // TODO: Hent en film via ID:
    public MovieDTO fetchMovieById(int movieId){
        String url = String.format("%s%d?api_key=%s&language=da-DK", BASE_URL, movieId, API_KEY);

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode()==200)
            {
                return objectMapper.readValue(response.body(), MovieDTO.class);
            }else {
                System.out.println("Fejl: " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //TODO: Hent danske film udgivet de sidste 5 år:

    public List<MovieDTO> fetchDanishMoviesFromTheLastFiveYears()
    {
        LocalDate fiveYearsAgo = LocalDate.now().minusYears(5);
        LocalDate today = LocalDate.now();
        String url = String.format("%sdiscover/movie?api_key=%s&language=da-DK&region=DK&primary_release_date.gte=%s&primary_release_date.lte=%s&sort_by=primary_release_date.desc",
                BASE_URL, API_KEY, fiveYearsAgo, today);


        System.out.println("API Request URL: " + url); // Debugging

        List<MovieDTO> movies = new ArrayList<>();

        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
            {
                JsonNode rootNode = objectMapper.readTree(response.body());
                JsonNode results = rootNode.get("results");

                for (JsonNode node : results)
                {
                    MovieDTO movie = objectMapper.treeToValue(node, MovieDTO.class);
                    movies.add(movie);
                }
            } else
            {
                System.out.println("Fejl: " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return movies;
    }
}
