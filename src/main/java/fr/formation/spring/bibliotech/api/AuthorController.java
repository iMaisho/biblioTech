package fr.formation.spring.bibliotech.api;

import fr.formation.spring.bibliotech.dal.entities.Author;
import fr.formation.spring.bibliotech.dal.repositories.AuthorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}