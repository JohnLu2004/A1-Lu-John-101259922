package org.example;

import java.sql.Array;
import java.util.ArrayList;

public class LibrarySystem {
    ArrayList<Book> books = new ArrayList<Book>();

    public void initializeLibrary() {
        Book newBook = new Book("Don Quixote", "Miguel de Cervantes");
        books.add(newBook);
        newBook = new Book("The Great Gatsby", "F. Scott Fitzgerald");
        books.add(newBook);
        newBook = new Book("The Catcher in the Rye", "J. D. Salinger");
        books.add(newBook);
        newBook = new Book("Moby Dick", "Herman Melville");
        books.add(newBook);
        newBook = new Book("Pride and Prejudice", "Jane Austen");
        books.add(newBook);
        newBook = new Book("Dune", "Frank Herbert");
        books.add(newBook);
        newBook = new Book("The Lord of the Rings", "J. R. R. Tolkien");
        books.add(newBook);
        newBook = new Book("To Kill a Mockingbird", "Harper Lee");
        books.add(newBook);
        newBook = new Book("The Trial", "Franz Kafka");
        books.add(newBook);
        newBook = new Book("Journey to the West", "Wu Cheng En");
        books.add(newBook);
        newBook = new Book("Adventures of Huckleberry Finn", "Mark Twain");
        books.add(newBook);
        newBook = new Book("The Odyssey", "Homer");
        books.add(newBook);
        newBook = new Book("1984", "George Orwell");
        books.add(newBook);
        newBook = new Book("The Divine Comedy", "Dante Alighieri");
        books.add(newBook);
        newBook = new Book("The Little Prince", "Antoine de Saint-Exupéry");
        books.add(newBook);
        newBook = new Book("The Alchemist", "Paulo Coelho");
        books.add(newBook);
        newBook = new Book("Frankenstein", "Mary Shelley");
        books.add(newBook);
        newBook = new Book("Les Misérables", "Victor Hugo");
        books.add(newBook);
        newBook = new Book("Dracula", "Bram Stoker");
        books.add(newBook);
        newBook = new Book("A Christmas Carol", "Charles Dickens");
        books.add(newBook);
    }

    public Book getBook(int index){
        return books.get(index);
    }

    public int getNumBooks() {
        return books.size();
    }

    public int getNumBorrowers(){
        return 0;
    }
}
