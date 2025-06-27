package fr.formation.spring.bibliotech.dal.repositories;

import fr.formation.spring.bibliotech.dal.entities.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
