package org.example;

public record Transaction(Borrower borrower, Book book, String dueDate) {}