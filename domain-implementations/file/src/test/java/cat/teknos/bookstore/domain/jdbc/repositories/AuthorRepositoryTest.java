package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AuthorRepositoryTest {
    @Test
    void saveNewAuthor() {
        var repository = new AuthorRepository();

        var author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setBiography("Es de vic");
        author.setBirthDate(LocalDate.of(2024, 4, 18));
        author.setNationality("Vic");

        repository.save(author);

        assertTrue(author.getId() > 0);
        assertNotNull(repository.get(author.getId()));

        AuthorRepository.load();

        assertNotNull(repository.get(author.getId()));
        assertEquals("John", repository.get(author.getId()).getFirstName());
    }

    @Test
    void updateAuthor() {
        var repository = new AuthorRepository();

        var author = new Author();
        author.setFirstName("Jane");
        author.setLastName("Smith");
        author.setBiography("No es de vic");
        author.setBirthDate(LocalDate.of(2000  , 5, 10));
        author.setNationality("Catalunya");

        repository.save(author);

        author.setBiography("Jefa jefa");

        repository.save(author);
        assertEquals("Jefa jefa", repository.get(author.getId()).getBiography());
    }
    void load() {

    }

    @Test
    void deleteAuthor() {
        var repository = new AuthorRepository();

        var author = new Author();
        author.setFirstName("Emilia");
        author.setLastName("Petro");
        author.setBiography("Proba d'esborrament");
        author.setBirthDate(LocalDate.of(1990, 3, 10));
        author.setNationality("Francia");

        repository.save(author);

        repository.delete(author);

        assertNull(repository.get(author.getId()));
    }

    @Test
    void getAuthor() {
        var repository = new AuthorRepository();

        var author = new Author();
        author.setFirstName("Francesc");
        author.setLastName("Josep");
        author.setBiography("Poeta de renom");
        author.setBirthDate(LocalDate.of(1978, 10, 25));
        author.setNationality("Espanyol");

        repository.save(author);
        assertNotNull(repository.get(author.getId()));
    }

    @Test
    void getAllAuthors() {
        var repository = new AuthorRepository();

        var author1 = new Author();
        author1.setFirstName("Alice");
        author1.setLastName("Smith");
        author1.setBiography("Una novelista destacada");
        author1.setBirthDate(LocalDate.of(1985, 7, 15));
        author1.setNationality("Alemanya");

        var author2 = new Author();
        author2.setFirstName("Emilia");
        author2.setLastName("Petro");
        author2.setBiography("Proba d'obtencio");
        author2.setBirthDate(LocalDate.of(1990, 3, 10));
        author2.setNationality("Francia");

        repository.save(author1);
        repository.save(author2);

        var allAuthors = repository.getAll();

        assertEquals(2, allAuthors.size());
        assertTrue(allAuthors.contains(author1));
        assertTrue(allAuthors.contains(author2));
    }

    @Test
    void getByNameAuthor() {
        var repository = new AuthorRepository();

        var author = new Author();
        author.setFirstName("Jane");
        author.setLastName("Smith");
        author.setBiography("No es de vic");
        author.setBirthDate(LocalDate.of(2000  , 5, 10));
        author.setNationality("Catalunya");

        repository.save(author);

        var retrievedAuthor = repository.getByName("Jane Smith");

        assertNotNull(retrievedAuthor);
        assertEquals(author.getFirstName(), retrievedAuthor.getFirstName());
        assertEquals(author.getLastName(), retrievedAuthor.getLastName());
        assertEquals(author.getBiography(), retrievedAuthor.getBiography());
        assertEquals(author.getBirthDate(), retrievedAuthor.getBirthDate());
        assertEquals(author.getNationality(), retrievedAuthor.getNationality());
    }
}