package fr.formation.spring.bibliotech.api.mapper;


import fr.formation.spring.bibliotech.api.dto.BookDto;
import fr.formation.spring.bibliotech.api.dto.BookSaveDto;
import fr.formation.spring.bibliotech.dal.entities.Author;
import fr.formation.spring.bibliotech.dal.entities.Book;
import fr.formation.spring.bibliotech.dal.repositories.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

// componentModel="spring" génère un @Component sur l'implémentation,
// la rendant injectable.
// uses = {AuthorRepository.class} permet à MapStruct d'injecter et
// d'utiliser ce repository.
@Mapper(componentModel = "spring", uses = {AuthorRepository.class})
public interface BookMapper {

    // MapStruct gère automatiquement les champs avec le même nom.
    // Pour "authorNames", le mapping est complexe, on le spécifie.
    @Mapping(target = "authorNames", source = "authors", qualifiedByName = "authorsToNames")
    BookDto toDto(Book entity);

    // Pour le DTO -> Entité, on doit gérer la conversion des IDs en entités.
    @Mapping(target = "authors", source = "authorIds")
    Book toEntity(BookSaveDto dto);

    // Cette méthode nommée "authorsToNames" sera utilisée par MapStruct
    // pour le mapping personnalisé.
    @Named("authorsToNames")
    default Set<String> authorsToNames(Set<Author> authors) {
        if (authors == null) {
            return null;
        }
        return authors.stream()
                .map(author -> author.getFirstName() + " " + author.getLastName())
                .collect(Collectors.toSet());
    }
}