package cat.teknos.bookstore.domain.jdbc.repositories;

import com.albertdiaz.bookstore.models.Review;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReviewRepository implements com.albertdiaz.bookstore.repositories.ReviewRepository {
    private static Map<Integer, Review> reviews = new HashMap<Integer, Review>();


    static void load() {
        var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try(var inputStream = new ObjectInputStream(new FileInputStream(dataDirectory + "review.ser"))) {
            reviews = (Map<Integer, Review>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static void write() {
        var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try(var outputStream = new ObjectOutputStream(new FileOutputStream(dataDirectory + "review.ser"))) {
            outputStream.writeObject(reviews);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Review model) {
        if (model.getId() <= 0) {
            // get new id
            var newId = reviews.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);

            reviews.put(newId, model);
        } else {
            reviews.put(model.getId(), model);
        }

        write();
    }

    @Override
    public void delete(Review model) {
        reviews.remove(model.getId());
    }

    @Override
    public Review get(Integer id) {
        return reviews.get(id);
    }

    @Override
    public Set<Review> getAll() {
        return new HashSet<>(reviews.values());
    }
    @Override
    public Review getByName(String name) {
        return null;
    }
}
