// public/js/login.js
document.getElementById("loginForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const password = document.getElementById("password").value;
  try {
    const r = await fetch("/api/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ password }),
    });
    if (!r.ok) {
      const err = await r.json();
      document.getElementById("msg").innerText =
        "Login failed: " + (err.error || r.statusText);
      return;
    }
    const data = await r.json();
    // Save token and navigate to options
    localStorage.setItem("lib_token", data.token);
    localStorage.setItem("lib_name", data.name || "");
    window.location.href = "/options.html";
  } catch (err) {
    document.getElementById("msg").innerText = "Network error";
  }
});
