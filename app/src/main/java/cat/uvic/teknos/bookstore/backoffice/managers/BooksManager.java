package cat.uvic.teknos.bookstore.backoffice.managers;

import com.albertdiaz.bookstore.models.Author;
import com.albertdiaz.bookstore.models.Book;
import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.AuthorRepository;
import com.albertdiaz.bookstore.repositories.BookRepository;
import cat.teknos.bookstore.domain.jpa.repositories.JpaAuthorRepository;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;

import java.util.Arrays;

import static cat.uvic.teknos.bookstore.backoffice.IOUtils.*;

public class BooksManager {
    private final BufferedReader in;
    private final PrintStream out;
    private final BookRepository bookRepository;
    private final ModelFactory modelFactory;
    private final AuthorRepository authorRepository;

    public BooksManager(BufferedReader in, PrintStream out, BookRepository bookRepository, ModelFactory modelFactory, AuthorRepository authorRepository) {
        this.in = in;
        this.out = out;
        this.bookRepository = bookRepository;
        this.modelFactory = modelFactory;
        this.authorRepository = authorRepository;
    }

    public void start() {
        String command;
        do {
            out.println("Books: ");
            out.println("Type:");
            out.println("1 to insert a new book");
            out.println("2 to update an existing book");
            out.println("3 to find a book by title");
            out.println("4 to show all the books");
            out.println("Type 'exit' to return to the main menu");

            command = readLine(in);

            switch (command) {
                case "1": insert(); break;
                case "2": update(); break;
                case "3": getByTitle(); break;
                case "4": getAll(); break;
            }
        } while (!command.equals("exit"));
    }


