document.addEventListener("DOMContentLoaded", () => {
    const input = document.getElementById("message");
    const button = document.getElementById("submit-message");
    const chatBox = document.getElementById("chat-box");

    const params = new URLSearchParams(window.location.search);
    const username = params.get("username");
    const receiverId = params.get("receiverId");


    document.getElementById("chatHeader").textContent = `Chat with ${username}`;



    const loadConversation = () => {
            fetch(`/api/message/conversation?receiverId=${receiverId}`)
                .then(res => res.json())
                .then(messages => {
                    chatBox.innerHTML = "";
                    messages.forEach(msg => {
                        const div = document.createElement("div");
                        div.className = "message-tile";
                        div.textContent = `${msg.senderUsername}: ${msg.content}`;
                        chatBox.appendChild(div);
                    });
                    chatBox.scrollTop = chatBox.scrollHeight;
                })
                .catch(err => console.error("Błąd przy pobieraniu wiadomości:", err));
        };


     loadConversation();

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
