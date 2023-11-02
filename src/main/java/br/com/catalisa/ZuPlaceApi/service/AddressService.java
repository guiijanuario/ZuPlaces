package br.com.catalisa.ZuPlaceApi.service;

import br.com.catalisa.ZuPlaceApi.dto.AddressResponseDto;
import br.com.catalisa.ZuPlaceApi.dto.ZipCodeRequestDto;
import br.com.catalisa.ZuPlaceApi.exception.CepFormatException;
import br.com.catalisa.ZuPlaceApi.exception.CepNullException;
import br.com.catalisa.ZuPlaceApi.model.AddressModel;
import br.com.catalisa.ZuPlaceApi.repository.AddressRepository;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;


@Service
public class AddressService {

    private static final String viaCepUrl = "https://viacep.com.br/ws/";
    private static final Gson gson = new Gson();

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    GoogleMapsService googleMapsService;

    @Autowired
    private ModelMapper modelMapper;

    public AddressResponseDto createAddres(ZipCodeRequestDto cepString) {
        try {
            logger.debug("Método findCep chamado com CEP: {}", cepString.getCep());

            logger.debug("Método removeMaskZipCode chamado");
            removeMaskZipCode(cepString.getCep());
            logger.info("Foi removido a mascara do cep com o método removeMaskZipCode");

            logger.debug("Método validZipCode chamado");
            validZipCode(cepString.getCep());
            logger.info("CEP foi validade pelo método validZipCode");


            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.of(1, MINUTES))
                    .build();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(viaCepUrl+cepString.getCep()+"/json"))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            AddressModel addressModel = gson.fromJson(httpResponse.body(), AddressModel.class);

            addressModel.setComplement(cepString.getComplement());
            addressModel.setNumberAddress(cepString.getNumberAddress());

            AddressResponseDto addressResponseDto = modelMapper.map(addressModel, AddressResponseDto.class);
            AddressModel addressModelSave = modelMapper.map(addressResponseDto, AddressModel.class);

            addressRepository.save(addressModelSave);
            logger.info("CEP {} encontrado com sucesso", cepString.getCep());

            return addressResponseDto;

        } catch (IOException | InterruptedException e) {
            logger.error("Erro ao buscar CEP: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<AddressResponseDto> findAll(){
        try{
            logger.debug("Listando todos os endereços!");
            List<AddressModel> findAddressList = addressRepository.findAll();
            List<AddressResponseDto> addressResponseDtoList = findAddressList.stream()
                    .map(addressModel -> modelMapper.map(addressModel, AddressResponseDto.class))
                    .collect(Collectors.toList());
            logger.debug("Endereços listados com sucesso!");
            return addressResponseDtoList;
        }catch (Exception e){
            throw e;
        }
    }

    public void validZipCode(String zipCode) throws CepNullException {
        if (Objects.isNull(zipCode) || zipCode.isEmpty() || zipCode.isBlank()) throw new CepNullException("O cep informado não pode ser nulo ou vazio");
        if (zipCode.length() > 9) throw new CepFormatException("CEP com números a mais");
        if (zipCode.length() < 8) throw new CepFormatException("CEP faltando números");
    }
    public String removeMaskZipCode(String zipCode){
        try {
            validZipCode(zipCode);
            return zipCode;
        } catch (CepFormatException e){
            return zipCode.replace("-", "");
        }
    }
}
