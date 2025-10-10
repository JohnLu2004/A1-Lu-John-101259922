package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

public class Borrower {
    private final String name;
    private final String password;
    private final HashSet<Book> booksPlacedOnHold = new HashSet<Book>();
    private final ArrayList<Book> borrowedBooks = new ArrayList<Book>();

    public Borrower(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPassword(String password) {
        return this.password.equals(password);
    }

    public void placeHold(Book book) {
        if (book != null && !booksPlacedOnHold.contains(book)) {
            book.placeHold(this);
            booksPlacedOnHold.add(book);
        }
    }

    public int getNumHolds() {
        return booksPlacedOnHold.size();
    }

    public boolean hasBorrowed(Book book) {
        return borrowedBooks.contains(book);
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public Book getBorrowedBook(int index) {
        return borrowedBooks.get(index);
    }

    public void returnBook(Book book) {
        this.borrowedBooks.remove(book);
    }

    public ArrayList<Book> getReadyToBorrowHolds() {
        ArrayList<Book> onHoldBooksAvailable = new ArrayList<Book>();
        for (Book book : booksPlacedOnHold) {
            if (book.nextQueuedUser() == this && book.getStatus() != Status.UNAVAILABLE) {
                onHoldBooksAvailable.add(book);
            }
        }
        return onHoldBooksAvailable;
    }

    public boolean isEligible() {
        return this.borrowedBooks.size() <= 2;
    }

    public int getNumBorrowedBooks() {
        return this.borrowedBooks.size();
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public HashSet<Book> getBooksOnHold(){
        return booksPlacedOnHold;
    }
}