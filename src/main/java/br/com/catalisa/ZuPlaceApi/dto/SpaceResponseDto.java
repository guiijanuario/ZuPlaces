package br.com.catalisa.ZuPlaceApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceResponseDto {
    @JsonProperty("nome")
    private String name;
    @JsonProperty("usuario_completo")
    private UserResponseDto user;
    @JsonProperty("recurso")
    private ResourceResponseDto resource;
    @JsonProperty("endereco_completo")
    private AddressResponseDto address;
    @JsonProperty("horario_funcionamento")
    private String openingHours;
    @JsonProperty("descricao")
    private String description;
    @JsonProperty("distancia")
    private double distance;
}
