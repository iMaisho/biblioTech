package fr.formation.spring.bibliotech.api;

import fr.formation.spring.bibliotech.dal.entities.Author;
import fr.formation.spring.bibliotech.dal.entities.Book;
import fr.formation.spring.bibliotech.dal.repositories.AuthorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

// @RestController combine @Controller et @ResponseBody.
// Il indique que cette classe gère des requêtes web et que les
// retours de méthodes seront directement écrits dans la réponse HTTP.
@RestController
// @RequestMapping définit l'URI de base pour toutes les méthodes
// de ce contrôleur.
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    // Injection de dépendance du repository pour accéder aux données.
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // @GetMapping indique que cette méthode répond aux requêtes HTTP GET.
    // L'URL complète sera donc : GET /api/authors
    @GetMapping
    public List<Author> getAllAuthors() {
        // Spring Data JPA nous offre findAll() pour récupérer tous les
        // enregistrements.
        return this.authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        // Spring Data JPA nous offre findAll() pour récupérer tous les
        // enregistrements.
        Optional<Author> author = this.authorRepository.findById(id);

        if (author.isPresent()) {
            return ResponseEntity.ok(author.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author authorToCreate) {

        Author savedAuthor = this.authorRepository.save(authorToCreate);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // Prend l'URL de la requête actuelle (/api/books)
                .path("/{id}")        // Ajoute /{id}
                .buildAndExpand(savedAuthor.getId()) // Remplace {id} par l'ID du livre créé
                .toUri();

        return ResponseEntity.created(location).body(savedAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor) {
        Optional<Author> authorToUpdate = this.authorRepository.findById(id);
        if (authorToUpdate.isPresent()) {
            authorToUpdate.get().setFirstName(updatedAuthor.getFirstName());
            authorToUpdate.get().setLastName(updatedAuthor.getLastName());
            return ResponseEntity.ok(this.authorRepository.save(authorToUpdate.get()));
        }
        else  {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {

        if (authorRepository.existsById(id)) {
            this.authorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else  {
            return ResponseEntity.notFound().build();
        }
}
}