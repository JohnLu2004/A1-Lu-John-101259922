fetch("/api/borrowed")
  .then((r) => r.json())
  .then((data) => {
    let div = document.getElementById("borrowed");
    data.forEach((b, i) => {
      let p = document.createElement("p");
      p.innerHTML = `${i}: ${b.title} (Due: ${b.dueDate})`;
      let btn = document.createElement("button");
      btn.textContent = "Return";
      btn.onclick = () => {
        fetch("/api/return", {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: `index=${i}`,
        })
          .then((r) => r.json())
          .then((result) => {
            alert("Book returned");
            location.reload();
          });
      };
      div.appendChild(p);
      div.appendChild(btn);
    });
  });
