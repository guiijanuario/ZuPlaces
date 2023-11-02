function getSpacesByProximity(maxDistance = 15000) {
  fetch('http://localhost:8080/api/location')
    .then(response => response.json())
    .then(userLocation => {
      fetch('http://localhost:8080/api/location', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          latitudeOrigem: userLocation.latitude,
          longitudeOrigem: userLocation.longitude,
          maxDistance: maxDistance,
        }),
      })
        .then(response => response.json())
        .then(spaces => {
          const map = new google.maps.Map(document.getElementById("gmp-map"), {
            zoom: 14,
            center: {
              lat: userLocation.latitude,
              lng: userLocation.longitude,
            },
            fullscreenContro: false,
            zoomControl: true,
            streetViewControl: true,
          });
          const userMarker = new google.maps.Marker({
            position: {
              lat: userLocation.latitude,
              lng: userLocation.longitude,
            },
            map,
            title: "Minha Localização",
            icon: {
              url: "./images/icon/icon-user.png",
            },
          });

          const spaceList = document.getElementById('space-list');
          spaceList.innerHTML = '';

          spaces.forEach(space => {
            const spaceLatLng = {
              lat: space.endereco_completo.latitude,
              lng: space.endereco_completo.longitude,
            };

            const marker = new google.maps.Marker({
              position: spaceLatLng,
              map,
              title: space.nome,
              icon: {
                url: "./images/icon/icon-space.png",
              }
            });

            const contentString = `<div>
            <h2>${space.nome}</h2>
              <p><strong>Recurso:</strong> ${space.recurso.nome}</p>
              <p><strong>Descrição do recurso:</strong> ${space.descricao}</p>
              <p><strong>Horário de funcionamento:</strong> ${space.horario_funcionamento}</p>
              <p><strong>Endereço:</strong> ${space.endereco_completo.logradouro} ${space.endereco_completo.numero_endereco}, ${space.endereco_completo.bairro}</p>
              <p><strong>Cidade:</strong> ${space.endereco_completo.localidade} ${space.endereco_completo.uf}</p>
              <a class="open-maps-button" href="https://www.google.com/maps/search/?api=1&query=${space.endereco_completo.latitude},${space.endereco_completo.longitude}" target="_blank">Abrir no Google Maps</a>
            </div>`;
            const infowindow = new google.maps.InfoWindow({
              content: contentString,
            });

            marker.addListener("click", () => {
              infowindow.open(map, marker);
            });

            const spaceDetails = document.createElement('div');
            spaceDetails.innerHTML = `<h4>${space.nome}</h4></a>
            <div class="space-details">
                <p> <strong>Recurso:</strong> ${space.recurso.nome}</p>
                <p> <strong>Descrição do recurso:</strong> ${space.descricao}</p>
                <p> <strong>Horário de funcionamento:</strong> ${space.horario_funcionamento}</p>
                <p> <strong>Endereço:</strong> ${space.endereco_completo.logradouro} ${space.endereco_completo.numero_endereco}, ${space.endereco_completo.bairro}</p>
                <p> <strong>Cidade:</strong> ${space.endereco_completo.localidade} ${space.endereco_completo.uf}</p>
                <p> <strong>CEP:</strong> ${space.endereco_completo.cep}</p>
                <a class="open-maps-button-list" href="https://www.google.com/maps/search/?api=1&query=${space.endereco_completo.latitude},${space.endereco_completo.longitude}" target="_blank">Abrir no Google Maps</a>
            </div> `;

           
            

            spaceList.appendChild(spaceDetails);
          });
        })
        .catch(error => {
          console.error('Erro ao obter espaços próximos: ' + error);
        });
    })
    .catch(error => {
      console.error('Erro ao obter a localização do usuário: ' + error);
    });
}
  
  
  function setDistance(maxDistance) {
    getSpacesByProximity(maxDistance);
  }
  
  function updateDistance() {
    const slider = document.getElementById("distance-slider");
    const output = document.getElementById("distance-output");
  
    const distance = slider.value;
    output.textContent = `${distance / 1000} km`;

    setDistance(distance);
  }

  
  