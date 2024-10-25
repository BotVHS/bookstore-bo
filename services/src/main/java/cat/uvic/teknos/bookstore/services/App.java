package cat.uvic.teknos.bookstore.services;

import cat.uvic.teknos.bookstore.services.controllers.AuthorController;
import cat.uvic.teknos.bookstore.services.controllers.Controller;
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

        // TODO: review how to deserialize json objects
        controllers.put("authors", new AuthorController(repositoryFactory, modelFactory));

        var requestRouter = new RequestRouterImpl(controllers);
        new Server(requestRouter).start();
    }
}