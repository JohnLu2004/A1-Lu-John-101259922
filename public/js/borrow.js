fetch("/api/books")
  .then((r) => r.json())
  .then((data) => {
    let div = document.getElementById("books");
    data.forEach((b, i) => {
      let p = document.createElement("p");
      p.innerHTML = `${i}: ${b.title} by ${b.author} — ${b.status}`;
      let btn = document.createElement("button");
      btn.textContent = "Borrow";
      btn.onclick = () => {
        fetch("/api/borrow", {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: `index=${i}`,
        })
          .then((r) => r.json())
          .then((result) => {
            if (result.success) alert("Borrowed successfully!");
            else alert("Book unavailable. Hold placed.");
            location.reload();
          });
      };
      div.appendChild(p);
      div.appendChild(btn);
    });
  });
