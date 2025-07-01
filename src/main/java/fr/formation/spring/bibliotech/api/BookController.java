package fr.formation.spring.bibliotech.api;

import fr.formation.spring.bibliotech.api.dto.BookDto;
import fr.formation.spring.bibliotech.api.dto.BookSaveDto;
import fr.formation.spring.bibliotech.api.mapper.BookMapper;
import fr.formation.spring.bibliotech.dal.entities.Book;
import fr.formation.spring.bibliotech.dal.repositories.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper; // On injecte notre mapper

    public BookController(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    // L'endpoint GET retourne maintenant une liste de BookDto
    @GetMapping
    public List<BookDto> getAllBooks() {
        return this.bookRepository.findAll().stream()
                .map(this.bookMapper::toDto) // On mappe chaque livre
                .collect(Collectors.toList());
    }

    // La création prend maintenant un BookSaveDto en entrée
    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookSaveDto bookToCreate) {
        // On convertit le DTO d'entrée en entité
        Book newBook = this.bookMapper.toEntity(bookToCreate);
        Book savedBook = this.bookRepository.save(newBook);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();

        // On retourne le DTO de sortie
        return ResponseEntity.created(location).body(this.bookMapper.toDto(savedBook));
    }

    // ... les autres méthodes (GET by ID, PUT, DELETE)
    // devraient être mises à jour de la même manière.
}
