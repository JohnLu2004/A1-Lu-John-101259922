package org.example;

public class Book {
    public String title;
    public String author;
    public String status;

    public Book(String title, String author){
        this.title = title;
        this.author = author;
        this.status = "AVAILABLE";
    }

    public void placeHold(Borrower borrower){}

    public int getNumHolds(){return 0;}
}
