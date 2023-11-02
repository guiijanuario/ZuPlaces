package br.com.catalisa.ZuPlaceApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceRequestDto {

    @JsonProperty("nome")
    private String name;

    @JsonProperty("usuario_id")
    private Long userId;

    @JsonProperty("recurso_id")
    private Long resourceId;

    @JsonProperty("endereco")
    private ZipCodeRequestDto address;

    @JsonProperty("horario_funcionamento")
    private String openingHours;

    @JsonProperty("descricao_espaco")
    private String description;
}
