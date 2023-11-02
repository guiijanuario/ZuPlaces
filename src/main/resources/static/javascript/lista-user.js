function preencherTabelaUser(users) {
    const tabela = document.getElementById("usersTableBody");
    tabela.innerHTML = "";

    users.forEach(user => {

        if(user.tipo_pessoa == "PHYSICAL_PERSON") {
            user.tipo_pessoa = "Pessoa Física";
        } else {
            user.tipo_pessoa = "Pessoa Juridica";
        }
        
        const newRow = tabela.insertRow();
        newRow.innerHTML = `
            <td>${user.id}</td>
            <th>${user.nome}</th>
            <th>${user.email}</th>
            <th>${user.telefone}</th>
            <th>${user.documento_identificacao}</th>
            <th>${user.tipo_pessoa}</th>
            <td>
                <button id="button-editar" class="btn btn-primary" onclick="editarUser(${user.id})">Editar</button>
                <button id="button-excluir" class="btn btn-danger" onclick="excluirUser(${user.id})">Excluir</button>
            </td>
        `;
    });
}


function editarUser(id) {
    window.location.href = `editar_user.html?id=${id}`;
}

function excluirUser(id) {
    if (confirm(`Tem certeza de que deseja excluir o usuário com ID ${id}?`)) {
        fetch(`http://localhost:8080/api/users/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.status === 204) {
                alert(`Usuário com ID ${id} excluído com sucesso.`);
                atualizarListaDeUsers();
            } else {
                alert('Erro ao excluir o usuário.');
            }
        })
        .catch(error => {
            console.error('Erro ao excluir o usuário:', error);
        });
    }
}

function atualizarListaDeUsers() {
    fetch('http://localhost:8080/api/users')
        .then(response => response.json())
        .then(data => {
            preencherTabelaUser(data);
        })
        .catch(error => {
            console.error('Erro ao obter a lista de usuários:', error);
        });
}

window.addEventListener('load', () => {
    fetch('http://localhost:8080/api/users')
        .then(response => response.json())
        .then(data => {
            preencherTabelaUser(data);
        })
        .catch(error => {
            console.error('Erro ao obter a lista de usuários:', error);
        });
});
