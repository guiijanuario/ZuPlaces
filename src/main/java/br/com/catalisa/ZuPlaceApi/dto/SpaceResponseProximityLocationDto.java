package br.com.catalisa.ZuPlaceApi.dto;

import br.com.catalisa.ZuPlaceApi.model.AddressModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceResponseProximityLocationDto {
    @JsonProperty("nome")
    private String name;
    @JsonProperty("usuario")
    private UserResponseDto user;
    @JsonProperty("recurso")
    private ResourceResponseDto resource;
    @JsonProperty("endereco_completo")
    private AddressModel address;
    @JsonProperty("horario_funcionamento")
    private String openingHours;
    @JsonProperty("descricao")
    private String description;
    @JsonProperty("distancia")
    private double distance;
}
