package br.com.catalisa.ZuPlaceApi.model;

import br.com.catalisa.ZuPlaceApi.enums.PersonType;
import br.com.catalisa.ZuPlaceApi.validation.CpfOrCnpj;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Email(message = "Email inválido")
    @NotEmpty(message = "Email não pode ser vazio")
    private String email;
    @NotEmpty(message = "a senha não pode estar vazia")
    private String password;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    private String phone;
    @CpfOrCnpj
    private String documentType;

    @OneToMany(mappedBy = "user")
    private List<SpaceModel> spaces;
}
