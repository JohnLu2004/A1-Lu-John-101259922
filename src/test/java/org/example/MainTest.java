package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    @DisplayName("System calculates which on-hold books are available to user")
    void RESP_05_test_01(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        Borrower borrower1 = librarySystem.getBorrower(0);
        Book book1 = librarySystem.getBook(0);
        Book book2 = librarySystem.getBook(1);

        borrower1.placeHold(book1);
        borrower1.placeHold(book2);

        librarySystem.authenticate("Pogchamp1234!");

        ArrayList<Book> availableBooksOnHold = librarySystem.getOnHoldBooksAvailable();

        assertEquals(2, availableBooksOnHold.size());
    }

    @Test
    @DisplayName("Valid user, holds placed, but none of the books available")
    void RESP_05_test_02(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        Borrower borrower1 = librarySystem.getBorrower(0);
        Book book1 = librarySystem.getBook(0);
        Book book2 = librarySystem.getBook(1);
        book1.setStatus("UNAVAILABLE");
        book2.setStatus("UNAVAILABLE");

        borrower1.placeHold(book1);
        borrower1.placeHold(book2);

        librarySystem.authenticate("Pogchamp1234!");

        ArrayList<Book> availableBooksOnHold = librarySystem.getOnHoldBooksAvailable();

        assertEquals(0, availableBooksOnHold.size());
    }

    @Test
    @DisplayName("Valid user, no holds placed")
    void RESP_05_test_03(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        librarySystem.authenticate("Pogchamp1234!");

        ArrayList<Book> availableBooksOnHold = librarySystem.getOnHoldBooksAvailable();

        assertEquals(0, availableBooksOnHold.size());
    }

    @Test
    @DisplayName("Verification of system selection of book")
    void RESP_07_test_01(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        librarySystem.selectBook(0);

        assertTrue(librarySystem.bookSelected());
    }

    @Test
    @DisplayName("Verification of system selection of book out of bounds")
    void RESP_07_test_02(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        librarySystem.selectBook(20);

        assertFalse(librarySystem.bookSelected());
    }

    @Test
    @DisplayName("Verification of system selection of no book")
    void RESP_07_test_03(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        assertFalse(librarySystem.bookSelected());
    }

    @Test
    @DisplayName("System verification of book with no holds not taken")
    void RESP_08_test_01(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        librarySystem.selectBook(0);

        assertEquals("AVAILABLE", librarySystem.bookIsAvailable());
    }

    @Test
    @DisplayName("System verification of book with no holds but taken")
    void RESP_08_test_02(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        Book book = librarySystem.getBook(0);
        book.setStatus("UNAVAILABLE");

        librarySystem.selectBook(0);

        assertEquals("UNAVAILABLE", librarySystem.bookIsAvailable());
    }

    @Test
    @DisplayName("System verification of book with holds but not taken")
    void RESP_08_test_03(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        Borrower borrower2 = librarySystem.getBorrower(1);
        Book book1 = librarySystem.getBook(0);
        borrower2.borrowBook(book1);

        librarySystem.selectBook(0);

        assertEquals("ON_HOLD", librarySystem.bookIsAvailable());
    }

    @Test
    @DisplayName("System verification of book with hold and taken")
    void RESP_08_test_04(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        Borrower borrower2 = librarySystem.getBorrower(1);
        Borrower borrower3 = librarySystem.getBorrower(2);
        Book book1 = librarySystem.getBook(0);
        borrower2.borrowBook(book1);
        borrower3.borrowBook(book1);

        librarySystem.selectBook(0);

        librarySystem.selectBook(0);

        assertEquals("UNAVAILABLE", librarySystem.bookIsAvailable());
    }



    @Test
    @DisplayName("System verification of user eligibility when user has 0s book taken")
    void RESP_09_test_01(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        librarySystem.authenticate("Pogchamp1234!");

        assertTrue(librarySystem.userIsEligible());
    }

    @Test
    @DisplayName("System verification of user eligibility when user has 3 books taken")
    void RESP_09_test_2(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();

        //add 3 books
        Borrower borrower = librarySystem.getBorrower(0);
        Book book1 = librarySystem.getBook(0);
        Book book2 = librarySystem.getBook(1);
        Book book3 = librarySystem.getBook(2);
        borrower.borrowBook(book1);
        borrower.borrowBook(book2);
        borrower.borrowBook(book3);

        librarySystem.authenticate("Pogchamp1234!");

        assertFalse(librarySystem.userIsEligible());
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

    @Test
    @DisplayName("Clear data on user logout")
    void RESP_18_test_01(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();
        librarySystem.authenticate("Pogchamp1234!");


        librarySystem.logOut();
        assertFalse(librarySystem.loggedIn());
    }

    @Test
    @DisplayName("Don't clear data when no user logout")
    void RESP_18_test_02(){
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();
        librarySystem.authenticate("Pogchamp1234!");

        assertTrue(librarySystem.loggedIn());
    }
}
