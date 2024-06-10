package cat.teknos.bookstore.domain.jdbc.repositories;

import com.albertdiaz.bookstore.models.Book;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BookRepository implements com.albertdiaz.bookstore.repositories.BookRepository {
    private static Map<Integer, Book> books = new HashMap<Integer, Book>();

    static void load(){
        var dataDirectory = System.getProperty("user.dir")+ "/src/main/resources/data/";

        try(var inputStream = new ObjectInputStream(new FileInputStream(dataDirectory + "books.ser"))) {
            books = (Map<Integer, Book>) inputStream.readObject();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void write() {
        var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try(var outputStream = new ObjectOutputStream(new FileOutputStream(dataDirectory + "books.ser"))) {
            outputStream.writeObject(books);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Book model) {
        if (model.getId() <= 0) {
            var newId = books.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);

            books.put(newId, model);
        } else {
            books.put(model.getId(), model);
        }

        write();
    }

    @Override
    public void delete(Book model) {
        books.remove(model.getId());
    }


    @Override
    public Book get(Integer id) {
        return books.get(id);
    }

    @Override
    public Set<Book> getAll() {
        return new HashSet<>(books.values());
    }

    @Override
    public Book getByName(String name) {
        for (Book book : books.values()) {
            if (book.getTitle().equals(name)) {
                return book;
            }
        }
        return null;
    }
}
