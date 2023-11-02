package br.com.catalisa.ZuPlaceApi.service;


import br.com.catalisa.ZuPlaceApi.dto.AddressResponseDto;
import br.com.catalisa.ZuPlaceApi.dto.ZipCodeRequestDto;
import br.com.catalisa.ZuPlaceApi.model.AddressModel;
import br.com.catalisa.ZuPlaceApi.repository.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private HttpClient httpClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAddress() {
        HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(httpResponse.body()).thenReturn("{\"cep\": \"25645-090\", \"logradouro\": \"Rua Teste\", \"bairro\": \"Bairro Teste\", \"uf\": \"MG\"}");

        ZipCodeRequestDto zipCodeRequestDto = new ZipCodeRequestDto();
        zipCodeRequestDto.setCep("25645-090");

        AddressModel addressModel = new AddressModel();
        addressModel.setCep("25645-090");
        addressModel.setLogradouro("Rua Teste");
        addressModel.setBairro("Bairro Teste");
        addressModel.setUf("MG");

        ModelMapper modelMapper = new ModelMapper();
        AddressResponseDto addressResponseDto = modelMapper.map(addressModel, AddressResponseDto.class);

        addressService.createAddres(zipCodeRequestDto);

        assertEquals("25645-090", addressResponseDto.getCep());
    }

    @Test
    public void testFindAllAddresses() {
        AddressRepository addressRepositoryMock = Mockito.mock(AddressRepository.class);

        List<AddressModel> addressModels = new ArrayList<>();
        AddressModel addressModel1 = new AddressModel();
        addressModel1.setId(1L);
        addressModel1.setCep("25645-090");
        addressModel1.setLogradouro("Rua Teste");
        addressModel1.setBairro("Bairro Teste");
        addressModel1.setUf("MG");
        addressModels.add(addressModel1);

        Mockito.when(addressRepositoryMock.findAll()).thenReturn(addressModels);

        List<AddressResponseDto> addressResponseDtoList = addressService.findAll();

        ModelMapper modelMapper = new ModelMapper();
        List<AddressResponseDto> addressResponseDtoListConverted = modelMapper.map(addressModels, new TypeToken<List<AddressResponseDto>>() {}.getType());

        assertEquals(1, addressResponseDtoListConverted.size());
        assertEquals("25645-090", addressResponseDtoListConverted.get(0).getCep());
    }
}