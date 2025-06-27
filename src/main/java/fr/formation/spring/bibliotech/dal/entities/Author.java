package fr.formation.bibliotech.dal.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data // Génère getters, setters, toString, etc.
@NoArgsConstructor // Génère un constructeur sans arguments
@Entity // Indique que cette classe est une entité JPA
public class Author {

    @Id // Clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrément
    private Long id;

    private String firstName;
    private String lastName;

    // Un auteur peut avoir écrit plusieurs livres.
    // 'mappedBy' indique que la relation est gérée côté Book.
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

}