    private void insert() {
        var book = modelFactory.createBook();
        out.println("Title: ");
        book.setTitle(readLine(in));
        out.println("Author ID: ");
        int authorId;
        try {
            authorId = Integer.parseInt(readLine(in));
        } catch (NumberFormatException e) {
            out.println("Invalid author ID. Please enter a valid integer ID or type 'exit' to exit.");
            return;
        }

        Author author = authorRepository.get(authorId);
        if (author == null) {
            out.println("Author with ID " + authorId + " does not exist.");
            return;
        }

        book.setAuthor(author);
        out.println("ISBN: ");
        book.setIsbn(readLine(in));
        out.println("Price: ");
        book.setPrice(Float.parseFloat(readLine(in)));
        out.println("Genre: ");
        book.setGenre(readLine(in));
        boolean publishDate = false;
        while (!publishDate) {
            out.println("Publish Date (YYYY-MM-DD): ");
            String publishdateString = readLine(in);
            try {
                LocalDate birthDate = LocalDate.parse(publishdateString, DateTimeFormatter.ISO_LOCAL_DATE);
                book.setPublishDate(birthDate);
                publishDate = true;
            } catch (DateTimeParseException e) {
                out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        out.println("Publisher: ");
        book.setPublisher(readLine(in));
        bookRepository.save(book);
        out.println("Page Number: ");
        book.setPageCount(Integer.parseInt(readLine(in)));
        out.println("Author added successfully."+ book);
    }

    private void update() {
        out.println("Enter book ID to update:");
        int bookId = Integer.parseInt(readLine(in));
        Book book = bookRepository.get(bookId);
        if (book == null) {
            out.println("Book not found.");
            return;
        }

        out.println("Select field to update:");
        out.println("1. Title");
        out.println("2. Author");
        out.println("3. ISBN");
        out.println("4. Price");
        out.println("5. Genre");
        out.println("6. Publish Date");
        out.println("7. Publisher");
        out.println("8. Page Count");
        out.println("9. All");

        String fieldChoice = readLine(in);
        switch (fieldChoice) {
            case "1": updateTitle(book); break;
            case "2": updateAuthor(book); break;
            case "3": updateISBN(book); break;
            case "4": updatePrice(book); break;
            case "5": updateGenre(book); break;
            case "6": updatePublishDate(book); break;
            case "7": updatePublisher(book); break;
            case "8": updatePageCount(book); break;
            case "9": updateAllFields(book); break;
            default: out.println("Invalid choice."); return;
        }

        bookRepository.save(book);
        out.println("Book updated successfully.");
    }

    private void updateTitle(Book book) {
        out.println("Enter new Title:");
        book.setTitle(readLine(in));
    }

    private void updateAuthor(Book book) {
        out.println("Enter new Author ID:");
        int authorId = Integer.parseInt(readLine(in));
        Author author = authorRepository.get(authorId);
        if (author == null) {
            out.println("Author with ID " + authorId + " does not exist.");
            return;
        }
        book.setAuthor(author);
    }

    private void updateISBN(Book book) {
        out.println("Enter new ISBN:");
        book.setIsbn(readLine(in));
    }

    private void updatePrice(Book book) {
        out.println("Enter new Price:");
        book.setPrice(Float.parseFloat(readLine(in)));
    }

    private void updateGenre(Book book) {
        out.println("Enter new Genre:");
        book.setGenre(readLine(in));
    }

    private void updatePublishDate(Book book) {
        out.println("Enter new Publish Date (YYYY-MM-DD):");
        String publishDateString = readLine(in);
        LocalDate publishDate = LocalDate.parse(publishDateString, DateTimeFormatter.ISO_LOCAL_DATE);
        book.setPublishDate(publishDate);
    }

    private void updatePublisher(Book book) {
        out.println("Enter new Publisher:");
        book.setPublisher(readLine(in));
    }

    private void updatePageCount(Book book) {
        out.println("Enter new Page Count:");
        book.setPageCount(Integer.parseInt(readLine(in)));
    }
    private void updateAllFields(Book book) {
        updateTitle(book);
        updateAuthor(book);
        updateISBN(book);
        updatePrice(book);
        updateGenre(book);
        updatePublishDate(book);
        updatePublisher(book);
        updatePageCount(book);
    }

    private void getByTitle() {
        out.println("Enter book title:");
        String title = readLine(in);

        Book book = bookRepository.getByName(title);

        if (book == null) {
            out.println("Book not found.");
        } else {
            System.out.println(AsciiTable.getTable(Arrays.asList(book), Arrays.asList(
                    new Column().header("Id").with(b -> Integer.toString(b.getId())),
                    new Column().header("Title").with(Book::getTitle),
                    new Column().header("Author").with(b -> {
                        Author author = b.getAuthor();
                        return author.getFirstName() + " " + author.getLastName();
                    }),
                    new Column().header("ISBN").with(Book::getIsbn),
                    new Column().header("Price").with(b -> Float.toString(b.getPrice())),
                    new Column().header("Genre").with(Book::getGenre),
                    new Column().header("Publish Date").with(b -> b.getPublishDate().toString()),
                    new Column().header("Publisher").with(Book::getPublisher),
                    new Column().header("Page Count").with(b -> Integer.toString(b.getPageCount()))
            )));
        }
    }

    private void getAll() {
        System.out.println(AsciiTable.getTable(bookRepository.getAll(), Arrays.asList(
                new Column().header("Id").with(book -> Integer.toString(book.getId())),
                new Column().header("Title").with(Book::getTitle),
                new Column().header("Author").with(book -> {
                    Author author = book.getAuthor();
                    return author.getFirstName() + " " + author.getLastName();
                }),
                new Column().header("ISBN").with(Book::getIsbn),
                new Column().header("Price").with(book -> book.getPrice() != null ? Float.toString(book.getPrice()) : "N/A"),
                new Column().header("Genre").with(Book::getGenre),
                new Column().header("Publish Date").with(book -> book.getPublishDate().toString()),
                new Column().header("Publisher").with(Book::getPublisher),
                new Column().header("Page Count").with(book -> Integer.toString(book.getPageCount()))
        )));
    }
}
