document.addEventListener("DOMContentLoaded", () => {
    const input = document.getElementById("message");
    const button = document.getElementById("submit-message");

    const params = new URLSearchParams(window.location.search);
    const username = params.get("username");
    const receiverId = params.get("receiverId");


    document.getElementById("chatHeader").textContent = `Chat with ${username}`;

    button.addEventListener("click", () => {
        const content = input.value.trim();
        if (!content) return alert("Wpisz wiadomość!");

        fetch("/api/message", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ receiverId: receiverId, content })
        })
        .then(res => res.json())
        .then(savedMessage => {
            console.log("Wiadomość wysłana:", savedMessage);
            input.value = "";
        })
        .catch(err => console.error("Błąd przy wysyłaniu:", err));
    });
});
