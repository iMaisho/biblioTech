package fr.formation.spring.bibliotech.api.mapper;

import fr.formation.spring.bibliotech.api.dto.BookDto;
import fr.formation.spring.bibliotech.api.dto.BookSaveDto;
import fr.formation.spring.bibliotech.dal.entities.Author;
import fr.formation.spring.bibliotech.dal.entities.Book;
import fr.formation.spring.bibliotech.dal.repositories.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component // Pour que Spring puisse l'injecter dans notre contrôleur
public class BookMapper {

    private final AuthorRepository authorRepository;

    public BookMapper(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Convertit une Entité Book en DTO de sortie BookDto
    public BookDto toDto(Book entity) {
        if (entity == null) {
            return null;
        }
        BookDto dto = new BookDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setIsbn(entity.getIsbn());
        dto.setPublicationDate(entity.getPublicationDate());

        // Pour les auteurs, on extrait juste leur nom complet.
        if (entity.getAuthors() != null) {
            dto.setAuthorNames(entity.getAuthors().stream()
                    .map(author -> author.getFirstName() + " " + author.getLastName())
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    // Convertit un DTO d'entrée BookSaveDto en Entité Book
    public Book toEntity(BookSaveDto dto) {
        if (dto == null) {
            return null;
        }
        Book entity = new Book();
        entity.setTitle(dto.getTitle());
        entity.setIsbn(dto.getIsbn());
        entity.setPublicationDate(dto.getPublicationDate());

        // Pour les IDs des auteurs, on va chercher les entités
        // correspondantes dans la base.
        if (dto.getAuthorIds() != null) {
            entity.setAuthors(dto.getAuthorIds().stream()
                    .map(authorRepository::findById)
                    .filter(java.util.Optional::isPresent)
                    .map(java.util.Optional::get)
                    .collect(Collectors.toSet()));
        }
        return entity;
    }
}