package br.com.catalisa.ZuPlaceApi.controller;

import br.com.catalisa.ZuPlaceApi.dto.GeoLocationUserResponseDto;
import br.com.catalisa.ZuPlaceApi.dto.SpaceRequestProximityLocationDto;
import br.com.catalisa.ZuPlaceApi.dto.SpaceRequestResourceLocationDto;
import br.com.catalisa.ZuPlaceApi.dto.SpaceResponseProximityLocationDto;
import br.com.catalisa.ZuPlaceApi.exception.ExternalRequestFailureException;
import br.com.catalisa.ZuPlaceApi.service.AddressService;
import br.com.catalisa.ZuPlaceApi.service.GoogleMapsService;
import br.com.catalisa.ZuPlaceApi.service.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/location", produces = {"application/json"})
@Tag(name = "Feature - Location")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<GeoLocationUserResponseDto> getLocationUser() {
        logger.debug("Método getLocationUser() chamado");
        GeoLocationUserResponseDto geoLocationUserResponseDto = googleMapsService.getLatitudeAndLongitudeUser();
        logger.info("Latitude: {}, Longitude: {}", geoLocationUserResponseDto.getLatitude(), geoLocationUserResponseDto.getLongitude());
        return ResponseEntity.status(HttpStatus.OK).body(geoLocationUserResponseDto);
    }

    @PostMapping
    public ResponseEntity<List<SpaceResponseProximityLocationDto>> createLong(@RequestBody SpaceRequestProximityLocationDto spaceRequestProximityLocationDto) {
        List<SpaceResponseProximityLocationDto> spaceResponseProximityLocationDto = locationService.findSpacesByAddressProximity(
                spaceRequestProximityLocationDto.getLatitudeOrigem(),
                spaceRequestProximityLocationDto.getLongitudeOrigem(),
                spaceRequestProximityLocationDto.getMaxDistance());
        System.out.println("Requisição da lista de spaces feita: " + spaceResponseProximityLocationDto);
        return ResponseEntity.status(HttpStatus.OK).body(spaceResponseProximityLocationDto);
    }

    @PostMapping(path = "/resource")
    public ResponseEntity<List<SpaceResponseProximityLocationDto>> findForResource(@RequestBody SpaceRequestResourceLocationDto spaceRequestResourceLocationDto) {
        List<SpaceResponseProximityLocationDto> spaceResponseProximityLocationDto = locationService.findSpacesByResources(
                spaceRequestResourceLocationDto.getLatitudeOrigem(),
                spaceRequestResourceLocationDto.getLongitudeOrigem(),
                spaceRequestResourceLocationDto.getRecurso());
        System.out.println("Requisição da lista de spaces feita: " + spaceResponseProximityLocationDto);
        return ResponseEntity.status(HttpStatus.OK).body(spaceResponseProximityLocationDto);
    }
}
