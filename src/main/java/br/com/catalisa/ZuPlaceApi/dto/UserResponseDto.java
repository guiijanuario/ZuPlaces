package br.com.catalisa.ZuPlaceApi.dto;

import br.com.catalisa.ZuPlaceApi.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDto {
    private Long id;
    @JsonProperty("nome")
    private String name;
    private String email;
    @JsonProperty("senha")
    private String password;
    @JsonProperty("tipo_pessoa")
    private PersonType personType;
    @JsonProperty("telefone")
    private String phone;
    @JsonProperty("documento_identificacao")
    private String documentType;
}
