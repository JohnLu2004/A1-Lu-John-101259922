package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Borrower {
    public String name;
    public String password;
    public ArrayDeque<Book> queue = new ArrayDeque<Book>();
    public ArrayList<Book> borrowedBooks = new ArrayList<Book>();


    public Borrower(String name, String password){
        this.name = name;
        this.password = password;
    }

    public void placeHold(Book book){
        if(book!=null) {
            book.placeHold(this);
            queue.add(book);
        }
    }

    public int getNumHolds(){return queue.size();}

    public void borrowBook(Book book) {
        queue.add(book);
    }

    public boolean borrowed(Book book){
        for(Book borrowedBook: borrowedBooks){
            if(borrowedBook == book){
                return true;
            }
        }
        return false;
    }
}