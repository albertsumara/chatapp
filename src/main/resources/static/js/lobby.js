document.addEventListener("DOMContentLoaded", function() {
    fetch("/api/check-session")
        .then(response => {
            if (!response.ok) {
                window.location.href = "signin.html";
            }
        })
        .catch(error => {
            console.error("Error checking session:", error);
            window.location.href = "signin.html";
        });
});
