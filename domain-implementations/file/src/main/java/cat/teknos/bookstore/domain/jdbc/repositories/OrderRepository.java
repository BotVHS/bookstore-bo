package cat.teknos.bookstore.domain.jdbc.repositories;

import com.albertdiaz.bookstore.models.Order;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrderRepository implements com.albertdiaz.bookstore.repositories.OrderRepository {
    private static Map<Integer, Order> orders = new HashMap<>();

    static void load() {
        var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try(var inputStream = new ObjectInputStream(new FileInputStream(dataDirectory + "order.ser"))) {
            orders = (Map<Integer, Order>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static void write() {
        var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try(var outputStream = new ObjectOutputStream(new FileOutputStream(dataDirectory + "orders.ser"))) {
            outputStream.writeObject(orders);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void save(Order model) {
        if (model.getId() <= 0) {
            var newId = orders.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);

            orders.put(newId, model);
        } else {
            orders.put(model.getId(), model);
        }

        write();
    }

    @Override
    public void delete(Order model) {
        orders.remove(model.getId());
    }


    @Override
    public Order get(Integer id) {
        return orders.get(id);
    }

    @Override
    public Set<Order> getAll() {
        return new HashSet<>(orders.values());
    }

    @Override
    public Order getByName(String name) {
        return null;
    }
}
