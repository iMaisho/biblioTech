package fr.formation.spring.bibliotech.api;

import fr.formation.spring.bibliotech.dal.entities.Book;
import fr.formation.spring.bibliotech.dal.repositories.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

// @RestController combine @Controller et @ResponseBody.
// Il indique que cette classe gère des requêtes web et que les
// retours de méthodes seront directement écrits dans la réponse HTTP.
@RestController
// @RequestMapping définit l'URI de base pour toutes les méthodes
// de ce contrôleur.
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    // Injection de dépendance du repository pour accéder aux données.
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // @GetMapping indique que cette méthode répond aux requêtes HTTP GET.
    // L'URL complète sera donc : GET /api/books
    @GetMapping
    public List<Book> getAllBooks() {
        // Spring Data JPA nous offre findAll() pour récupérer tous les
        // enregistrements.
        return this.bookRepository.findAll();
    }
    // @GetMapping("/{id}") complète l'URI de base "/api/books".

    // L'URL sera donc /api/books/{id}, où {id} est une variable.
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        // @PathVariable lie la variable "id" de l'URL au paramètre "id"
        // de la méthode.

        // findById retourne un Optional<Book>, qui est un conteneur
        // pouvant contenir ou non une valeur. C'est une façon propre de
        // gérer les cas où le livre n'est pas trouvé.
        Optional<Book> book = this.bookRepository.findById(id);

        if (book.isPresent()) {
            // Si le livre existe, on retourne un statut 200 OK
            // et le livre dans le corps de la réponse.
            return ResponseEntity.ok(book.get());
        } else {
            // Si le livre n'existe pas, on retourne un statut 404 Not Found.
            // .build() crée une réponse sans corps.
            return ResponseEntity.notFound().build();
        }
    }
}

