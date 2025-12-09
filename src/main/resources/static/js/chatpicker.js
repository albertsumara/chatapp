document.addEventListener("DOMContentLoaded", () => {
    const picker = document.getElementById("chat-picker");

    fetch('/api/users')
      .then(res => {
          if (!res.ok) throw new Error("Błąd pobierania użytkowników: " + res.status);
          return res.json();
      })
      .then(users => {
          users.forEach(u => {
              const div = document.createElement('div');
              div.className = 'picker-item';
              div.textContent = u.username;
              picker.appendChild(div);
          });
      })
      .catch(err => {
          console.error(err);
          picker.textContent = "Nie udało się wczytać użytkowników.";
      });
});
