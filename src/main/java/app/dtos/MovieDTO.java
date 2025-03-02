package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignorerer ukendte felter som eks "adult" - Jackson ignorerer felter i JSON, som ikke findes i MovieDTO'en, da den ellers viser denne fejl: com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "adult" (class app.dtos.MovieDTO), not marked as ignorable (7 known properties: "score", "release_date", "title", "overview", "id", "description", "language"])
public class MovieDTO {

    private int id;
    private String title;

    private String overview;
    private String description;
    private LocalDate releaseDate;
    private Double score;
    private String language;
    private String country;


    @JsonProperty("release_date")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = LocalDate.parse(releaseDate);
    }

    public String getReleaseYear() {
        return String.valueOf(releaseDate.getYear());
    }
}