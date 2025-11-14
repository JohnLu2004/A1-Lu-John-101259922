import org.example.*;
import io.cucumber.java.en.*;

import static org.junit.Assert.*;

public class UsageSteps {
    private LibrarySystem librarySystem;

    @Given("library is initialized")
    public void the_library_is_initialized() {
        librarySystem = new LibrarySystem();
        librarySystem.initializeLibrary();
    }

    @When("{string} logs in")
    public void user_logs_in(String name) {
        switch (name) {
            case "alice" -> librarySystem.authenticate("pass123");
            case "bob" -> librarySystem.authenticate("pass456");
            case "charlie" -> librarySystem.authenticate("pass789");
        }
    }

    @When("{string} selects {string} from the catalogue")
    public void current_user_selects_bool_from_the_catalogue(String user, String title) {
        for (int i = 0; i < librarySystem.getBooks().size(); i++) {
            if (librarySystem.getBook(i).getTitle().equals(title)) {
                librarySystem.selectBook(i);
            }
        }
    }

    @When("{string} selects first returnable book")
    public void current_user_selects_first_returnable_book(String user) {
        librarySystem.selectBorrowedBook(0);
    }

    @When("{string} borrows the selected book")
    public void current_user_borrows_the_selected_book(String user) {
        librarySystem.borrow();
    }

    @When("{string} returns the selected book")
    public void current_user_returns_selected_book(String user) {
        librarySystem.returnBook();
    }

    @When("{string} logs out")
    public void current_user_logs_out(String user) {
        librarySystem.logOut();
    }

    @Then("{string} is available")
    public void book_is_available(String title) {
        Book book = librarySystem.getBook(0);
        for (int i = 0; i < librarySystem.getBooks().size(); i++) {
            if (librarySystem.getBook(i).getTitle().equals(title))
                book = librarySystem.getBook(i);
        }
        assertEquals(Status.AVAILABLE, book.getStatus());
    }

    @Then("{string} is unavailable")
    public void book_is_unavailable(String title) {
        Book book = librarySystem.getBook(0);
        for (int i = 0; i < librarySystem.getBooks().size(); i++) {
            if (librarySystem.getBook(i).getTitle().equals(title))
                book = librarySystem.getBook(i);
        }
        assertEquals(Status.UNAVAILABLE, book.getStatus());
    }

    @Then("{string} placed hold on {string}")
    public void current_user_placed_hold_on_book(String user, String title) {
        Book book = librarySystem.getBook(0);
        for (int i = 0; i < librarySystem.getBooks().size(); i++) {
            if (librarySystem.getBook(i).getTitle().equals(title))
                book = librarySystem.getBook(i);
        }
        assertEquals(1, book.getNumHolds());
        assertEquals(1, librarySystem.getBooksOnHold().size());
    }

    @Then("{string} is first properly in {string} queue")
    public void book_holds_queue_properly(String user, String title) {
        Book book = librarySystem.getBook(0);
        for (int i = 0; i < librarySystem.getBooks().size(); i++) {
            if (librarySystem.getBook(i).getTitle().equals(title))
                book = librarySystem.getBook(i);
        }
        Borrower firstBorrowerInQueue = book.nextQueuedUser();
        assertEquals(user, firstBorrowerInQueue.getName());
    }

    @Then("{string} has the {string} in the catalogue")
    public void current_user_has_book_in_the_catalogue(String user, String title) {
        Book book = librarySystem.getBook(0);
        for (int i = 0; i < librarySystem.getBooks().size(); i++) {
            if (librarySystem.getBook(i).getTitle().equals(title))
                book = librarySystem.getBook(i);
        }
        assertTrue(librarySystem.getBorrowedBooks().contains(book));
    }

    @Then("{string} has no books to return")
    public void no_books_to_return(String user) {
        assertTrue(librarySystem.getBorrowedBooks().isEmpty());
    }

    @Then("every book is available")
    public void all_books_are_available() {
        for (int i = 0; i < librarySystem.getNumBooks(); i++) {
            librarySystem.selectBook(i);
            assertEquals(Status.AVAILABLE, librarySystem.bookIsAvailable());
        }
    }

    @Then("{string} cannot borrow the selected book")
    public void current_user_cannot_borrow_the_selected_book(String user) {
        int numBorrowedBooksBeforeAttemptAtBorrowing = librarySystem.getBorrowedBooks().size();
        librarySystem.borrow();
        int numBorrowedBooksAfterAttemptAtBorrowing = librarySystem.getBorrowedBooks().size();

        assertEquals(3, numBorrowedBooksBeforeAttemptAtBorrowing);
        assertEquals(numBorrowedBooksBeforeAttemptAtBorrowing, numBorrowedBooksAfterAttemptAtBorrowing);
    }

    @Then("{string} should have one book on hold")
    public void current_user_should_have_one_book_on_hold(String user) {
        int numBooksOnHold = librarySystem.getBooksOnHold().size();
        assertEquals(1, numBooksOnHold);
    }

    @Then("{string} drops below the limit")
    public void current_user_drops_below_the_limit(String user) {
        assertTrue(librarySystem.userIsEligible());
    }

    @Then("system shows book that was on hold available")
    public void system_shows_book_that_was_on_hold_available() {
        assertEquals(1, librarySystem.getReadyToBorrowHolds().size());
    }

    @Then("book has not been selected")
    public void no_book_has_been_selected() {
        assertFalse(librarySystem.bookSelected());
    }
}
