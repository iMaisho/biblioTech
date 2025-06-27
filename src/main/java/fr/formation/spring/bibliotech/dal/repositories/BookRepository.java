package fr.formation.spring.bibliotech.dal.repositories;

import fr.formation.spring.bibliotech.dal.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}