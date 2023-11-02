const searchUserInput = document.getElementById('searchUser');
const userSuggestions = document.getElementById('userSuggestions');
let selectedUser = {
    id: null,
    name: null
};

searchUserInput.addEventListener('input', () => {
    const searchTerm = searchUserInput.value;

    userSuggestions.innerHTML = '';

    if (searchTerm.trim() !== '') {
        fetch(`http://localhost:8080/api/users/search?nome=${searchTerm}`)
            .then(response => response.json())
            .then(data => {
                data.forEach(user => {
                    const userOption = document.createElement('li');
                    userOption.classList.add('suggestion-item');

                    const userHeader = document.createElement('h3');
                    userHeader.style.color = 'black'; 
                    userHeader.innerHTML = `<strong>Usuário:</strong> ${user.name}`;
                    userOption.appendChild(userHeader);
                
                    userOption.addEventListener('click', () => {
                        searchUserInput.value = user.name;
                        selectedUser.id = user.id;  // Salvar o ID do usuário selecionado
                        selectedUser.name = user.name;  // Salvar o nome do usuário selecionado
                        console.log('Usuário selecionado:', selectedUser);
                        userSuggestions.ingnerHTML = '';
                    });
                    userSuggestions.appendChild(userOption);
                });
            })
            .catch(error => {
                console.error(error);
            });
    }
});

// userOption.addEventListener('click', () => {
//     searchUserInput.value = user.nome;
//     selectedUser = user.name;
//     console.log('Usuário selecionado:', selectedUser);
//     userSuggestions.innerHTML = '';
// });


function processSelectedUser() {
    if (selectedUser) {
        console.log('Usuário selecionado:', selectedUser);
    } else {
        console.log('Nenhum usuário selecionado.');
    }
}

const searchResourceInput = document.getElementById('searchResource');
const resourceSuggestions = document.getElementById('resourceSuggestions');
let selectedResource = {
    id: null,
    name: null
};

searchResourceInput.addEventListener('input', () => {
    const searchTerm = searchResourceInput.value;

    resourceSuggestions.innerHTML = '';

    if (searchTerm.trim() !== '') {
        fetch(`http://localhost:8080/api/resources/search?nome=${searchTerm}`)
            .then(response => response.json())
            .then(data => {
                data.forEach(resource => {
                    const resourceOption = document.createElement('li');
                    resourceOption.classList.add('suggestion-item');

                    const resourceHeader = document.createElement('h3');
                    resourceHeader.style.color = 'black';
                    resourceHeader.innerHTML = `<strong>Recurso:</strong> ${resource.name}`;
                    resourceOption.appendChild(resourceHeader);

                    resourceOption.addEventListener('click', () => {
                        searchResourceInput.value = resource.name;
                        selectedResource.id = resource.id;  
                        selectedResource.name = resource.name; 
                        console.log('Recurso selecionado:', selectedResource);
                        resourceSuggestions.innerHTML = '';
                    });
                    resourceSuggestions.appendChild(resourceOption);
                });
            })
            .catch(error => {
                console.error(error);
            });
    }
});


const spaceForm = document.getElementById('spaceForm');

spaceForm.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(spaceForm);

    const spaceData = {
        nome: formData.get('nome'),
        usuario_id: selectedUser.id,
        recurso_id: selectedResource.id, 
        endereco: {
            cep: document.getElementById('cep').value,
            numero_endereco: document.getElementById('numero_endereco').value,
            complemento: document.getElementById('complemento').value
        },
        horario_funcionamento: document.getElementById('horario_funcionamento').value,
        descricao_espaco: document.getElementById('descricao_espaco').value
    };

    console.log("o spaceData é: " + spaceData);

    fetch('http://localhost:8080/api/spaces', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(spaceData),
    })
        .then(response => {
            if (response.status === 201) {
                document.getElementById('mensagem-sucesso').style.display = 'block';
            } else {
                console.error('Erro ao criar espaço:', response.statusText);
            }
        })
        .catch(error => {
            console.error('Erro ao criar espaço:', error);
        });
});


function buscarNomeUsuarioPorID() {
    const userID = document.getElementById('userIDInput').value;

    fetch(`http://localhost:8080/api/users/${userID}`)
        .then(response => response.json())
        .then(data => {
            const userResult = document.getElementById('userResult');
            userResult.innerHTML = `Nome do Usuário: ${data.nome}`; 
        })
        .catch(error => {
            const userResult = document.getElementById('userResult');
            userResult.innerHTML = `Usuário não encontrado ou ocorreu um erro: ${error}`;
        });
}
