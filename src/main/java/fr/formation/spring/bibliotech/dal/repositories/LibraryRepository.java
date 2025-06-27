package fr.formation.spring.bibliotech.dal.repositories;

import fr.formation.spring.bibliotech.dal.entities.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
