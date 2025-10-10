package org.example;

import java.util.ArrayDeque;

public class Book {
    public String title;
    public String author;
    public String status;
    public String dueDate;
    public ArrayDeque<Borrower> queue = new ArrayDeque<Borrower>();
    public Borrower borrower = null;


    public Book(String title, String author){
        this.title = title;
        this.author = author;
        this.status = "AVAILABLE";
    }

    public void setStatus(String status){
        this.status=status;
    }

    public void placeHold(Borrower borrower){
        queue.add(borrower);
    }

    public int getNumHolds(){return queue.size();}

    public boolean borrowedBy(Borrower borrower){
        return this.borrower == borrower;
    }
}