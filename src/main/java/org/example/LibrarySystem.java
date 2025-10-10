package org.example;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class LibrarySystem {
    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<Borrower> borrowers = new ArrayList<Borrower>();
    Borrower currentUser = null;

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


        Borrower newBorrower = new Borrower("Stephen King", "Pogchamp1234!");
        borrowers.add(newBorrower);
        newBorrower = new Borrower("Charles Darwin", "Skibidi5678!");
        borrowers.add(newBorrower);
        newBorrower = new Borrower("William Shakespeare", "Goon4321!");
        borrowers.add(newBorrower);
        newBorrower = new Borrower("Julius Caesar", "Brainrot8765!");
        borrowers.add(newBorrower);
        newBorrower = new Borrower("Charles Dickens", "Rizz1010!");
        borrowers.add(newBorrower);
    }

    public Book getBook(int index){
        return books.get(index);
    }

    public int getNumBooks() {
        return books.size();
    }

    public int getNumBorrowers(){
        return borrowers.size();
    }

    public Borrower getBorrower(int index){
        return borrowers.get(index);
    }

    public boolean authenticate(String password){
        for(Borrower borrower: borrowers){
            if(borrower.password.equals(password)){
                currentUser = borrower;
                return true;
            }
        }
        return false;
    }

    public boolean loggedIn(){
        return (currentUser != null);
    }

    public String bookIsAvailable(){
        return "UNAVAILABLE";
    }

    public String createDueDate(){
        LocalDate dueDate = LocalDate.now().plusDays(14);
        return dueDate.getYear()+"-"+dueDate.getMonthValue()+"-"+dueDate.getDayOfMonth();
    }

    public void logOut(){
        currentUser=null;
    }

    public ArrayList<Book> getOnHoldBooksAvailable(){
        ArrayList<Book> onHoldBooksAvailable = new ArrayList<Book>();
        for(Book book: currentUser.queue){
            if(book.queue.getFirst()==currentUser && !book.status.equals("UNAVAILABLE")){
                onHoldBooksAvailable.add(book);
            }
        }
        return onHoldBooksAvailable;
    }

    public boolean userIsEligible(){
        return currentUser.queue.size()<3;
    }
}