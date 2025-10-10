package org.example;

public class Borrower {
    public String name;
    public String password;

    public Borrower(String name, String password){
        this.name = name;
        this.password = password;
    }

    public void placeHold(Book book){}

    public int getNumHolds(){return 0;}
}