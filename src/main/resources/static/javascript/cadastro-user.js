document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('cadastroUsuarioForm');

    let typePerson;

    form.addEventListener('submit', function(event) {
      event.preventDefault();

      console.log("Tamanho é : " + document.getElementById('documento').value.length)

      if (document.getElementById('documento').value.length === 11) {
        typePerson = "PHYSICAL_PERSON";
      } else if (document.getElementById('documento').value.length === 14) {
        typePerson = "LEGAL_PERSON";
      } else {
        typePerson = "INVALID_DOCUMENT";
      }
    
      const senha = document.getElementById('confirmarSenhaCadastro').value;
      const confirmarSenha = document.getElementById('confirmarSenhaCadastro').value;
    
      if (senha !== confirmarSenha) {
        alert('As senhas não correspondem');
        return;
      }
  
      const usuarioData = {
        nome: document.getElementById('nome').value,
        email: document.getElementById('login').value,
        senha: document.getElementById('confirmarSenhaCadastro').value,
        tipo_pessoa: typePerson,
        telefone: document.getElementById('telefone').value,
        documento_identificacao: document.getElementById('documento').value,
      };
    
      fetch('http://localhost:8080/api/users', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(usuarioData),
      })
        .then(response => response.json())
        .then(usuarioData => {
          console.log(usuarioData);
          if (usuarioData) {
            console.log("Usuário é: " + usuarioData);
            alert('Cadastro realizado com sucesso!');
            form.reset();
            window.location.href = './logado.html';
          } else {
            alert('Ocorreu um erro durante o cadastro. Tente novamente mais tarde.');
          }
        })
        .catch(error => {
          console.error('Erro ao realizar o cadastro: ' + error);
          alert('Ocorreu um erro durante o cadastro. Tente novamente mais tarde.');
        });
    });
  });


  