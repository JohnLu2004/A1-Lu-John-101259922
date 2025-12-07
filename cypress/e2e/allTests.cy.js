describe("A1 Scenario - Borrow & Return Cycle", () => {
  beforeEach(() => {
    cy.request("POST", "http://localhost:3000/api/reset");
    cy.visit("http://localhost:3000/index.html");
  });

  it("should borrow a book, prevent another user from borrowing, then return it", () => {
    // User 1 logs in
    cy.get("#username").type("alice");
    cy.get("#password").type("pass123");
    cy.get("#loginButton").click();

    //Pick the borrow option
    cy.get("#borrowOption").click();

    // Borrow the first book
    cy.get("#bookIndex").type("0");
    cy.get("#borrowButton").click();

    cy.url().should("include", "borrow=success");

    // Log out
    cy.get("#logoutOption").click();

    // User 2 tries to borrow the same book
    cy.get("#username").type("bob");
    cy.get("#password").type("pass456");
    cy.get("#loginButton").click();
    cy.get("#borrowOption").click();

    // Borrow the first book
    cy.get("#bookIndex").type("0");
    cy.get("#borrowButton").click();
    // url should say hold=placed
    cy.url().should("include", "hold=placed");

    cy.get("#logoutOption").click();

    // User 1 returns the book
    cy.get("#username").type("alice");
    cy.get("#password").type("pass123");
    cy.get("#loginButton").click();

    cy.get("#returnOption").click();

    cy.get("#bookIndex").type("0");
    cy.get("#returnButton").click();

    cy.get("#logoutOption").click();

    // Now User 2 can borrow it
    cy.get("#username").type("bob");
    cy.get("#password").type("pass456");
    cy.get("#loginButton").click();

    cy.get("#borrowOption").click();

    // Borrow the first book
    cy.get("#bookIndex").type("0");
    cy.get("#borrowButton").click();

    // url should say borrow=success
    cy.url().should("include", "borrow=success");

    //now return the book
    cy.get("#returnOption").click();

    cy.get("#bookIndex").type("0");
    cy.get("#returnButton").click();

    cy.url().should("include", "return=success");
  });

  it("should allow multiple users to place holds and notify correctly", () => {
    // User 1 borrows Book 0
    cy.get("#username").type("alice");
    cy.get("#password").type("pass123");
    cy.get("#loginButton").click();
    cy.get("#borrowOption").click();
    cy.get("#bookIndex").type("0");
    cy.get("#borrowButton").click();
    cy.url().should("include", "borrow=success");
    cy.get("#logoutOption").click();

    // User 2 places a hold on Book 0
    cy.get("#username").type("bob");
    cy.get("#password").type("pass456");
    cy.get("#loginButton").click();
    cy.get("#borrowOption").click();
    cy.get("#bookIndex").type("0");
    cy.get("#borrowButton").click();
    cy.url().should("include", "hold=placed");
    cy.get("#logoutOption").click();

    // User 3 places a hold on Book 0
    cy.get("#username").type("charlie");
    cy.get("#password").type("pass789!");
    cy.get("#loginButton").click();
    cy.get("#borrowOption").click();
    cy.get("#bookIndex").type("0");
    cy.get("#borrowButton").click();
    cy.url().should("include", "hold=placed");
    cy.get("#logoutOption").click();

    // User 1 returns Book 0
    cy.get("#username").type("alice");
    cy.get("#password").type("pass123");
    cy.get("#loginButton").click();
    cy.get("#returnOption").click();
    cy.get("#bookIndex").type("0");
    cy.get("#returnButton").click();
    cy.get("#logoutOption").click();

    // User 2 should now be able to borrow Book 0
    cy.get("#username").type("bob");
    cy.get("#password").type("pass456");
    cy.get("#loginButton").click();

    cy.get("#borrowOption").click();
    cy.get("#bookIndex").type("0");
    cy.get("#borrowButton").click();
    cy.url().should("include", "borrow=success");
    cy.get("#logoutOption").click();

    // User 3 still cannot borrow, must wait for notification
    cy.get("#username").type("charlie");
    cy.get("#password").type("pass789!");
    cy.get("#loginButton").click();
    cy.get("#borrowOption").click();
    cy.get("#bookIndex").type("0");
    cy.get("#borrowButton").click();
    cy.url().should("include", "hold=placed");
    cy.get("#logoutOption").click();
  });

  it("should enforce borrowing limit and allow holds at the limit", () => {
    // User 1 borrows 3 books
    cy.get("#username").type("alice");
    cy.get("#password").type("pass123");
    cy.get("#loginButton").click();

    for (let i = 0; i < 3; i++) {
      cy.get("#borrowOption").click();
      cy.get("#bookIndex").type(`${i}`);
      cy.get("#borrowButton").click();
      cy.url().should("include", "borrow=success");
    }

    // Try to borrow a 4th book - should fail / place hold
    cy.get("#borrowOption").click();
    cy.get("#bookIndex").type("3");
    cy.get("#borrowButton").click();
    cy.url().should("include", "hold=placed");

    cy.get("#logoutOption").click();

    // User returns one book
    cy.get("#username").type("alice");
    cy.get("#password").type("pass123");
    cy.get("#loginButton").click();
    cy.get("#returnOption").click();
    cy.get("#bookIndex").type("0");
    cy.get("#returnButton").click();
    cy.url().should("include", "return=success");

    cy.get("#logoutOption").click();

    // If this user was next in queue for any held book, they could borrow now
    // (Assuming Book 3 is held for them)
    cy.get("#username").type("alice");
    cy.get("#password").type("pass123");
    cy.get("#loginButton").click();
    cy.get("#borrowOption").click();
    cy.get("#bookIndex").type("3");
    cy.get("#borrowButton").click();
    cy.url().should("include", "borrow=success");

    cy.get("#logoutOption").click();
  });

  it("should handle empty borrowed book list correctly", () => {
    // User logs in without any borrowed books
    cy.get("#username").type("alice");
    cy.get("#password").type("pass123");
    cy.get("#loginButton").click();

    cy.get("#returnOption").click();

    // Enter an invalid index or just check message (depends on frontend)
    cy.get("#bookIndex").type("0");
    cy.get("#returnButton").click();

    // Should redirect to options page with error or message
    cy.url().should("include", "error=invalid");

    cy.visit("http://localhost:3000/options.html");
    cy.get("#logoutOption").click();

    // All books are available
    cy.get("#username").type("bob");
    cy.get("#password").type("pass456");
    cy.get("#loginButton").click();

    cy.get("#borrowOption").click();
    cy.get("#bookIndex").type("0");
    cy.get("#borrowButton").click();
    cy.url().should("include", "borrow=success");
  });
});
