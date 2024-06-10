package cat.uvic.teknos.bookstore.backoffice;

import cat.uvic.teknos.bookstore.backoffice.managers.AuthorsManager;
import cat.uvic.teknos.bookstore.backoffice.managers.BooksManager;
import cat.uvic.teknos.bookstore.backoffice.managers.UsersManager;
import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.RepositoryFactory;

import java.io.*;
import static cat.uvic.teknos.bookstore.backoffice.IOUtils.*;

public class BackOffice {
    private final BufferedReader in;
    private final PrintStream out;
    private final RepositoryFactory repositoryFactory;
    private final ModelFactory modelFactory;

    public BackOffice(InputStream inputStream, OutputStream outputStream, RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.in = new BufferedReader(new InputStreamReader(inputStream));
        this.out = new PrintStream(outputStream);
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    public void start(){
        showWelcomeMessage();

        var command = "";
        do{
            showMainMenu();
            command = readLine(in);

            switch(command){
                case "1" -> manageAuthor();
                case "2" -> manageBook();
                case "3" -> manageUser();
            }

        } while(!command.equals("exit"));

        out.println("Bye!");
    }

    private void manageUser() {
        new UsersManager(in, out, repositoryFactory.getUserRepository(), modelFactory).start();
    }

    private void manageAuthor() {
        new AuthorsManager(in, out, repositoryFactory.getAuthorRepository(), modelFactory).start();
    }

    private void manageBook() {
        new BooksManager(in, out, repositoryFactory.getBookRepository(), modelFactory, repositoryFactory.getAuthorRepository()).start();
    }

    private void showWelcomeMessage() {
        out.println("Welcome to the Book Store Back Office");
        out.println("Select a menu option or type exit to exit the application: ");
    }

    private void showMainMenu() {
        out.println("1. Author");
        out.println("2. Book");
        out.println("3. Users");
        out.println("4. Orders");
    }
}
