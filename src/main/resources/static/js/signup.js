const signupButton = document.getElementById('signup-button');
const messageDiv = document.getElementById('signup-message');

signupButton.addEventListener('click', async () => {
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, email, password }),
        });

        const result = await response.text();

        if (response.ok) {
            messageDiv.textContent = result;
            messageDiv.style.color = 'green';
        } else {
            messageDiv.textContent = result;
            messageDiv.style.color = 'red';
        }

    } catch (error) {
        messageDiv.textContent = 'Server error. Try again later.';
        messageDiv.style.color = 'red';
    }
});
