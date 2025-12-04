document.addEventListener("DOMContentLoaded", function () {
    const loginButton = document.getElementById("signin-button");
    const messageDiv = document.getElementById("signin-message");

    loginButton.addEventListener("click", function () {
        const usernameInput = document.getElementById("username");
        const passwordInput = document.getElementById("password");

        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();

        if (!username || !password) {
            messageDiv.innerText = "Please enter username and password";
            messageDiv.style.color = "red";
            return;
        }

        fetch("/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        })
        .then(response => response.text())
        .then(text => {
            if (text === "success") {
                window.location.href = "lobby.html";
            } else {
                messageDiv.innerText = text;
                messageDiv.style.color = "red";
            }
        })
        .catch(error => {
            messageDiv.innerText = "Error connecting to server";
            messageDiv.style.color = "red";
            console.error(error);
        });
    });
});
