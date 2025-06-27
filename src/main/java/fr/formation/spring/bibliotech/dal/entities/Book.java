package fr.formation.bibliotech.dal.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique = true) // L'ISBN doit être unique
    private String isbn;

    private LocalDate publicationDate;

    // Un livre peut avoir plusieurs auteurs.
    @ManyToMany
    @JoinTable( // Table de jointure pour la relation ManyToMany
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;
}
