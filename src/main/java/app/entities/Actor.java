package app.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Actor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String character;

    @ManyToMany(mappedBy ="actors", fetch = FetchType.EAGER)
    private Set<Movie> movies;

    public Actor(String name, String character){
        this.name = name;
        this.character = character;
    }
}