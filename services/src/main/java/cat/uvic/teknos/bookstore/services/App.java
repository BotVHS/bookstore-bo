package cat.uvic.teknos.bookstore.services;

import cat.uvic.teknos.bookstore.services.controllers.*;
import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.RepositoryFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        var properties = new Properties();
        properties.load(App.class.getResourceAsStream("/app.properties"));
        validateSecurityConfig(properties);
        RepositoryFactory repositoryFactory = (RepositoryFactory) Class.forName(
                properties.getProperty("repositoryFactory")
        ).getConstructor().newInstance();

        ModelFactory modelFactory = (ModelFactory) Class.forName(
                properties.getProperty("modelFactory")
        ).getConstructor().newInstance();

        var controllers = new HashMap<String, Controller>();
        controllers.put("authors", new AuthorController(repositoryFactory, modelFactory));
        controllers.put("books", new BookController(repositoryFactory, modelFactory));
        controllers.put("users", new UserController(repositoryFactory, modelFactory));
        controllers.put("reviews", new ReviewController(repositoryFactory, modelFactory));
        controllers.put("orders", new OrderController(repositoryFactory, modelFactory));

        try {
            new Server(controllers, properties).start();
        } catch (Exception e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void validateSecurityConfig(Properties properties) {
        String keystorePath = properties.getProperty("server.keystore.path");
        String keystorePassword = properties.getProperty("server.keystore.password");

        if (keystorePath == null || keystorePath.isEmpty()) {
            throw new IllegalStateException("Missing server.keystore.path in app.properties");
        }
        if (keystorePassword == null || keystorePassword.isEmpty()) {
            throw new IllegalStateException("Missing server.keystore.password in app.properties");
        }

        if (App.class.getResourceAsStream(keystorePath) == null) {
            throw new IllegalStateException("Keystore file not found: " + keystorePath);
        }
    }
}