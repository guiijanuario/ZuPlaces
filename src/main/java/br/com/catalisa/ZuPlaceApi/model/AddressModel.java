package br.com.catalisa.ZuPlaceApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Endereço")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    private Double latitude;
    private Double longitude;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private SpaceModel space;
}
