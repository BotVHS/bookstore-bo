package cat.teknos.bookstore.domain.jdbc.repositories;

import com.albertdiaz.bookstore.models.User;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserRepository implements com.albertdiaz.bookstore.repositories.UserRepository {
    private static Map<Integer, User> users = new HashMap<Integer, User>();

    static void load() {
        var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try(var inputStream = new ObjectInputStream(new FileInputStream(dataDirectory + "users.ser"))) {
            users = (Map<Integer, User>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static void write() {
        var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try(var outputStream = new ObjectOutputStream(new FileOutputStream(dataDirectory + "users.ser"))) {
            outputStream.writeObject(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User model) {
        if (model.getId() <= 0) {
            // get new id
            var newId = users.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);

            users.put(newId, model);
        } else {
            users.put(model.getId(), model);
        }

        write();
    }

    @Override
    public void delete(User model) {
        users.remove(model.getId());
    }

    @Override
    public User get(Integer id) {
        return users.get(id);
    }

    @Override
    public Set<User> getAll() {
        return new HashSet<>(users.values());
    }

    @Override
    public User getByName(String name) {
        for (User user : users.values()) {
            if (user.getFirstName().equals(name.split(" ")[0]) && user.getLastName().equals(name.split(" ")[1])) {
                return user;
            }
        }
        return null;
    }

}
