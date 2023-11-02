package br.com.catalisa.ZuPlaceApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddressResponseDto {

    @Schema(description = "CEP", example = "87000-00")
    private String cep;

    @Schema(description = "Rua/Av", example = "Rua Juruá")
    private String logradouro;

    @Schema(description = "Número do Endereço", example = "30 A")
    @JsonProperty("numero_endereco")
    private String numberAddress;

    @Schema(description = "Complemento do endereço", example = "Ponto de referência")
    @JsonProperty("complemento")
    private String complement;

    @Schema(description = "Bairro", example = "Jardim Antunes")
    private String bairro;

    @Schema(description = "Cidade", example = "Maringá")
    private String localidade;

    @Schema(description = "Estado", example = "PR")
    private String uf;

    @Schema(description = "Latitude")
    private Double latitude;

    @Schema(description = "Longitude")
    private Double longitude;
}
