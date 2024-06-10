package cat.uvic.teknos.bookstore.backoffice.managers;

import com.albertdiaz.bookstore.models.Author;
import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.AuthorRepository;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;


import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.Arrays;

import static cat.uvic.teknos.bookstore.backoffice.IOUtils.*;

public class AuthorsManager {
    private final BufferedReader in;
    private final PrintStream out;
    private final AuthorRepository authorRepository;
    private final ModelFactory modelFactory;
    public AuthorsManager(BufferedReader in, PrintStream out, AuthorRepository authorRepository, ModelFactory modelFactory) {
        this.in  = in;
        this.out = out;
        this.authorRepository = authorRepository;
        this.modelFactory = modelFactory;
    }

    public void start() {
        String command;
        do {
            out.println("Authors: ");
            out.println("Type:");
            out.println("1 to insert a new author");
            out.println("2 to update an existing author");
            out.println("3 to find a author by the name");
            out.println("4 to show all the authors");
            out.println("5 to delete an author");
            out.println("Type 'exit' to return to the main menu");

            command = readLine(in);

            switch (command) {
                case "1": insert(); break;
                case "2": update(); break;
                case "3": getByName(); break;
                case "4": getAll(); break;
                case "5": delete(); break;
            }
        } while (!command.equals("exit"));
    }

    private void delete() {
        out.println("Enter author ID to delete:");
        int authorId = Integer.parseInt(readLine(in));
        Author author = authorRepository.get(authorId);
        if (author == null) {
            out.println("Author not found.");
            return;
        }

        authorRepository.delete(author);
        out.println("Author deleted successfully.");
    }
    private void insert() {
        var author = modelFactory.createAuthor();
        out.println("First Name: ");
        author.setFirstName(readLine(in));
        out.println("Last Name: ");
        author.setLastName(readLine(in));
        out.println("Biography: ");
        author.setBiography(readLine(in));
        boolean validDate = false;
        while (!validDate) {
            out.println("Birthdate (YYYY-MM-DD): ");
            String birthDateString = readLine(in);
            try {
                LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ISO_LOCAL_DATE);
                author.setBirthDate(birthDate);
                validDate = true;
            } catch (DateTimeParseException e) {
                out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        out.println("Nationality: ");
        author.setNationality(readLine(in));
        authorRepository.save(author);
        out.println("Author added successfully."+ author);
    }

    private void update() {
        out.println("Enter author ID to update:");
        int authorId = Integer.parseInt(readLine(in));
        Author author = authorRepository.get(authorId);
        if (author == null) {
            out.println("Author not found.");
            return;
        }

        out.println("Select field to update:");
        out.println("1. First Name");
        out.println("2. Last Name");
        out.println("3. Biography");
        out.println("4. Birthdate");
        out.println("5. Nationality");
        out.println("6. All");

        String fieldChoice = readLine(in);
        switch (fieldChoice) {
            case "1": updateFirstName(author); break;
            case "2": updateLastName(author); break;
            case "3": updateBiography(author); break;
            case "4": updateBirthdate(author); break;
            case "5": updateNationality(author); break;
            case "6": updateAllFields(author); break;
            default: out.println("Invalid choice."); return;
        }

        authorRepository.save(author);
        out.println("Author updated successfully.");
    }

    private void updateFirstName(Author author) {
        out.println("Enter new First Name:");
        author.setFirstName(readLine(in));
    }

    private void updateLastName(Author author) {
        out.println("Enter new Last Name:");
        author.setLastName(readLine(in));
    }

    private void updateBiography(Author author) {
        out.println("Enter new Biography:");
        author.setBiography(readLine(in));
    }

    private void updateBirthdate(Author author) {
        out.println("Enter new Birthdate (YYYY-MM-DD):");
        String birthDateString = readLine(in);
        LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ISO_LOCAL_DATE);
        author.setBirthDate(birthDate);
    }

    private void updateNationality(Author author) {
        out.println("Enter new Nationality:");
        author.setNationality(readLine(in));
    }

    private void updateAllFields(Author author) {
        updateFirstName(author);
        updateLastName(author);
        updateBiography(author);
        updateBirthdate(author);
        updateNationality(author);
    }


    private void getByName() {
        out.println("Enter author name:");
        String name = readLine(in);

        Author author = authorRepository.getByName(name);

        if (author == null) {
            out.println("Author not found.");
        } else {
            System.out.println(AsciiTable.getTable(Arrays.asList(author), Arrays.asList(
                    new Column().header("Id").with(a -> Integer.toString(a.getId())),
                    new Column().header("First Name").with(Author::getFirstName),
                    new Column().header("Last Name").with(Author::getLastName),
                    new Column().header("Biography").with(Author::getBiography),
                    new Column().header("DoB").with(a -> a.getBirthDate().toString()),
                    new Column().header("Nationality").with(Author::getNationality))));
        }
    }

    private void getAll() {
        System.out.println(AsciiTable.getTable(authorRepository.getAll(), Arrays.asList(
                new Column().header("Id").with(a -> Integer.toString(a.getId())),
                new Column().header("First Name").with(Author::getFirstName),
                new Column().header("Last Name").with(Author::getLastName),
                new Column().header("Biography").with(Author::getBiography),
                new Column().header("DoB").with(a -> {
                    LocalDate birthDate = a.getBirthDate();
                    return (birthDate != null) ? birthDate.toString() : "N/A";
                }),
                new Column().header("Nationality").with(Author::getNationality))));
    }
}
