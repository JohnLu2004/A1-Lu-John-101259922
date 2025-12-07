Cypress.Commands.add("login", (name) => {
  cy.visit("/start.html");
  cy.get("#nameInput").type(name);
  cy.get("#startButton").click();
});
