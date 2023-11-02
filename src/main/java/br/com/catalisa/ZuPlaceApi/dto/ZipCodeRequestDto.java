package br.com.catalisa.ZuPlaceApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Endereço")
public class ZipCodeRequestDto {
    @Schema(description = "CEP", example = "87000-000")
    private String cep;

    @Schema(description = "Número do Endereço", example = "30 A")
    @JsonProperty("numero_endereco")
    private String numberAddress;

    @Schema(description = "Complemento do endereço", example = "Ponto de referência")
    @JsonProperty("complemento")
    private String complement;
}
