package br.com.catalisa.ZuPlaceApi.controller;

import br.com.catalisa.ZuPlaceApi.dto.UserRequestDto;
import br.com.catalisa.ZuPlaceApi.dto.UserResponseDto;
import br.com.catalisa.ZuPlaceApi.enums.PersonType;
import br.com.catalisa.ZuPlaceApi.model.SpaceModel;
import br.com.catalisa.ZuPlaceApi.model.UserModel;
import br.com.catalisa.ZuPlaceApi.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {
    private static final Long ID = 1L;
    private static final Integer INDEX = 0;
    private static final String NAME = "athos";
    private static final String EMAIL = "athossilva@gmail.com";
    private static final String PASSWORD = "1234";
    private static final PersonType personType = PersonType.PHYSICAL_PERSON;
    private static final String PHONE = "021996705392";
    private static final String DOCUMENT_TYPE = "01613587401";
    private static final List<SpaceModel> SPACES = new ArrayList<>();
    private UserModel userModel = new UserModel();
    private UserResponseDto responseDto = new UserResponseDto();
    private UserRequestDto requestDto = new UserRequestDto();
    @InjectMocks
    private UserController controller;
    @Mock
    private UserService service;
    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    @DisplayName("quando chamar o método findAll, me retorne uma lista de usuários")
    void whenFindAllUsersThenReturnAListOfDTO() {
        when(service.findAll()).thenReturn(List.of(userModel));
        when(mapper.map(any(), any())).thenReturn(responseDto);
        ResponseEntity<List<UserResponseDto>> response = controller.findAllUsers();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserResponseDto.class, response.getBody().get(INDEX).getClass());
        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
        assertEquals(personType, response.getBody().get(INDEX).getPersonType());
        assertEquals(PHONE, response.getBody().get(INDEX).getPhone());
        assertEquals(DOCUMENT_TYPE, response.getBody().get(INDEX).getDocumentType());
    }

    @Test
    @DisplayName("quando buscar um usuário específico por id, me retorne uma instância de usuário")
    void whenFinByIdThenReturnAUserInstance() {
        when(service.findById(anyLong())).thenReturn(userModel);
        when(mapper.map(any(), any())).thenReturn(responseDto);
        ResponseEntity<UserResponseDto> response = controller.findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserResponseDto.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
        assertEquals(personType, response.getBody().getPersonType());
        assertEquals(PHONE, response.getBody().getPhone());
        assertEquals(DOCUMENT_TYPE, response.getBody().getDocumentType());
    }

    @Test
    @DisplayName("quando criar um usuário, me retorne um status code created")
    void whenCreateThenReturnCreated() {
        when(service.create(any())).thenReturn(responseDto);
        ResponseEntity<UserResponseDto> response = controller.createUser(requestDto);
assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    @DisplayName("quando atualizar um usuário por id, me retorne sucesso")
    void whenUpdateThenReturnSuccess() {
        when(service.update(anyLong(), any())).thenReturn(userModel);
        when(mapper.map(any(), any())).thenReturn(responseDto);
        ResponseEntity<UserResponseDto> response = controller.update(ID, responseDto);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserResponseDto.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
        assertEquals(personType, response.getBody().getPersonType());
        assertEquals(PHONE, response.getBody().getPhone());
        assertEquals(DOCUMENT_TYPE, response.getBody().getDocumentType());

    }

    @Test
    @DisplayName("quando deletar um usuário por id, me retorne sucesso")
    void whenDeleteThenReturnSuccess() {
        doNothing().when(service).delete(anyLong());
        ResponseEntity<Void> response = controller.delete(ID);
        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyLong());
    }
@DisplayName("método que configura a instanciação dos objetos para realizar os testes")
    private void startUser() {
        userModel = new UserModel(ID, NAME, EMAIL, PASSWORD, personType, PHONE, DOCUMENT_TYPE, SPACES);
        responseDto = new UserResponseDto(ID, NAME, EMAIL, PASSWORD, personType, PHONE, DOCUMENT_TYPE);
   requestDto = new UserRequestDto(NAME, EMAIL, PASSWORD, personType, PHONE, DOCUMENT_TYPE);
    }
}
