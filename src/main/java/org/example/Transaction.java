package org.example;

public record Transaction(Borrower borrower, Book book, String dueDate) {
    public boolean equals(Object obj) {
        return false;
    }
}