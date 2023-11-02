package br.com.catalisa.ZuPlaceApi.dto;

import br.com.catalisa.ZuPlaceApi.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
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
