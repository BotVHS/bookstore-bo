package cat.uvic.teknos.bookstore.client;

import cat.uvic.teknos.bookstore.client.menu.MenuManager;
import cat.uvic.teknos.bookstore.client.utils.InputScanner;

public class App {
    public static void main(String[] args) {
        try {
            MenuManager.showMainMenu();
        } finally {
            InputScanner.close();
        }
    }
}