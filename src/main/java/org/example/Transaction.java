package org.example;

public record Transaction(Borrower borrower, Book book, String dueDate) {
    public boolean equals(Transaction transaction) {
        return this.borrower == transaction.borrower && this.book == transaction.book
                && this.dueDate.equals(transaction.dueDate);
    }
}