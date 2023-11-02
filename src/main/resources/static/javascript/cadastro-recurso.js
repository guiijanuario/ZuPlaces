  document.getElementById("recursos-form").addEventListener("submit", function (event) {
    event.preventDefault(); 

    const nome = document.getElementById("nomeRecurso").value;

    const data = {
      nome: nome
    };

    fetch('http://localhost:8080/api/resources', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    })
      .then(response => response.json())
      .then(data => {
        console.log(data);

        // document.getElementById("mensagem-sucesso").style.display = "block";
        alert("Recurso cadastrado com sucesso!")
      })
      .catch(error => {
        console.error('Erro ao enviar solicitação:', error);
      });
  });