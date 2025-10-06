package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
