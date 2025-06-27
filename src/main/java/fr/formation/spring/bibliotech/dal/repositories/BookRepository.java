package fr.formation.bibliotech.dal.repositories;

import fr.formation.bibliotech.dal.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}