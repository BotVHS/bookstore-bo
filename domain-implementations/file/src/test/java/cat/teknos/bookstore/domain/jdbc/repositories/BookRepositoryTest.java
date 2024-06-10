package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Author;
import cat.teknos.bookstore.domain.jdbc.models.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    @Test
    void load() {
    }

    @Test
    void saveNewBook() {
        var repository = new BookRepository();

        var book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor(new Author());
        book.setIsbn("9781234567890");
        book.setPrice(15.99F);
        book.setGenre("Fiction");
        book.setPublishDate(LocalDate.of(1969, 4, 20));
        book.setPublisher("Planeta");
        book.setPageCount(180);

        repository.save(book);

        assertTrue(book.getId() > 0);
        assertNotNull(repository.get(book.getId()));

        BookRepository.load();

        assertNotNull(repository.get(book.getId()));
        assertEquals("Harry Potter", repository.get(book.getId()).getTitle());
    }

    @Test
    void updateBook() {
        var repository = new BookRepository();

        var book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor(new Author());
        book.setIsbn("9781234567890");
        book.setPrice(15.99F);
        book.setGenre("Fiction");
        book.setPublishDate(LocalDate.of(1969, 4, 20));
        book.setPublisher("Planeta");
        book.setPageCount(180);

        repository.save(book);

        book.setPrice(12.99f);
        repository.save(book);

        assertEquals(12.99f, repository.get(book.getId()).getPrice());
    }

    @Test
    void deleteBook() {
        var repository = new BookRepository();

        var book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor(new Author());
        book.setIsbn("9781234567890");
        book.setPrice(15.99F);
        book.setGenre("Fiction");
        book.setPublishDate(LocalDate.of(1969, 4, 20));
        book.setPublisher("Planeta");
        book.setPageCount(180);

        repository.save(book);

        repository.delete(book);

        assertNull(repository.get(book.getId()));
    }

    @Test
    void getBook() {
        var repository = new BookRepository();

        var book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor(new Author());
        book.setIsbn("9781234567890");
        book.setPrice(15.99F);
        book.setGenre("Fiction");
        book.setPublishDate(LocalDate.of(1969, 4, 20));
        book.setPublisher("Planeta");
        book.setPageCount(180);

        repository.save(book);

        var retrievedBook = repository.get(book.getId());

        assertEquals(book.getId(), retrievedBook.getId());
        assertEquals(book.getTitle(), retrievedBook.getTitle());
        assertEquals(book.getIsbn(), retrievedBook.getIsbn());
        assertEquals(book.getPrice(), retrievedBook.getPrice());
    }

    @Test
    void getAllBooks() {
        var repository = new BookRepository();

        var book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor(new Author());
        book.setIsbn("9781234567890");
        book.setPrice(15.99F);
        book.setGenre("Fiction");
        book.setPublishDate(LocalDate.of(1969, 4, 20));
        book.setPublisher("Planeta");
        book.setPageCount(180);

        var book2 = new Book();
        book2.setTitle("Joc de trons");
        book2.setAuthor(new Author());
        book2.setIsbn("9780061120084");
        book2.setPrice(10.99f);
        book2.setGenre("Fantasia");
        book2.setPublishDate(LocalDate.of(1960, 7, 11));
        book2.setPublisher("Minotauro");
        book2.setPageCount(281);

        repository.save(book);
        repository.save(book2);

        var allBooks = repository.getAll();

        assertEquals(2, allBooks.size());
        assertTrue(allBooks.contains(book));
        assertTrue(allBooks.contains(book2));
    }

    @Test
    void getByNameBook() {
        var repository = new BookRepository();

        var book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor(new Author());
        book.setIsbn("9781234567890");
        book.setPrice(15.99F);
        book.setGenre("Fiction");
        book.setPublishDate(LocalDate.of(1969, 4, 20));
        book.setPublisher("Planeta");
        book.setPageCount(180);

        repository.save(book);

        var retrievedBook = repository.getByName("Harry Potter");

        assertEquals(book.getId(), retrievedBook.getId());
        assertEquals(book.getTitle(), retrievedBook.getTitle());
        assertEquals(book.getIsbn(), retrievedBook.getIsbn());
        assertEquals(book.getPrice(), retrievedBook.getPrice());
        assertEquals(book.getGenre(), retrievedBook.getGenre());
        assertEquals(book.getPublishDate(), retrievedBook.getPublishDate());
        assertEquals(book.getPublisher(), retrievedBook.getPublisher());
        assertEquals(book.getPageCount(), retrievedBook.getPageCount());
    }
}