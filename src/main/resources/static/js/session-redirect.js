document.addEventListener("DOMContentLoaded", function() {
    fetch("/api/check-session")
        .then(response => {
            if (response.ok) {
                if (window.location.pathname.includes("signin.html")) {
                    window.location.href = "lobby.html";
                }
            } else {
                if ((window.location.pathname.includes("lobby.html")) ||
                    (window.location.pathname.includes("chat.html")))
                 {
                    window.location.href = "signin.html";
                }
            }
        })
        .catch(error => console.error("Error checking session:", error));
});
