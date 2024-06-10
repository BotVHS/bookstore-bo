package cat.uvic.teknos.bookstore.backoffice;

import cat.uvic.teknos.bookstore.backoffice.exceptions.BackOfficeExceptions;

import java.io.BufferedReader;
import java.io.IOException;

public class IOUtils {
    public static String readLine(BufferedReader in) {
        String command;
        try {
            command = in.readLine();
        } catch (IOException e) {
            throw new BackOfficeExceptions("Error while reading the menu option", e);
        }
        return command;
    }
}
