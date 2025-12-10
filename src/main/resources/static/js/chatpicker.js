document.addEventListener("DOMContentLoaded", () => {
    const picker = document.getElementById("chat-picker");

    fetch('/api/users')
      .then(res => {
          if (!res.ok) throw new Error("Błąd pobierania użytkowników: " + res.status);
          return res.json();
      })
      .then(users => {
          picker.innerHTML = ""; // czyścimy wcześniej listę

          users.forEach(u => {
              const div = document.createElement('div');
              div.className = 'picker-item';
              div.style.display = 'flex';
              div.style.alignItems = 'center';
              div.style.justifyContent = 'space-between';
              div.style.padding = '5px 10px';

              const nameSpan = document.createElement('span');
              nameSpan.textContent = u.username;

              const statusCircle = document.createElement('span');
              statusCircle.style.width = '12px';
              statusCircle.style.height = '12px';
              statusCircle.style.borderRadius = '50%';
              statusCircle.style.display = 'inline-block';
              statusCircle.style.backgroundColor = u.logged ? 'green' : 'red';

              div.appendChild(nameSpan);
              div.appendChild(statusCircle);
              picker.appendChild(div);
          });
      })
      .catch(err => {
          console.error(err);
          picker.textContent = "Nie udało się wczytać użytkowników.";
      });
});
