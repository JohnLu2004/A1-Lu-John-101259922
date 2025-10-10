package org.example;

import java.util.ArrayDeque;

public class Book {
    private final String title;
    private final String author;
    private Status status;
    private String dueDate;
    private final ArrayDeque<Borrower> queue = new ArrayDeque<Borrower>();
    private Borrower borrower = null;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.status = Status.AVAILABLE;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void placeHold(Borrower borrower) {
        queue.add(borrower);
    }

    public int getNumHolds() {
        return queue.size();
    }

    public boolean borrowedBy(Borrower borrower) {
        return this.borrower == borrower;
    }

    public void signOutBy(Borrower borrower, String dueDate) {
        this.dueDate = dueDate;
        this.borrower = borrower;
        this.status = Status.UNAVAILABLE;
    }

    public void returned() {
        if (!this.queue.isEmpty()) {
            this.status = Status.ON_HOLD;
        } else {
            this.status = Status.AVAILABLE;
        }
        this.borrower = null;
    }

    public boolean hasNoHolds() {
        return this.queue.isEmpty();
    }

    public Borrower nextQueuedUser() {
        return this.queue.getFirst();
    }
}