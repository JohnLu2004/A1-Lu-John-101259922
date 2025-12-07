// public/js/options.js
async function apiGet(path) {
  const token = localStorage.getItem("lib_token");
  const r = await fetch(path, { headers: { Authorization: token } });
  if (r.status === 401) {
    localStorage.removeItem("lib_token");
    window.location.href = "/";
    throw new Error("Not authenticated");
  }
  return r.json();
}

async function logout() {
  const token = localStorage.getItem("lib_token");
  await fetch("/api/logout", {
    method: "POST",
    headers: { Authorization: token },
  });
  localStorage.removeItem("lib_token");
  window.location.href = "/";
}

document.getElementById("logoutBtn").addEventListener("click", logout);

(async function init() {
  const token = localStorage.getItem("lib_token");
  if (!token) return (window.location.href = "/");
  try {
    const me = await apiGet("/api/me");
    document.getElementById("welcome").innerText = `Logged in as: ${me.name}`;
    const ready = await apiGet("/api/ready");
    const notif = document.getElementById("notifications");
    if (ready && ready.length) {
      notif.innerHTML = "<h3>Notifications</h3>";
      const ul = document.createElement("ul");
      ready.forEach((r) => {
        const li = document.createElement("li");
        li.innerText = `Hold ready: ${r.title} by ${r.author} (index ${r.index})`;
        ul.appendChild(li);
      });
      notif.appendChild(ul);
      notif.innerHTML += "<p>You can borrow them by choosing Borrow Book.</p>";
    } else {
      notif.innerText = "";
    }
  } catch (e) {
    console.error(e);
  }
})();
