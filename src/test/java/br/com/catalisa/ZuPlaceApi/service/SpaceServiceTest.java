package br.com.catalisa.ZuPlaceApi.service;

import br.com.catalisa.ZuPlaceApi.dto.*;
import br.com.catalisa.ZuPlaceApi.exception.SpaceNotFoundException;
import br.com.catalisa.ZuPlaceApi.model.AddressModel;
import br.com.catalisa.ZuPlaceApi.model.ResourceModel;
import br.com.catalisa.ZuPlaceApi.model.SpaceModel;
import br.com.catalisa.ZuPlaceApi.model.UserModel;
import br.com.catalisa.ZuPlaceApi.repository.AddressRepository;
import br.com.catalisa.ZuPlaceApi.repository.ResourceRepository;
import br.com.catalisa.ZuPlaceApi.repository.SpaceRepository;
import br.com.catalisa.ZuPlaceApi.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SpaceServiceTest {
    private static final Long ID = 1L;
    private static final Integer INDEX = 0;
    public Long idError = 1L;
    private static final String NAME = "faculdade";
    private static final String ESPACO_NAO_ENCONTRADO = "Espaço não encontrado com o ID: " + ID;
    private final  UserModel user = new UserModel();
    private final ResourceModel resourceModel = new ResourceModel();
    private final AddressModel addressModel = new AddressModel();
    private static final double DISTANCE = 15635.635;
    @InjectMocks
    private SpaceService service;
    @Mock
    private SpaceRepository spaceRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ResourceRepository resourceRepository;
    @Mock
    private AddressService addressService;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private GoogleMapsService googleMapsService;
    @Mock
    private ModelMapper mapper;
    private SpaceModel spaceModel;
    private SpaceRequestDto spaceRequestDto;
    private SpaceResponseDto spaceResponseDto;
    private Optional<SpaceModel> optionalSpaceModel;
    private Optional<SpaceResponseDto> optionalSpaceResponseDto;
    private final UserResponseDto userResponseDto = new UserResponseDto();
    private final ResourceResponseDto resourceResponseDto = new ResourceResponseDto();
    private  final  AddressResponseDto addressResponseDto = new AddressResponseDto();
    private static final Long USER_ID = 1L;
    private static final Long RESOURSE_ID = 1L;
    private ZipCodeRequestDto zipCodeRequestDto = new ZipCodeRequestDto();

    void setUp(){
        MockitoAnnotations.openMocks(this);
        startSpace();
    }
    @Test
    @DisplayName("Me retorne uma lista de usuários quando chamar o método findAll")
    void  whenFindAllThenReturnAListOfDTO(){
        when(spaceRepository.findAll()).thenReturn(List.of(spaceModel));
        when(mapper.map(any(), SpaceResponseDto.class)).thenReturn(new SpaceResponseDto());
        List<SpaceResponseDto> response = service.findAll();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(SpaceResponseDto.class, response.get(INDEX).getClass());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(userResponseDto, response.get(INDEX).getUser());
        assertEquals(resourceResponseDto, response.get(INDEX).getResource());
        assertEquals(addressResponseDto, response.get(INDEX).getAddress());
        assertEquals(DISTANCE, response.get(INDEX).getDistance());
    }

    @Test
    @DisplayName("Lance uma exceção quando não encontrar usuários")
    void  whenFindAllThenReturnASpaceNotFoundException(){
        when(spaceRepository.findAll()).thenThrow(new SpaceNotFoundException(ID) );

        try {
            service.findAll();
        } catch(SpaceNotFoundException e){
            assertEquals(SpaceNotFoundException.class, e.getClass());
            assertEquals(ESPACO_NAO_ENCONTRADO, e.getMessage());
        }
    }

    @DisplayName("organizando a chamada das instâncias para a realizar testes:")
    private void startSpace(){
        //provavelmente vamos precisar cadastrar o horário de funcionamento e também a descrição
        spaceModel = new SpaceModel();
        spaceModel.setId(ID);
        spaceModel.setName(NAME);
        spaceModel.setUser(user);
        spaceModel.setResource(resourceModel);
        spaceModel.setAddress(addressModel);
        spaceModel.setDistance(DISTANCE);

        spaceRequestDto = new SpaceRequestDto();
        spaceRequestDto.setName(NAME);
        spaceRequestDto.setUserId(USER_ID);
        spaceRequestDto.setResourceId(RESOURSE_ID);
        spaceRequestDto.setAddress(zipCodeRequestDto);

        spaceResponseDto = new SpaceResponseDto();
        spaceResponseDto.setName(NAME);
        spaceResponseDto.setUser(userResponseDto);
        spaceResponseDto.setResource(resourceResponseDto);
        spaceResponseDto.setAddress(addressResponseDto);
        spaceResponseDto.setDistance(DISTANCE);
        
        optionalSpaceModel = Optional.of(spaceModel);
        optionalSpaceResponseDto = Optional.of(spaceResponseDto);
    }
}
