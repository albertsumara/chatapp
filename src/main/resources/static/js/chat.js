const input = document.getElementById("message");
const button = document.getElementById("submit-message");

button.addEventListener("click", () => {
    const message = input.value;

    if (!message) {
        alert("Wpisz wiadomość!");
        return;
    }

    fetch("/api/message", {
        method: "POST",
        headers: {
            "Content-Type": "text/plain"
        },
        body: message
    })
    .then(response => response.text())
    .then(result => {
        console.log("Odpowiedź z serwera:", result);
        input.value = "";
    })
    .catch(error => console.error("Błąd przy wysyłaniu:", error));
});