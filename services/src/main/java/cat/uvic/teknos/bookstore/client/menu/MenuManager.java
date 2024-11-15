package cat.uvic.teknos.bookstore.client.menu;

import cat.uvic.teknos.bookstore.client.managers.*;
import cat.uvic.teknos.bookstore.client.utils.InputScanner;

public class MenuManager {
    public static void showMainMenu() {
        while (true) {
            System.out.println("\n=== Bookstore Management System ===");
            System.out.println("1. Manage Authors");
            System.out.println("2. Manage Books");
            System.out.println("3. Manage Reviews");
            System.out.println("4. Manage Orders");
            System.out.println("5. Manage Users");
            System.out.println("0. Exit");

            int option = InputScanner.readInt("Select an option: ");

            switch (option) {
                case 1:
                    AuthorManager.manage();
                    break;
                case 2:
                    BookManager.manage();
                    break;
                case 3:
                    ReviewManager.manage();
                    break;
                case 4:
                    OrderManager.manage();
                    break;
                case 5:
                    UserManager.manage();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}