package br.com.catalisa.ZuPlaceApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequestDto {
    @Schema(description = "Nome completo", example = "Ricardo dos Santos")
    @JsonProperty("nome")
    private String name;

    @JsonProperty("email")
    @Schema(description = "E-mail", example = "usuario@zup.com.br")
    private String email;

    @JsonProperty("telefone")
    @Schema(description = "Telefone", example = "11988887777")
    private String phone;

    @JsonProperty("espaco_indicado")
    @Schema(description = "Espaço", example = "Biblioteca - Bloco A")
    private String spaceIndicated;

    @JsonProperty("descricao")
    @Schema(description = "Descrição", example = "Gostaria de indicar esse espaço na empresa XPTO")
    private String description;
}
