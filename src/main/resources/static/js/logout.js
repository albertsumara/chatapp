document.addEventListener("DOMContentLoaded", () => {
    const logoutButton = document.getElementById("logout-button");

    logoutButton.addEventListener("click", () => {
        fetch("/api/logout", { method: "POST" })
            .then(response => response.text())
            .then(msg => {
                alert(msg);
                window.location.href = "index.html";
            })
            .catch(error => console.error("Error:", error));
    });
});
