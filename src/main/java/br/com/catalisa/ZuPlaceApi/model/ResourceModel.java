package br.com.catalisa.ZuPlaceApi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Recurso")
public class ResourceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Nome", example = "Água potável")
    private String name;
    @OneToMany(mappedBy = "resource")
    private List<SpaceModel> spaces;
}