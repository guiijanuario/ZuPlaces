package br.com.catalisa.ZuPlaceApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpaceRequestResourceLocationDto {
    private Double latitudeOrigem;
    private Double longitudeOrigem;
    private String recurso;
}
