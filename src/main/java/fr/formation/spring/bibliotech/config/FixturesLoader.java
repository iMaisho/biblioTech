package fr.formation.spring.bibliotech.config;

import fr.formation.spring.bibliotech.dal.entities.Author;
import fr.formation.spring.bibliotech.dal.entities.Book;
import fr.formation.spring.bibliotech.dal.entities.Library;
import fr.formation.spring.bibliotech.dal.repositories.AuthorRepository;
import fr.formation.spring.bibliotech.dal.repositories.BookRepository;
import fr.formation.spring.bibliotech.dal.repositories.LibraryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component // Pour que Spring gère ce bean
public class FixturesLoader implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    // Injection des dépendances par le constructeur
    public FixturesLoader(AuthorRepository authorRepository,
                      BookRepository bookRepository,
                      LibraryRepository libraryRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Création des auteurs
        Author author1 = new Author();
        author1.setFirstName("J.K.");
        author1.setLastName("Rowling");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setFirstName("J.R.R.");
        author2.setLastName("Tolkien");
        authorRepository.save(author2);

        // Création des livres
        Book book1 = new Book();
        book1.setTitle("Harry Potter and the Sorcerer's Stone");
        book1.setIsbn("978-0439708180");
        book1.setPublicationDate(LocalDate.of(1997, 6, 26));
        book1.setAuthors(Set.of(author1)); // Associe le livre à l'auteur
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("The Hobbit");
        book2.setIsbn("978-0345339683");
        book2.setPublicationDate(LocalDate.of(1937, 9, 21));
        book2.setAuthors(Set.of(author2));
        bookRepository.save(book2);

        // Création des bibliothèques
        Library library1 = new Library();
        library1.setName("Bibliothèque Nationale de France");
        library1.setAddress("Quai François Mauriac, 75706 Paris");
        libraryRepository.save(library1);
    }
}
