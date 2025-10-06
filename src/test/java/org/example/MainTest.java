package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals("Wu Cheng En", firstBook.author);

        Book twentiethBook = librarySystem.getBook(19);
        assertEquals("A Christmas Carol", twentiethBook.title);
        assertEquals("Charles Dickens", firstBook.author);
    }
}
