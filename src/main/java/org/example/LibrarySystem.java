package org.example;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibrarySystem {
    private final ArrayList<Book> books = new ArrayList<Book>();
    private final ArrayList<Borrower> borrowers = new ArrayList<Borrower>();
    private Borrower currentUser = null;
    private Book currentBook = null;
    private final ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    public void initializeBooks() {
        books.addAll(List.of(
                new Book("Don Quixote", "Miguel de Cervantes"),
                new Book("The Great Gatsby", "F. Scott Fitzgerald"),
                new Book("The Catcher in the Rye", "J. D. Salinger"),
                new Book("Moby Dick", "Herman Melville"),
                new Book("Pride and Prejudice", "Jane Austen"),
                new Book("Dune", "Frank Herbert"),
                new Book("The Lord of the Rings", "J. R. R. Tolkien"),
                new Book("To Kill a Mockingbird", "Harper Lee"),
                new Book("The Trial", "Franz Kafka"),
                new Book("Journey to the West", "Wu Cheng En"),
                new Book("Adventures of Huckleberry Finn", "Mark Twain"),
                new Book("The Odyssey", "Homer"),
                new Book("1984", "George Orwell"),
                new Book("The Divine Comedy", "Dante Alighieri"),
                new Book("The Little Prince", "Antoine de Saint-Exupéry"),
                new Book("The Alchemist", "Paulo Coelho"),
                new Book("Frankenstein", "Mary Shelley"),
                new Book("Les Misérables", "Victor Hugo"),
                new Book("Dracula", "Bram Stoker"),
                new Book("A Christmas Carol", "Charles Dickens")));
    }

    public void initializeBorrowers() {
        borrowers.addAll(List.of(
                new Borrower("Stephen King", "Pogchamp1234!"),
                new Borrower("Charles Darwin", "Skibidi5678!"),
                new Borrower("William Shakespeare", "Goon4321!"),
                new Borrower("Julius Caesar", "Brainrot8765!"),
                new Borrower("Charles Dickens", "Rizz1010!")

        ));
    }

    public void initializeLibrary() {
        this.initializeBooks();
        this.initializeBorrowers();
    }

    public Book getBook(int index) {
        return books.get(index);
    }

    public int getNumBooks() {
        return books.size();
    }

    public int getNumBorrowers() {
        return borrowers.size();
    }

    public Borrower getBorrower(int index) {
        return borrowers.get(index);
    }

    public boolean authenticate(String password) {
        for (Borrower borrower : borrowers) {
            if (borrower.isPassword(password)) {
                currentUser = borrower;
                return true;
            }
        }
        return false;
    }

    public boolean loggedIn() {
        return (currentUser != null);
    }

    public String createDueDate() {
        LocalDate dueDate = LocalDate.now().plusDays(14);
        return dueDate.getYear() + "-" + dueDate.getMonthValue() + "-" + dueDate.getDayOfMonth();
    }

    public void logOut() {
        currentUser = null;
    }

    public ArrayList<Book> getReadyToBorrowHolds() {
        return currentUser.getReadyToBorrowHolds();
    }

    public boolean userIsEligible() {
        return currentUser.isEligible();
    }

    public void selectBook(int index) {
        if (index >= 0 && index < 20)
            currentBook = getBook(index);
    }

    public boolean bookSelected() {
        return currentBook != null;
    }

    public Status bookIsAvailable() {
        if (currentBook.getStatus() == Status.UNAVAILABLE)
            return Status.UNAVAILABLE;
        else if (currentBook.getStatus()==Status.ON_HOLD && !currentBook.hasNoHolds() && currentBook.nextQueuedUser() == currentUser) {
            currentBook.removeNextQueuedUser();
            return Status.AVAILABLE;
        } else if (!currentBook.hasNoHolds())
            return Status.ON_HOLD;
        return currentBook.getStatus();
    }

    public boolean borrow() {
        if (currentBook == null)
            return false;
        //If book is available or they're next in the queue
        if(currentUser.isEligible() && this.bookIsAvailable()==Status.AVAILABLE) {
            currentBook.signOutBy(currentUser, createDueDate());
            currentUser.borrowBook(currentBook);
        }else{
            currentUser.placeHold(currentBook);
            currentBook.placeHold(currentUser);
        }
        return true;
    }

    public void createTransaction() {
        if (currentUser != null && currentBook != null)
            transactions.add(new Transaction(currentUser, currentBook, createDueDate()));
    }

    public Transaction getLastTransaction() {
        if (transactions.isEmpty())
            return null;
        return transactions.getLast();
    }

    public void selectBorrowedBook(int index) {
        currentBook = null;
        if (index >= 0 && index < currentUser.getNumBorrowedBooks())
            currentBook = currentUser.getBorrowedBook(index);
        ;
    }

    public void returnBook() {
        if (currentBook == null)
            return;
        currentBook.returned();
        currentUser.returnBook(currentBook);
    }

    public Transaction getConfirmation() {
        return transactions.getLast();
    }

    public ArrayList<Book> getBorrowedBooks() {
        return currentUser.getBorrowedBooks();
    }
}