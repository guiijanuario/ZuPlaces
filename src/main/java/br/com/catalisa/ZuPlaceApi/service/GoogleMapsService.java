package br.com.catalisa.ZuPlaceApi.service;

import br.com.catalisa.ZuPlaceApi.dto.*;
import br.com.catalisa.ZuPlaceApi.exception.ExternalRequestFailureException;
import com.google.gson.Gson;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class GoogleMapsService {
   @Getter
    @Autowired
    @Value("${google.maps.api.key}")
    private String apiKey;

    @Autowired
    @Value("${abstractapi.api.key}")
    private String abstractApiKey;

    private static final Gson gson = new Gson();


    public GeoLocationUserResponseDto getLatitudeAndLongitudeUser() throws ExternalRequestFailureException {
        try {
            String baseURL = "http://ipgeolocation.abstractapi.com/v1/";
            String url = baseURL
                    + "?api_key=" + abstractApiKey;

            HttpClient httpClient = HttpClient.newHttpClient();

            System.out.println("O httpClient é assim: " + httpClient);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            System.out.println("O httpRequest é assim: " + httpRequest);

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println("O httpResponse é assim: " + httpResponse);

            GetGeolocationDto getGeolocationDto = gson.fromJson(httpResponse.body(), GetGeolocationDto.class);

            if (getGeolocationDto.getLatitude() < 0 && getGeolocationDto.getLongitude() < 0) {
                double latitude = getGeolocationDto.getLatitude();
                double longitude = getGeolocationDto.getLongitude();
                String ipAddress = getGeolocationDto.getIp_address();
                return new GeoLocationUserResponseDto(latitude, longitude,ipAddress);
            }
            return new GeoLocationUserResponseDto(0.0, 0.0, null);
        }catch (IOException | InterruptedException e){
            throw new ExternalRequestFailureException("Falhou" + e);
        }
    }


    public CoordsResponseDto geocodeAddress(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String baseURL = "https://maps.googleapis.com/maps/api/geocode/json";
            String url = baseURL
                    + "?address=" + encodedAddress
                    + "&key=" + apiKey;

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

           GoogleGeocodeResponseDto googleGeocodeResponseDto = gson.fromJson(httpResponse.body(), GoogleGeocodeResponseDto.class);

            if (googleGeocodeResponseDto.getResults() != null && googleGeocodeResponseDto.getResults() != null && googleGeocodeResponseDto.getResults().length > 0) {
                GoogleGeocodeResponseDto.Result result = googleGeocodeResponseDto.getResults()[0];
                double latitude = result.getGeometry().getLocation().getLat();
                double longitude = result.getGeometry().getLocation().getLng();
                return new CoordsResponseDto(latitude, longitude);
            }

            return new CoordsResponseDto(0.0, 0.0);
        }catch (IOException | InterruptedException e){
            throw new ExternalRequestFailureException("Falhou: " + e.getMessage());
        }
    }

    public double getDistanceBetweenCeps(Double originLat, Double originLng, Double destinationLat, Double destinationLng) {
        try {
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json"
                    + "?origins=" + originLat + "," + originLng
                    + "&destinations=" + destinationLat + "," + destinationLng
                    + "&key=" + apiKey;

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            GoogleDistanceMatrixResponseDto googleDistanceMatrixResponseDto = gson.fromJson(httpResponse.body(), GoogleDistanceMatrixResponseDto.class);

            if (googleDistanceMatrixResponseDto != null && googleDistanceMatrixResponseDto.getRows() != null && googleDistanceMatrixResponseDto.getRows().length > 0) {
                GoogleDistanceMatrixResponseDto.Row row = googleDistanceMatrixResponseDto.getRows()[0];
                if (row.getElements() != null && row.getElements().length > 0) {
                    GoogleDistanceMatrixResponseDto.Element element = row.getElements()[0];
                    GoogleDistanceMatrixResponseDto.Distance distance = element.getDistance();
                    if (distance != null) {
                        double distanceValue = distance.getValue();
                        System.out.println("Valor de 'value': " + distanceValue);
                        return distanceValue;
                    }
                }
            }
            return 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
