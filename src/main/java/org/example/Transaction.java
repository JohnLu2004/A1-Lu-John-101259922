package org.example;

public class Transaction {
    private Borrower borrower;
    private Book book;

    public Transaction(Borrower borrower, Book book){
        this.borrower=borrower;
        this.book=book;
    }
    public Borrower getBorrower(){
        return this.borrower;
    }
    public Book getBook(){
        return this.book;
    }
}