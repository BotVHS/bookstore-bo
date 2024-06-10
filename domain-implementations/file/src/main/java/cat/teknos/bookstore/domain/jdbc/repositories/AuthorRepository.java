package cat.teknos.bookstore.domain.jdbc.repositories;

import com.albertdiaz.bookstore.models.Author;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AuthorRepository implements com.albertdiaz.bookstore.repositories.AuthorRepository {
    private static Map<Integer, Author> authors = new HashMap<>();

    static void load() {
        var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try(var inputStream = new ObjectInputStream(new FileInputStream(dataDirectory + "authors.ser"))) {
            authors = (Map<Integer, Author>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static void write() {
        var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try(var outputStream = new ObjectOutputStream(new FileOutputStream(dataDirectory + "authors.ser"))) {
            outputStream.writeObject(authors);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void save(Author model) {
        if (model.getId() <= 0) {
            // get new id
            var newId = authors.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);

            authors.put(newId, model);
        } else {
            authors.put(model.getId(), model);
        }

        write();
    }

    @Override
    public void delete(Author model) {
        authors.remove(model.getId());
    }

    @Override
    public Author get(Integer id) {
        return authors.get(id);
    }

    @Override
    public Set<Author> getAll() {
        return new HashSet<>(authors.values());
    }
    @Override
    public Author getByName(String name) {
        for (Author author : authors.values()) {
            if (author.getFirstName().equals(name.split(" ")[0]) && author.getLastName().equals(name.split(" ")[1])) {
                return author;
            }
        }
        return null;
    }
}
