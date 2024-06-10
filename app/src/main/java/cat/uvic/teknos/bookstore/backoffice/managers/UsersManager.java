package cat.uvic.teknos.bookstore.backoffice.managers;

import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.models.User;
import com.albertdiaz.bookstore.repositories.UserRepository;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;

import static cat.uvic.teknos.bookstore.backoffice.IOUtils.readLine;

public class UsersManager {
    private final BufferedReader in;
    private final PrintStream out;
    private final UserRepository userRepository;
    private final ModelFactory modelFactory;

    public UsersManager(BufferedReader in, PrintStream out, UserRepository userRepository, ModelFactory modelFactory) {
        this.in = in;
        this.out = out;
        this.userRepository = userRepository;
        this.modelFactory = modelFactory;
    }

    public void start() {
        String command;
        do {
            out.println("Users Management: ");
            out.println("Type:");
            out.println("1 to insert a new user");
            out.println("2 to update an existing user");
            out.println("3 to find a user by name");
            out.println("4 to show all users");
            out.println("5 to delete a user");
            out.println("Type 'exit' to return to the main menu");

            command = readLine(in);

            switch (command) {
                case "1": insert(); break;
                case "2": update(); break;
                case "3": getByName(); break;
                case "4": getAll(); break;
                case "5": delete(); break;
            }
        } while (!command.equals("exit"));
    }


    private void insert() {
        var user = modelFactory.createUser();
        out.println("First Name: ");
        user.setFirstName(readLine(in));
        out.println("Last Name: ");
        user.setLastName(readLine(in));
        out.println("Email: ");
        user.setEmail(readLine(in));
        out.println("Password: ");
        user.setPasswordHash(readLine(in));
        out.println("Address: ");
        user.setAddress(readLine(in));
        out.println("City: ");
        user.setCity(readLine(in));
        out.println("Country: ");
        user.setCountry(readLine(in));
        out.println("Postal Code: ");
        user.setPostalCode(readLine(in));
        out.println("Join Date (YYYY-MM-DD): ");
        LocalDate joinDate;
        boolean validDate = false;
        while (!validDate) {
            try {
                joinDate = LocalDate.parse(readLine(in), DateTimeFormatter.ISO_LOCAL_DATE);
                user.setJoinDate(joinDate);
                validDate = true;
            } catch (DateTimeParseException e) {
                out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        userRepository.save(user);
        out.println("User added successfully: " + user);
    }

    private void update() {
        out.println("Enter user ID to update:");
        int userId = Integer.parseInt(readLine(in));
        User user = userRepository.get(userId);
        if (user == null) {
            out.println("User not found.");
            return;
        }

        out.println("Select field to update:");
        out.println("1. First Name");
        out.println("2. Last Name");
        out.println("3. Email");
        out.println("4. Password");
        out.println("5. Address");
        out.println("6. City");
        out.println("7. Country");
        out.println("8. Postal Code");
        out.println("9. Join Date");
        out.println("10. All");

        String fieldChoice = readLine(in);
        switch (fieldChoice) {
            case "1" -> updateFirstName(user);
            case "2" -> updateLastName(user);
            case "3" -> updateEmail(user);
            case "4" -> updatePassword(user);
            case "5" -> updateAddress(user);
            case "6" -> updateCity(user);
            case "7" -> updateCountry(user);
            case "8" -> updatePostalCode(user);
            case "9" -> updateJoinDate(user);
            case "10" -> updateAllFields(user);
            default -> out.println("Invalid choice.");
        }

        userRepository.save(user);
        out.println("User updated successfully.");
    }

    private void updateFirstName(User user) {
        out.println("Enter new First Name:");
        user.setFirstName(readLine(in));
    }

    private void updateLastName(User user) {
        out.println("Enter new Last Name:");
        user.setLastName(readLine(in));
    }

    private void updateEmail(User user) {
        out.println("Enter new Email:");
        user.setEmail(readLine(in));
    }

    private void updatePassword(User user) {
        out.println("Enter new Password:");
        user.setPasswordHash(readLine(in));
    }

    private void updateAddress(User user) {
        out.println("Enter new Address:");
        user.setAddress(readLine(in));
    }

    private void updateCity(User user) {
        out.println("Enter new City:");
        user.setCity(readLine(in));
    }

    private void updateCountry(User user) {
        out.println("Enter new Country:");
        user.setCountry(readLine(in));
    }

    private void updatePostalCode(User user) {
        out.println("Enter new Postal Code:");
        user.setPostalCode(readLine(in));
    }

    private void updateJoinDate(User user) {
        out.println("Enter new Join Date (YYYY-MM-DD):");
        LocalDate joinDate;
        boolean validDate = false;
        while (!validDate) {
            try {
                joinDate = LocalDate.parse(readLine(in), DateTimeFormatter.ISO_LOCAL_DATE);
                user.setJoinDate(joinDate);
                validDate = true;
            } catch (DateTimeParseException e) {
                out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private void updateAllFields(User user) {
        updateFirstName(user);
        updateLastName(user);
        updateEmail(user);
        updatePassword(user);
        updateAddress(user);
        updateCity(user);
        updateCountry(user);
        updatePostalCode(user);
        updateJoinDate(user);
    }

    private void getByName() {
        out.println("Enter user name:");
        String name = readLine(in);

        User user = userRepository.getByName(name);

        if (user == null) {
            out.println("User not found.");
        } else {
            String table = AsciiTable.getTable(Arrays.asList(user), Arrays.asList(
                    new Column().header("Id").with(u -> Integer.toString(u.getId())),
                    new Column().header("First Name").with(User::getFirstName),
                    new Column().header("Last Name").with(User::getLastName),
                    new Column().header("Email").with(User::getEmail),
                    new Column().header("Address").with(User::getAddress),
                    new Column().header("City").with(User::getCity),
                    new Column().header("Country").with(User::getCountry),
                    new Column().header("Postal Code").with(User::getPostalCode),
                    new Column().header("Join Date").with(u -> u.getJoinDate().toString())
            ));

            out.println(table);
        }
    }

    private void delete() {
        out.println("Enter user ID to delete:");
        int userId = Integer.parseInt(readLine(in));
        User user = userRepository.get(userId);
        if (user == null) {
            out.println("User not found.");
            return;
        }
        userRepository.delete(user);
        out.println("User deleted successfully.");
    }

    private void getAll() {
        out.println("All Users:");
        System.out.println(AsciiTable.getTable(userRepository.getAll(), Arrays.asList(
                new Column().header("Id").with(u -> Integer.toString(u.getId())),
                new Column().header("First Name").with(User::getFirstName),
                new Column().header("Last Name").with(User::getLastName),
                new Column().header("Email").with(User::getEmail),
                new Column().header("Address").with(User::getAddress),
                new Column().header("City").with(User::getCity),
                new Column().header("Country").with(User::getCountry),
                new Column().header("Postal Code").with(User::getPostalCode),
                new Column().header("Join Date").with(u -> u.getJoinDate().toString()))));

        }
    }
