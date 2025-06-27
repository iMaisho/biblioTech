package fr.formation.spring.bibliotech.config;

import fr.formation.spring.bibliotech.dal.entities.Author;
import fr.formation.spring.bibliotech.dal.entities.Book;
import fr.formation.spring.bibliotech.dal.entities.Library;
import fr.formation.spring.bibliotech.dal.repositories.AuthorRepository;
import fr.formation.spring.bibliotech.dal.repositories.BookRepository;
import fr.formation.spring.bibliotech.dal.repositories.LibraryRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component // Pour que Spring gère ce bean
public class FixturesLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(FixturesLoader.class);

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    public FixturesLoader(AuthorRepository authorRepository,
                      BookRepository bookRepository,
                      LibraryRepository libraryRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    @Override
    @Transactional // Transaction pour de meilleures performances sur les écritures multiples
    public void run(String... args) throws Exception {
        if (bookRepository.count() > 0 || authorRepository.count() > 0) {
            log.info("La base de données contient déjà des données. Le seeder ne s'exécutera pas.");
            return;
        }

        log.info("Démarrage du seeder pour la base de données BiblioTech...");

        List<Author> authors = createAuthors();
        createLibraries();

        createComputerScienceBooks(authors, 150);

        log.info("Le seeder a terminé son travail. La base est prête !");
    }

    private List<Author> createAuthors() {
        log.info("Création des auteurs de référence...");
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Martin", "Fowler"));
        authors.add(new Author("Robert C.", "Martin"));
        authors.add(new Author("Kent", "Beck"));
        authors.add(new Author("Joshua", "Bloch"));
        authors.add(new Author("Erich", "Gamma"));
        authors.add(new Author("Richard", "Helm"));
        authors.add(new Author("Ralph", "Johnson"));
        authors.add(new Author("John", "Vlissides"));
        authors.add(new Author("Donald", "Knuth"));
        authors.add(new Author("Bjarne", "Stroustrup"));
        authors.add(new Author("James", "Gosling"));
        authors.add(new Author("Guido", "van Rossum"));
        authors.add(new Author("Kathy", "Sierra"));
        authors.add(new Author("Bert", "Bates"));
        authors.add(new Author("Craig", "Walls"));
        authors.add(new Author("Sam", "Newman"));
        authors.add(new Author("Andrew", "Tanenbaum"));
        authors.add(new Author("Thomas H.", "Cormen"));
        authors.add(new Author("Charles E.", "Leiserson"));
        authors.add(new Author("Ronald L.", "Rivest"));
        authors.add(new Author("Clifford", "Stein"));

        return authorRepository.saveAll(authors);
    }

    private void createLibraries() {
        log.info("Création des bibliothèques...");
        Library lib1 = new Library("Bibliothèque Tech de Paris", "1 Rue de la Tech, 75001 Paris");
        Library lib2 = new Library("Pôle Numérique de Lyon", "42 Avenue du Code, 69002 Lyon");
        libraryRepository.save(lib1);
        libraryRepository.save(lib2);
    }

    private void createComputerScienceBooks(List<Author> authors, int numberOfBooks) {
        log.info("Génération de {} livres sur l'informatique...", numberOfBooks);
        Random random = new Random();
        Set<String> usedIsbns = new HashSet<>();

        List<String> topics = List.of(
                "Java", "Python", "JavaScript", "C++", "Spring Boot", "React", "Angular",
                "Microservices", "Docker", "Kubernetes", "Cloud Computing", "AWS", "Azure",
                "Data Structures", "Algorithms", "Machine Learning", "Deep Learning",
                "Cybersecurity", "Blockchain", "REST APIs", "GraphQL", "DevOps",
                "CI/CD", "Agile", "Scrum", "Software Architecture", "Clean Code",
                "Design Patterns", "Domain-Driven Design", "TDD", "BDD", "SQL", "NoSQL", "PHP", "Erlang",
                "Elixir", "Haskell", "Android", "Kotlin", "Symfony", "Angular", "React", "Flutter",
                "MySQL", "PostgreSQL", "DB2", "Oracle", "MariaDB", "MongoDB", "Neo4J", ""
        );
        List<String> qualifiers = List.of(
                "in Action", "A Practical Guide", "The Definitive Guide", "Mastery",
                "for the Enterprise", "From Scratch", "Fundamentals",
                "Illustrated", "Advanced Techniques", "Cookbook", "for Dummies",
                "The Missing Manual", "Patterns & Practices", "in depth", "in a week", "for beginners",
                "step by step", "a primer", "by example", "up and running", "unlocked", "for the expert",
                "the complete reference", "the good parts", "unleashed"
        );

        for (int i = 0; i < numberOfBooks; i++) {
            Book book = new Book();

            // Générer un titre unique
            String topic1 = topics.get(random.nextInt(topics.size()));
            String topic2 = topics.get(random.nextInt(topics.size()));
            String qualifier = qualifiers.get(random.nextInt(qualifiers.size()));
            if (random.nextBoolean() && !topic1.equals(topic2)) {
                book.setTitle(topic1 + " & " + topic2 + ": " + qualifier);
            } else {
                book.setTitle(topic1 + " " + qualifier);
            }

            // Générer un ISBN unique
            String isbn;
            do {
                isbn = String.format("978-1-%04d-%05d-%d",
                        random.nextInt(10000), random.nextInt(100000), random.nextInt(10));
            } while (usedIsbns.contains(isbn));
            usedIsbns.add(isbn);
            book.setIsbn(isbn);

            // Générer une date de publication
            long minDay = LocalDate.of(1980, Month.JANUARY, 1).toEpochDay();
            long maxDay = LocalDate.now().toEpochDay();
            long randomDay = minDay + random.nextInt((int) (maxDay - minDay));
            book.setPublicationDate(LocalDate.ofEpochDay(randomDay));


            // Associer des auteurs (avec chance de co-écriture)
            Set<Author> bookAuthors = new HashSet<>();
            Collections.shuffle(authors);
            bookAuthors.add(authors.get(0));

            // 25% de chance d'avoir un deuxième auteur
            if (random.nextDouble() < 0.25 && authors.size() > 1) {
                bookAuthors.add(authors.get(1));
            }
            // 5% de chance d'avoir un troisième auteur
            if (random.nextDouble() < 0.05 && authors.size() > 2) {
                bookAuthors.add(authors.get(2));
            }

            book.setAuthors(bookAuthors);
            bookRepository.save(book);
        }
        log.info("{} livres ont été générés.", numberOfBooks);
    }
}
