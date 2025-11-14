Feature: Use Library
  Background:
    Given library is initialized
  Scenario Outline: User borrow/availability behaviour AND Returned book is available
    When <user1> logs in
    And <user1> selects <book> from the catalogue
    And <user1> borrows the selected book
    And <user1> logs out
    And <user2> logs in
    And <user2> selects <book> from the catalogue
    Then <book> is unavailable
    When <user2> logs out
    And <user1> logs in
    And <user1> selects first returnable book
    And <user1> returns the selected book
    And <user1> logs out
    And <user2> logs in
    And <user2> selects <book> from the catalogue
    Then <book> is available
    Examples:
      | user1   | user2     | book               |
      | "alice" | "bob"     | "The Great Gatsby" |
      | "bob"   | "charlie" | "Harry Potter"     |
      | "charlie"|"alice"   | "The Divine Comedy"|
  Scenario Outline: Users can place holds on unavailable books AND Hold queue follows FIFO ordering
    When <user1> logs in
    And <user1> selects <book> from the catalogue
    And <user1> borrows the selected book
    And <user1> logs out
    And <user2> logs in
    And <user2> selects <book> from the catalogue
    And <user2> borrows the selected book
    Then <user2> placed hold on <book>
    When <user2> logs out
    And <user3> logs in
    And <user3> selects <book> from the catalogue
    And <user3> borrows the selected book
    And <user3> logs out
    Then <user2> is first properly in <book> queue
    When <user1> logs in
    And <user1> selects first returnable book
    And <user1> returns the selected book
    And <user1> logs out
    And <user2> logs in
    Then system shows book that was on hold available
    When <user2> selects <book> from the catalogue
    And <user2> borrows the selected book
    Then <user2> has the <book> in the catalogue
    When <user2> selects first returnable book
    And <user2> returns the selected book
    And <user2> logs out
    And <user3> logs in
    And <user3> selects <book> from the catalogue
    And <user3> borrows the selected book
    Then <user3> has the <book> in the catalogue
    Examples:
      | user1     | user2 | user3     | book                |
      | "alice"   | "bob" | "charlie" | "The Great Gatsby"  |
      | "charlie" | "bob" | "alice"   | "Wuthering Heights" |
      | "bob"     |"alice"| "charlie" | "Ulysses"           |
  Scenario Outline: Users cannot exceed the 3-book borrowing limit AND Users can place holds even when at the borrowing limit (i.e., when they already have 3
  books borrowed) AND When a user at the borrowing limit returns a book, they drop below the limit
    When <user1> logs in
    And <user1> selects <book1> from the catalogue
    And <user1> borrows the selected book
    And <user1> selects <book2> from the catalogue
    And <user1> borrows the selected book
    And <user1> selects <book3> from the catalogue
    And <user1> borrows the selected book
    And <user1> selects <book4> from the catalogue
    Then <user1> cannot borrow the selected book
    When <user1> borrows the selected book
    Then <user1> should have one book on hold
    When <user1> selects first returnable book
    And <user1> returns the selected book
    Then <user1> drops below the limit
    When <user1> selects <book4> from the catalogue
    Then system shows book that was on hold available
    Examples:
      | user1    | book1              | book2                   | book3                 | book4               |
      | "alice"  | "The Great Gatsby" | "To Kill a Mockingbird" | "Pride and Prejudice" | "Harry Potter"      |
      | "bob"    | "Don Quixote"      | "War and Peace"         | "The Iliad"           | "Animal Farm"       |
      |"charlie" | "Hamlet"           | "Crime and Punishment"  | "The Hobbit"          | "Lord of the Flies" |
  Scenario Outline: System correctly handles return operations with an empty borrowed list AND All books show as available when none are borrowed
    When <user1> logs in
    Then <user1> has no books to return
    When <user1> selects <book> from the catalogue
    And <user1> borrows the selected book
    And <user1> selects first returnable book
    And <user1> returns the selected book
    Then every book is available
    When <user1> selects first returnable book
    Then book has not been selected
    Examples:
      | user1     | book               |
      | "alice"   | "The Great Gatsby" |
      | "bob"     | "Moby Dick"        |
      | "charlie" | "Jane Eyre"        |