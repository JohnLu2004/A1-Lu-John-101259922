const express = require("express");
const path = require("path");

const app = express();
app.use(express.urlencoded({ extended: true }));
app.use(express.static("public"));

/* ---------------------------
   GLOBAL CURRENT USER (NO SESSIONS)
   --------------------------- */
let currentUser = null;

/* ---------------------------
   CLASSES
   --------------------------- */
class Book {
  constructor(title, author) {
    this.title = title;
    this.author = author;
    this.status = "AVAILABLE";
    this.dueDate = null;
    this.queue = [];
    this.holders = new Set();
    this.borrower = null;
  }

  placeHold(user) {
    if (!this.holders.has(user)) {
      this.queue.push(user);
      this.holders.add(user);
    }
    if (this.status === "AVAILABLE") {
      this.status = "ON_HOLD";
    }
  }

  signOutBy(user, dueDate) {
    this.dueDate = dueDate;
    this.borrower = user;
    this.status = "UNAVAILABLE";
  }

  returned() {
    if (this.queue.length > 0) this.status = "ON_HOLD";
    else {
      this.status = "AVAILABLE";
      this.dueDate = null;
    }
    this.borrower = null;
  }
}

class Borrower {
  constructor(name, password) {
    this.name = name;
    this.password = password;
    this.borrowed = [];
    this.holds = new Set();
  }

  isPassword(pw) {
    return pw === this.password;
  }
}

/* ---------------------------
   DATA
   --------------------------- */

const initialBorrowersData = [
  { name: "alice", password: "pass123" },
  { name: "bob", password: "pass456" },
  { name: "charlie", password: "pass789!" },
];

const initialBooksData = [
  { title: "The Great Gatsby", author: "F. Scott Fitzgerald" },
  { title: "To Kill a Mockingbird", author: "Harper Lee" },
  { title: "1984", author: "George Orwell" },
  { title: "Pride and Prejudice", author: "Jane Austen" },
  { title: "The Hobbit", author: "J.R.R. Tolkien" },
  { title: "Harry Potter", author: "J.K. Rowling" },
  { title: "The Catcher in the Rye", author: "J. D. Salinger" },
  { title: "Animal Farm", author: "George Orwell" },
  { title: "Lord of the Flies", author: "William Golding" },
  { title: "Jane Eyre", author: "Charlotte Brontë" },
  { title: "Wuthering Heights", author: "Emily Brontë" },
  { title: "Moby Dick", author: "Herman Melville" },
  { title: "The Odyssey", author: "Homer" },
  { title: "Hamlet", author: "charlie" },
  { title: "War and Peace", author: "Leo Tolstoy" },
  { title: "The Divine Comedy", author: "Dante Alighieri" },
  { title: "Crime and Punishment", author: "Fyodor Dostoevsky" },
  { title: "Don Quixote", author: "Miguel de Cervantes" },
  { title: "The Illiad", author: "Homer" },
  { title: "Ulysses", author: "James Joyce" },
];

const borrowers = [
  new Borrower("alice", "pass123"),
  new Borrower("bob", "pass456"),
  new Borrower("charlie", "pass789!"),
];

const books = [
  new Book("The Great Gatsby", "F. Scott Fitzgerald"),
  new Book("To Kill a Mockingbird", "Harper Lee"),
  new Book("1984", "George Orwell"),
  new Book("Pride and Prejudice", "Jane Austen"),
  new Book("The Hobbit", "J.R.R. Tolkien"),
  new Book("Harry Potter", "J.K. Rowling"),
  new Book("The Catcher in the Rye", "J. D. Salinger"),
  new Book("Animal Farm", "George Orwell"),
  new Book("Lord of the Flies", "William Golding"),
  new Book("Jane Eyre", "Charlotte Brontë"),
  new Book("Wuthering Heights", "Emily Brontë"),
  new Book("Moby Dick", "Herman Melville"),
  new Book("The Odyssey", "Homer"),
  new Book("Hamlet", "charlie"),
  new Book("War and Peace", "Leo Tolstoy"),
  new Book("The Divine Comedy", "Dante Alighieri"),
  new Book("Crime and Punishment", "Fyodor Dostoevsky"),
  new Book("Don Quixote", "Miguel de Cervantes"),
  new Book("The Illiad", "Homer"),
  new Book("Ulysses", "James Joyce"),
];

function dueDate() {
  const now = new Date();
  now.setDate(now.getDate() + 14);
  return now.toISOString().split("T")[0];
}

/* ---------------------------
   LOGIN MIDDLEWARE SUBSTITUTE
   --------------------------- */
function requireLogin(req, res, next) {
  if (!currentUser) return res.redirect("/index.html");
  next();
}

/* ---------------------------
   ROUTES
   --------------------------- */

app.post("/login", (req, res) => {
  const { username, password } = req.body;

  const user = borrowers.find(
    (b) => b.name === username && b.isPassword(password)
  );

  if (!user) return res.redirect("/index.html?error=1");

  currentUser = user; // <-- REPLACES req.session.user
  res.redirect("/options.html");
});

app.get("/logout", (req, res) => {
  currentUser = null; // <-- CLEAR USER
  res.redirect("/index.html");
});

app.get("/books", requireLogin, (req, res) => {
  res.json(
    books.map((b) => ({
      title: b.title,
      author: b.author,
      status: b.status,
      dueDate: b.dueDate,
    }))
  );
});

app.post("/borrow", requireLogin, (req, res) => {
  const index = parseInt(req.body.index);
  const book = books[index];
  const user = currentUser;

  if (!book) return res.redirect("/borrow.html?error=invalid");
  if (
    (user.borrowed.length >= 3 && user != book.borrower) ||
    book.status == "UNAVAILABLE"
  ) {
    book.placeHold(user);
    return res.redirect("/options.html?hold=placed");
  } else if (book.status == "ON_HOLD" && book.queue[0] !== user) {
    book.placeHold(user);
    return res.redirect("/options.html?hold=placed");
  } else if (book.status == "ON_HOLD" && book.queue[0] === user) {
    book.queue.shift();
    book.holders.delete(user);
    book.signOutBy(user, dueDate());
    user.borrowed.push(book);
    return res.redirect("/options.html?borrow=success");
  } else {
    book.signOutBy(user, dueDate());
    user.borrowed.push(book);
    return res.redirect("/options.html?borrow=success");
  }
});

app.get("/borrowed", requireLogin, (req, res) => {
  res.json(
    currentUser.borrowed.map((b) => ({
      title: b.title,
      author: b.author,
      dueDate: b.dueDate,
      status: b.status,
    }))
  );
});

app.post("/return", requireLogin, (req, res) => {
  const index = parseInt(req.body.index);
  const user = currentUser;
  const book = user.borrowed[index];

  if (!book) return res.redirect("/return.html?error=invalid");

  book.returned();
  user.borrowed.splice(index, 1);

  return res.redirect("/options.html?return=success");
});

app.post("/api/reset", (req, res) => {
  // Reset borrowers
  borrowers.length = 0;
  for (const b of initialBorrowersData) {
    borrowers.push(new Borrower(b.name, b.password));
  }

  // Reset books
  books.length = 0;
  for (const b of initialBooksData) {
    books.push(new Book(b.title, b.author));
  }

  // Clear current user
  currentUser = null;

  res.json({ message: "Library reset to initial state" });
});

app.listen(3000, () => console.log("Server running on http://localhost:3000"));
