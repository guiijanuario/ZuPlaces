document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const email = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch('http://localhost:8080/api/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
    })
    .then(response => {
        if (response.status === 200) {
            window.location.href = 'logado.html';
        } else {
            alert('Credenciais inválidas. Tente novamente.');
        }
    })
    .catch(error => {
        console.error('Erro ao fazer login:', error);
    });
});
