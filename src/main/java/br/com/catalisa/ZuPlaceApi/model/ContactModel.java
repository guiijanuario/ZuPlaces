package br.com.catalisa.ZuPlaceApi.model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Schema(name = "Contato")
public class ContactModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nome completo", example = "Ricardo dos Santos")
    private String name;

    @Schema(description = "E-mail", example = "usuario@zup.com.br")
    private String email;

    @Schema(description = "Telefone", example = "11988887777")
    private String phone;

    @Schema(description = "Espaço Indicado", example = "USP")
    private String spaceIndicated;

    @Schema(description = "Descrição", example = "Gostaria de indicar esse espaço na empresa XPTO")
    private String description;
}
