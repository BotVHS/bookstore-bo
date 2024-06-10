package cat.teknos.bookstore.domain.jdbc.repositories;

import com.albertdiaz.bookstore.models.OrderDetail;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrderDetailRepository implements com.albertdiaz.bookstore.repositories.OrderDetailRepository {
    private static Map<Integer, OrderDetail> orderDetails = new HashMap<Integer, OrderDetail>();

    static void load() {
        var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try(var inputStream = new ObjectInputStream(new FileInputStream(dataDirectory + "orderDetails.ser"))) {
            orderDetails = (Map<Integer, OrderDetail>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static void write() {
        var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try(var outputStream = new ObjectOutputStream(new FileOutputStream(dataDirectory + "orderDetails.ser"))) {
            outputStream.writeObject(orderDetails);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(OrderDetail model) {
        if (model.getId() <= 0) {
            // get new id
            var newId = orderDetails.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);

            orderDetails.put(newId, model);
        } else {
            orderDetails.put(model.getId(), model);
        }

        write();
    }

    @Override
    public void delete(OrderDetail model) {
        orderDetails.remove(model.getId());
    }

    @Override
    public OrderDetail get(Integer id) {
        return orderDetails.get(id);
    }

    public Set<OrderDetail> getAll() {
        return new HashSet<>(orderDetails.values());
    }

    @Override
    public OrderDetail getByOrderId(Integer name) {
        return null;
    }

}
