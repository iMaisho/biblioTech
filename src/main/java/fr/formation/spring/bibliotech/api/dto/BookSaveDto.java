package fr.formation.spring.bibliotech.api.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class BookSaveDto {

    // Le titre ne doit pas être nul et ne doit pas être une chaîne vide.
    // Il doit contenir entre 3 et 150 caractères.
    @NotBlank(message = "Le titre ne peut pas être vide.")
    @Size(min = 3, max = 150, message = "Le titre doit faire entre 3 et 150 caractères.")
    private String title;

    @NotBlank(message = "L'ISBN ne peut pas être vide.")
    private String isbn;

    // La date de publication ne doit pas être nulle.
    @NotNull(message = "La date de publication est requise.")
    private LocalDate publicationDate;

    // La liste des IDs d'auteurs ne doit pas être vide.
    @NotEmpty(message = "Un livre doit avoir au moins un auteur.")
    private Set<Long> authorIds;}