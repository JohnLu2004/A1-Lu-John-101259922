package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AccTest {
    @Test
    @DisplayName("A-TEST-01: Multi-User Borrow and Return with Availability Validated")
    void secondBorrowSeesBookIsUnavailable() {
        //ARRANGE
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        //ACT - first user logs in, borrows book, logs out, and second user logs in
        // --- UC-01: Login ---
        librarySystem.authenticate("Pogchamp1234!");

        // --- UC-02: Borrow book ---
        librarySystem.getBooks();
        librarySystem.selectBook(1);
        librarySystem.borrow();
        librarySystem.createTransaction();

        // --- UC-04: Logout ---
        librarySystem.logOut();

        // --- UC-01: Login ---
        librarySystem.authenticate("Skibidi5678!");

        //ASSERT - see book is unavailable
        ArrayList<Book> books = librarySystem.getBooks();
        assertEquals(Status.UNAVAILABLE, books.get(1).getStatus());

        // --- UC-04: Logout ---
        librarySystem.logOut();

        //ACT - first user logs in, returns book, logs out, and second user logs in
        // --- UC-01: Login ---
        librarySystem.authenticate("Pogchamp1234!");

        // --- UC-03: Return book ---
        librarySystem.selectBorrowedBook(0);
        librarySystem.returnBook();

        // --- UC-04: Logout ---
        librarySystem.logOut();

        // --- UC-01: Login ---
        librarySystem.authenticate("Skibidi5678!");

        //ASSERT - see book is available
        //see book is available now
        librarySystem.getBooks();
        books = librarySystem.getBooks();
        assertEquals(Status.AVAILABLE, books.get(1).getStatus());

        // --- UC-04: Logout ---
        librarySystem.logOut();
    }

    @Test
    @DisplayName("A-TEST-02: Initialization and Authentication with Error Handling")
    void authenticationErrorHandling() {
        //ARRANGE
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        //ACT - first user logs in, borrows book, logs out, and second user logs in
        // --- UC-01: Login ---
        librarySystem.authenticate("Pogchamp1234!");

        //ASSERT
        assertTrue(librarySystem.loggedIn());

        // --- UC-04: Logout ---
        librarySystem.logOut();

        // --- UC-01: Login ---
        librarySystem.authenticate("Gyatt8765!");

        //ASSERT
        assertFalse(librarySystem.loggedIn());
    }
}
