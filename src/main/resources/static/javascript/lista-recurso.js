function preencherTabelaRecursos(recursos) {
    const tabela = document.getElementById("recursoTableBody");
    tabela.innerHTML = "";

    recursos.forEach(recurso => {
        const newRow = tabela.insertRow();
        newRow.innerHTML = `
            <td>${recurso.id}</td>
            <td>${recurso.nome}</td>
            <td>
                <button class="btn btn-primary" onclick="editarRecurso(${recurso.id})">Editar</button>
                <button class="btn btn-danger" onclick="excluirRecurso(${recurso.id})">Excluir</button>
            </td>
        `;
    });
}

function editarRecurso(id) {
    window.location.href = `editar_recurso.html?id=${id}`;
}

function excluirRecurso(id) {
    if (confirm(`Tem certeza de que deseja excluir o recurso com ID ${id}?`)) {
        fetch(`http://localhost:8080/api/resources/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.status === 204) {
                alert(`Recurso com ID ${id} excluído com sucesso.`);
                atualizarListaDeRecursos();
            } else {
                alert('Erro ao excluir o recurso.');
            }
        })
        .catch(error => {
            console.error('Erro ao excluir o recurso:', error);
        });
    }
}

function atualizarListaDeRecursos() {
    fetch('http://localhost:8080/api/resources')
        .then(response => response.json())
        .then(data => {
            preencherTabelaRecursos(data);
        })
        .catch(error => {
            console.error('Erro ao obter a lista de recursos:', error);
        });
}

window.addEventListener('load', () => {
    fetch('http://localhost:8080/api/resources')
        .then(response => response.json())
        .then(data => {
            preencherTabelaRecursos(data);
        })
        .catch(error => {
            console.error('Erro ao obter a lista de recursos:', error);
        });
});
