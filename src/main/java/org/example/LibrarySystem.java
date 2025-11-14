package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LibrarySystem {
    private final ArrayList<Book> books = new ArrayList<Book>();
    private final ArrayList<Borrower> borrowers = new ArrayList<Borrower>();
    private Borrower currentUser = null;
    private Book currentBook = null;
    private final ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    public void initializeBooks() {
        books.addAll(List.of(
                new Book("The Great Gatsby", "F. Scott Fitzgerald"),
                new Book("To Kill a Mockingbird", "Harper Lee"),
                new Book("Pride and Prejudice", "Jane Austen"),
                new Book("The Hobbit", "J. R. R. Tolkien"),
                new Book("Harry Potter", "J. K. Rowling"),
                new Book("The Catcher in the Rye", "J. D. Salinger"),
                new Book("Animal Farm", "George Orwell"),
                new Book("Lord of the Flies", "William Golding"),
                new Book("Jane Eyre", "Charlotte Bronte"),
                new Book("Wuthering Heights", "Emily Bronte"),
                new Book("Moby Dick", "Herman Melville"),
                new Book("The Odyssey", "Homer"),
                new Book("Hamlet", "William Shakespeare"),
                new Book("War and Peace", "Leo Tolstoy"),
                new Book("The Divine Comedy", "Dante Alighieri"),
                new Book("Crime and Punishment", "Fyodor Dostoevsky"),
                new Book("Don Quixote", "Miguel de Cervantes"),
                new Book("The Iliad", "Homer"),
                new Book("Ulysses", "James Royce")
        ));
    }

    public void initializeBorrowers() {
        borrowers.addAll(List.of(
                new Borrower("alice", "pass123"),
                new Borrower("bob", "pass456"),
                new Borrower("charlie", "pass789")
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
        else if (currentBook.getStatus() == Status.ON_HOLD && !currentBook.hasNoHolds()
                && currentBook.nextQueuedUser() == currentUser) {
            currentBook.removeNextQueuedUser();
            return Status.AVAILABLE;
        } else if (!currentBook.hasNoHolds())
            return Status.ON_HOLD;
        return currentBook.getStatus();
    }

    public boolean borrow() {
        if (currentBook == null)
            return false;
        // If book is available or they're next in the queue
        if (currentUser.isEligible() && this.bookIsAvailable() == Status.AVAILABLE) {
            currentBook.signOutBy(currentUser, createDueDate());
            currentUser.borrowBook(currentBook);
            return true;
        }else if(currentUser.getBorrowedBooks().contains(currentBook)){
            return false;
        } else {
            currentUser.placeHold(currentBook);
            currentBook.placeHold(currentUser);
            return false;
        }
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

    public ArrayList<Book> getBooks() {
        return books;
    }

    public HashSet<Book> getBooksOnHold() {
        return currentUser.getBooksOnHold();
    }
}