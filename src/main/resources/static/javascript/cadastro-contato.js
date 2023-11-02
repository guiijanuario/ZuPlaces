document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('contactForm');

    form.addEventListener('submit', function(event) {
      event.preventDefault();

      const contatoData = {
        nome: document.getElementById('nome').value,
        email: document.getElementById('email').value,
        telefone: document.getElementById('telefone').value,
        espaco_indicado: document.getElementById('espaco_indicado').value,
        descricao: document.getElementById('descricao').value,
      };
    
      fetch('http://localhost:8080/api/contacts', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(contatoData),
      })
        .then(response => response.json())
        .then(contatoData => {
          console.log(contatoData);
          if (contatoData) {
            console.log("Contato é: " + contatoData);
            alert('Contato foi enviado com sucesso!');
            form.reset();
          } else {
            alert('Ocorreu um erro durante o envio do contato. Tente novamente mais tarde.');
          }
        })
        .catch(error => {
          console.error('Erro ao envio do contato: ' + error);
          alert('Ocorreu um erro durante o envio do contato. Tente novamente mais tarde.');
        });
    });
  });


  