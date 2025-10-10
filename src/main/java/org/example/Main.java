package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LibrarySystem librarySystem = new LibrarySystem();

    public static void main(String[] args) {
        librarySystem.initializeLibrary();
        System.out.println("=== Welcome to the Library ===");
        mainLoop();
        System.out.println("Thanks for using our library");
    }

    private static void mainLoop() {
        while (true) {
            if (!librarySystem.loggedIn()) {
                if (!userLogin()) {
                    return;
                }
            }
            userMenuLoop();
        }
    }

    private static boolean userLogin() {
        while (true) {
            System.out.println();
            System.out.println("Please choose:");
            System.out.println(" 1) Login");
            System.out.println(" 0) Exit");
            System.out.print("> ");
            String choice = scanner.nextLine().trim();
            if (choice.equals("0")) return false;
            if (choice.equals("1")) {
                System.out.print("Enter borrower name: ");
                String name = scanner.nextLine().trim();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                if (authenticate(name, password)) {
                    System.out.println("Login successful. Welcome!");
                    return true;
                } else {
                    System.out.println("Invalid credentials. Try again");
                }
            } else {
                System.out.println("Unknown choice. Try again");
            }
        }
    }

    private static boolean authenticate(String name, String password) {
        return librarySystem.authenticate(password);
    }

    private static void showReadyToBorrowNotifications() {
        ArrayList<Book> ready = librarySystem.getReadyToBorrowHolds();
        if (ready == null || ready.isEmpty()) {
            return;
        }
        System.out.println();
        System.out.println("=== Notification: The following held books are now ready for you to borrow ===");
        for (int i = 0; i < ready.size(); i++) {
            Book b = ready.get(i);
            System.out.printf(" %d) %s by %s%n", i, b.getTitle(), b.getAuthor());
        }
        System.out.println("You can borrow them by choosing 'Borrow Book' from the menu");
    }

    private static void userMenuLoop() {
        showReadyToBorrowNotifications();
        while (librarySystem.loggedIn()) {
            System.out.println();
            System.out.println("=== Main Menu ===");
            System.out.println("Logged in:");
            System.out.println(" 1) Borrow Book");
            System.out.println(" 2) Return Book");
            System.out.println(" 3) Logout");
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    borrowBook();
                    break;
                case "2":
                    returnBook();
                    break;
                case "3":
                    userLogout();
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void displayAllBooks() {
        System.out.println();
        System.out.println("=== Library Collection ===");
        for (int i = 0; i < librarySystem.getNumBooks(); i++) {
            Book b = librarySystem.getBook(i);
            String status = b.getStatus().name();
            String due = (b.getDueDate() == null) ? "" : (" Due: " + b.getDueDate());
            System.out.printf(" %2d) %s by %s -- %s%s%n", i, b.getTitle(), b.getAuthor(), status, due);
        }
    }

    private static void borrowBook() {
        System.out.println();
        ArrayList<Book> borrowed = librarySystem.getBorrowedBooks();
        int currentCount = (borrowed == null) ? 0 : borrowed.size();
        System.out.printf("You currently have %d book(s) borrowed.%n", currentCount);

        displayAllBooks();

        System.out.println();
        System.out.print("Enter the index of the book you want to borrow (or 'c' to cancel): ");
        String raw = scanner.nextLine().trim();
        if (raw.equalsIgnoreCase("c")) {
            System.out.println("Cancelled");
            return;
        }
        int index = Integer.parseInt(raw);
        if (index < 0 || index >= librarySystem.getNumBooks()) {
            System.out.println("Index out of range");
            return;
        }

        librarySystem.selectBook(index);
        Book selectedBook = librarySystem.getBook(index);
        System.out.printf("Selected: %s by %s%n", selectedBook.getTitle(), selectedBook.getAuthor());
        System.out.println("Status: " + selectedBook.getStatus() + (selectedBook.getDueDate() == null ? "" : " Due: " + selectedBook.getDueDate()));
        System.out.print("Confirm borrow? (y/n): ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Borrow cancelled");
            return;
        }

        boolean success = librarySystem.borrow();
        if(!success){
            System.out.println("You're not eligible or book is unavailable. Placed a hold on the book");
        }else{
            System.out.println("Successfully borrowed the book");
        }
    }

    private static void returnBook() {
        System.out.println();
        ArrayList<Book> borrowed = librarySystem.getBorrowedBooks();

        if(borrowed.isEmpty()){
            System.out.println("No books to return");
            return;
        }

        System.out.println("Select which book to return:");
        for (int i = 0; i < borrowed.size(); i++) {
            Book b = borrowed.get(i);
            System.out.printf(" %d) %s by %s -- Due: %s%n", i, b.getTitle(), b.getAuthor(), b.getDueDate());
        }
        System.out.print("Enter index (or 'c' to cancel): ");
        String raw = scanner.nextLine().trim();
        if (raw.equalsIgnoreCase("c")) {
            System.out.println("Cancelled");
            return;
        }
        int index = Integer.parseInt(raw);
        if (index < 0 || index >= borrowed.size()) {
            System.out.println("Index out of range");
            return;
        }

        librarySystem.selectBorrowedBook(index);

        librarySystem.returnBook();
        System.out.println("Book returned. To acknowledge, type any character and press enter now");
        scanner.nextLine();
    }

    private static void userLogout() {
        librarySystem.logOut();
        System.out.println("Logged out");
    }
}
