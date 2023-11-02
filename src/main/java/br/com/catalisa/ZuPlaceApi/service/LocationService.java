package br.com.catalisa.ZuPlaceApi.service;

import br.com.catalisa.ZuPlaceApi.dto.CoordsResponseDto;
import br.com.catalisa.ZuPlaceApi.dto.ResourceResponseDto;
import br.com.catalisa.ZuPlaceApi.dto.SpaceResponseProximityLocationDto;
import br.com.catalisa.ZuPlaceApi.dto.UserResponseDto;
import br.com.catalisa.ZuPlaceApi.exception.ExternalRequestFailureException;
import br.com.catalisa.ZuPlaceApi.model.AddressModel;
import br.com.catalisa.ZuPlaceApi.model.SpaceModel;
import br.com.catalisa.ZuPlaceApi.repository.SpaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {


    @Autowired
    GoogleMapsService googleMapsService;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    ModelMapper modelMapper;


    public List<SpaceResponseProximityLocationDto> findSpacesByAddressProximity(Double latitudeOrigem, Double longitudeOrigem, Double maxDistance) {
        List<SpaceModel> spaces = spaceRepository.findAll();

        List<SpaceResponseProximityLocationDto> spaceResponseProximityLocationResultDtos = spaces.stream()
                .map(spaceModel -> modelMapper.map(spaceModel, SpaceResponseProximityLocationDto.class))
                .collect(Collectors.toList());

        List<SpaceResponseProximityLocationDto> result = new ArrayList<>();

        for(SpaceResponseProximityLocationDto spaceDto : spaceResponseProximityLocationResultDtos) {

            AddressModel address = spaceDto.getAddress();
            UserResponseDto user = spaceDto.getUser();
            ResourceResponseDto resource = spaceDto.getResource();

            if (address != null) {
                String addresString = address.getLogradouro() + " " + address.getNumberAddress() + " " + address.getLocalidade();
                CoordsResponseDto coords = googleMapsService.geocodeAddress(addresString);

                double distance = googleMapsService.getDistanceBetweenCeps(
                        latitudeOrigem, longitudeOrigem, coords.getLatitude(), coords.getLongitude());

                if (distance <= maxDistance) {
                    spaceDto.setName(spaceDto.getName());
                    spaceDto.setUser(user);
                    spaceDto.setResource(resource);
                    spaceDto.setDistance(distance);
                    address.setLatitude(coords.getLatitude());
                    address.setLongitude(coords.getLongitude());
                    spaceDto.setAddress(address);
                    result.add(spaceDto);
                }
            }
        }
        result = sortSpacesByDistance(result);
        return result;
    }

    public List<SpaceResponseProximityLocationDto> findSpacesByResources(Double latitudeOrigem, Double longitudeOrigem, String resource) throws ExternalRequestFailureException {
        List<SpaceModel> spaces = spaceRepository.findAll();

        List<SpaceResponseProximityLocationDto> spaceResponseProximityLocationResultDtos = spaces.stream()
                .map(spaceModel -> modelMapper.map(spaceModel, SpaceResponseProximityLocationDto.class))
                .collect(Collectors.toList());

        List<SpaceResponseProximityLocationDto> result = new ArrayList<>();
        for(SpaceResponseProximityLocationDto spaceDto : spaceResponseProximityLocationResultDtos) {
            try {
                AddressModel address = spaceDto.getAddress();
                UserResponseDto user = spaceDto.getUser();
                ResourceResponseDto resourceResponseDto = spaceDto.getResource();

                if (address != null) {
                    String addresString = address.getLogradouro() + " " + address.getNumberAddress() + " " + address.getLocalidade();
                    CoordsResponseDto coords = googleMapsService.geocodeAddress(addresString);

                    double distance = googleMapsService.getDistanceBetweenCeps(
                            latitudeOrigem, longitudeOrigem, coords.getLatitude(), coords.getLongitude());

                    if (spaceDto.getResource().getName().equalsIgnoreCase(resource)){
                        spaceDto.setName(spaceDto.getName());
                        spaceDto.setUser(user);
                        spaceDto.setResource(resourceResponseDto);
                        spaceDto.setDistance(distance);
                        address.setLatitude(coords.getLatitude());
                        address.setLongitude(coords.getLongitude());
                        spaceDto.setAddress(address);
                        result.add(spaceDto);
                    }
                }
            } catch (Exception e) {
                throw e;
            }
        }
        result = sortSpacesByDistance(result);
        return result;
    }

    public static class SpaceDistanceComparator implements Comparator<SpaceResponseProximityLocationDto> {
        @Override
        public int compare(SpaceResponseProximityLocationDto space1, SpaceResponseProximityLocationDto space2) {
            return Double.compare(space1.getDistance(), space2.getDistance());
        }
    }

    public List<SpaceResponseProximityLocationDto> sortSpacesByDistance(List<SpaceResponseProximityLocationDto> result) {
        SpaceDistanceComparator comparator = new SpaceDistanceComparator();
        result.sort(comparator);
        return result;
    }
}
