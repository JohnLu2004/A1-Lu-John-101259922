package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    @DisplayName("Initial Size of the library catalogue is 20")
    void RESP_01_test_01(){
        LibrarySystem librarySystem = new LibrarySystem();

        librarySystem.initializeLibrary();

        int numBooks = librarySystem.getNumBooks();

        assertEquals(20, numBooks);
    }

    @Test
    @DisplayName("Test the contents of the library")
    void RESP_01_test_02(){
        LibrarySystem librarySystem = new LibrarySystem();

        librarySystem.initializeLibrary();

        Book firstBook = librarySystem.getBook(0);
        assertEquals("Don Quixote", firstBook.title);
        assertEquals("Miguel de Cervantes", firstBook.author);

        Book tenthBook = librarySystem.getBook(9);
        assertEquals("Journey to the West", tenthBook.title);
        assertEquals("Wu Cheng En", tenthBook.author);

        Book twentiethBook = librarySystem.getBook(19);
        assertEquals("A Christmas Carol", twentiethBook.title);
        assertEquals("Charles Dickens", twentiethBook.author);
    }

    @Test
    @DisplayName("Initial amount borrowers is 5")
    void RESP_02_test_01(){
        LibrarySystem librarySystem = new LibrarySystem();

        librarySystem.initializeLibrary();

        int numBorrowers = librarySystem.getNumBorrowers();

        assertEquals(5, numBorrowers);
    }

    @Test
    @DisplayName("Borrowers have info")
    void RESP_02_test_02(){
        LibrarySystem librarySystem = new LibrarySystem();

        librarySystem.initializeLibrary();

        Borrower firstBorrower = librarySystem.getBorrower(0);
        assertEquals(firstBorrower.name, "Stephen King");

        Borrower thirdBorrower = librarySystem.getBorrower(2);
        assertEquals(thirdBorrower.name, "William Shakespeare");

        Borrower fifthBorrower = librarySystem.getBorrower(4);
        assertEquals(fifthBorrower.name, "Charles Dickens");
    }

    @Test
    @DisplayName("Authenticate valid user")
    void RESP_03_test_01(){
        LibrarySystem librarySystem = new LibrarySystem();

        librarySystem.initializeLibrary();

        assertTrue(librarySystem.authenticate("Pogchamp1234!"));
    }

    @Test
    @DisplayName("Authenticate invalid user")
    void RESP_03_test_02(){
        LibrarySystem librarySystem = new LibrarySystem();

        librarySystem.initializeLibrary();

        assertFalse(librarySystem.authenticate("$UncoolDude$"));
    }

    @Test
    @DisplayName("Check if authenticated user was set as current user")
    void RESP_04_test_01(){
        LibrarySystem librarySystem = new LibrarySystem();

        librarySystem.initializeLibrary();

        librarySystem.authenticate("Pogchamp1234!");

        assertTrue(librarySystem.loggedIn());
    }

    @Test
    @DisplayName("Check if unauthenticated user was set as current user")
    void RESP_04_test_02(){
        LibrarySystem librarySystem = new LibrarySystem();

        librarySystem.initializeLibrary();

        librarySystem.authenticate("Poggers1234!");

        assertFalse(librarySystem.loggedIn());
    }

    @Test
    @DisplayName("Check if user not logged in is logged in")
    void RESP_04_test_03(){
        LibrarySystem librarySystem = new LibrarySystem();

        librarySystem.initializeLibrary();

        assertFalse(librarySystem.loggedIn());
    }

    @Test
    @DisplayName("System creates a due date that is 14 days later")
    void RESP_10_test_01(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        String dueDate = librarySystem.createDueDate();

        LocalDate expectedDueDate = LocalDate.now().plusDays(14);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String expectedFormattedDate = expectedDueDate.format(formatter);

        assertEquals(expectedFormattedDate, dueDate);
    }

    @Test
    @DisplayName("System creates a due date that is not before 14 days later")
    void RESP_10_test_02(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        String dueDate = librarySystem.createDueDate();

        LocalDate expectedDueDate = LocalDate.now().plusDays(14);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate actualDueDate = LocalDate.parse(dueDate, formatter);

        assertFalse(actualDueDate.isBefore(expectedDueDate));
    }

    @Test
    @DisplayName("System creates a due date that is not after 14 days later")
    void RESP_10_test_03(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        String dueDate = librarySystem.createDueDate();

        LocalDate expectedDueDate = LocalDate.now().plusDays(14);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate actualDueDate = LocalDate.parse(dueDate, formatter);

        assertFalse(actualDueDate.isAfter(expectedDueDate));
    }

    @Test
    @DisplayName("User place hold on existing book")
    void RESP_17_test_01(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        Borrower borrower1 = librarySystem.getBorrower(0);
        Book book1 = librarySystem.getBook(0);

        borrower1.placeHold(book1);

        assertEquals(1, borrower1.getNumHolds());
        assertEquals(1, book1.getNumHolds());
    }

    @Test
    @DisplayName("User place hold on non-existing book")
    void RESP_17_test_02(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        Borrower borrower1 = librarySystem.getBorrower(0);
        Book book1 = null;

        borrower1.placeHold(book1);

        assertEquals(0, borrower1.getNumHolds());
    }
}
