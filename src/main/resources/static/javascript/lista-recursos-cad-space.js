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

// Função para processar o recurso selecionado
function processSelectedResource() {
    if (selectedResource) {
        console.log('Recurso selecionado:', selectedResource);
        return selectedResource;
    } else {
        console.log('Nenhum recurso selecionado.');
    }
}