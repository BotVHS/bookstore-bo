package cat.uvic.teknos.bookstore.services;

import cat.uvic.teknos.bookstore.client.menu.MenuManager;
import cat.uvic.teknos.bookstore.client.utils.InputScanner;
import cat.uvic.teknos.bookstore.services.controllers.*;
import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.RepositoryFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var properties = new Properties();
        properties.load(App.class.getResourceAsStream("/app.properties"));

        RepositoryFactory repositoryFactory = (RepositoryFactory) Class.forName(properties.getProperty("repositoryFactory")).getConstructor().newInstance();
        ModelFactory modelFactory = (ModelFactory) Class.forName(properties.getProperty("modelFactory")).getConstructor().newInstance();

        var controllers = new HashMap<String, Controller>();

        controllers.put("authors", new AuthorController(repositoryFactory, modelFactory));
        controllers.put("books", new BookController(repositoryFactory, modelFactory));
        controllers.put("users", new UserController(repositoryFactory, modelFactory));
        controllers.put("reviews", new ReviewController(repositoryFactory, modelFactory));
        controllers.put("orders", new OrderController(repositoryFactory, modelFactory));

        var requestRouter = new RequestRouterImpl(controllers);
        new Server(requestRouter).start();
    }
}