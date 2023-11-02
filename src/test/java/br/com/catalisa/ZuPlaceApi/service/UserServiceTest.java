package br.com.catalisa.ZuPlaceApi.service;

import br.com.catalisa.ZuPlaceApi.dto.SpaceResponseDto;
import br.com.catalisa.ZuPlaceApi.dto.UserRequestDto;
import br.com.catalisa.ZuPlaceApi.dto.UserResponseDto;
import br.com.catalisa.ZuPlaceApi.enums.PersonType;
import br.com.catalisa.ZuPlaceApi.exception.ResourseNotFoundException;
import br.com.catalisa.ZuPlaceApi.model.SpaceModel;
import br.com.catalisa.ZuPlaceApi.model.UserModel;
import br.com.catalisa.ZuPlaceApi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    private static Long ID = 1L;
    private  static final Integer INDEX = 0;
    private static final String NAME = "athos";
    private static final String EMAIL = "athossilva@gmail.com";
private static final String PASSWORD = "1234";
private static final PersonType personType = PersonType.PHYSICAL_PERSON;
private static final String PHONE = "021996705392";
private static final  String DOCUMENT_TYPE = "01613587401";
private  static  final  List<SpaceModel> SPACES = new ArrayList<>();
private static final String USUARIO_NAO_ENCONTRADO = "usuário não encontrado";
private static final String USUARIO_NAO_CADASTRADO = "usuário não cadastrado";

    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;

    @Mock
    private UserModel userModel;
    private UserRequestDto userRequestDto;

    @Mock
    private UserResponseDto responseDto;

    private Optional<UserModel> optionalUserModel;

    @BeforeEach
    void  setUp(){
        MockitoAnnotations.openMocks(this);
    startUser();
    }



    @Test
    @DisplayName("quando buscar um usuário por id, retorne uma instância de usuário.")
    void  whenFindByIdThenReturnAUserInstance(){
        when(repository.findById(anyLong())).thenReturn(optionalUserModel);
        UserModel responce = service.findById(ID);

        assertNotNull(responce);

        assertEquals(UserModel.class, responce.getClass());
        assertEquals(ID, responce.getId());
        assertEquals(NAME, responce.getName());
        assertEquals(EMAIL, responce.getEmail());
        assertEquals(PASSWORD, responce.getPassword());
        assertEquals(personType, responce.getPersonType());
        assertEquals(PHONE, responce.getPhone());
        assertEquals(DOCUMENT_TYPE, responce.getDocumentType());
        assertEquals(SPACES, responce.getSpaces());
    }
    @Test
    @DisplayName("quando buscar por um id de usuário e não encontrar lance uma exceção")
    void   whenFindByIdThenReturnResourseNotFoundException(){
        when(repository.findById(anyLong()))
                .thenThrow(new ResourseNotFoundException(USUARIO_NAO_ENCONTRADO));
        try {
            service.findById(ID);

        } catch (Exception e){
    assertEquals(ResourseNotFoundException.class, e.getClass());
    assertEquals(USUARIO_NAO_ENCONTRADO, e.getMessage());

        }
    }
    @Test
    @DisplayName("quando buscar por usuários então me retorne uma lista de usuários")
    void whenFindAllThenReturnAListOfUsers(){
        when(repository.findAll()).thenReturn(List.of(userModel));
        List<UserModel> responce = service.findAll();
        assertNotNull(responce);
        assertEquals(1, responce.size());
        assertEquals(UserModel.class, responce.get(INDEX).getClass());
        assertEquals(ID, responce.get(INDEX).getId());
        assertEquals(NAME, responce.get(INDEX).getName());
        assertEquals(EMAIL, responce.get(INDEX).getEmail());
        assertEquals(PASSWORD, responce.get(INDEX).getPassword());
        assertEquals(personType, responce.get(INDEX).getPersonType());
        assertEquals(PHONE, responce.get(INDEX).getPhone());
        assertEquals(DOCUMENT_TYPE, responce.get(INDEX).getDocumentType());
        assertEquals(SPACES, responce.get(INDEX).getSpaces());
    }

//    @Test
//    @DisplayName("quando cadastrar um uduário então retorne sucesso")
//    void whenCreateThenReturnASuccess(){
//        when(mapper.map(userRequestDto, UserModel.class)).thenReturn(userModel);
//        when(repository.save(any(UserModel.class))).thenReturn(userModel);
//        when(mapper.map(userModel, UserResponseDto.class)).thenReturn(responseDto);
//
//         UserResponseDto response = service.create(userRequestDto);
//    assertNotNull(response);
//
//        assertEquals(UserResponseDto.class, response.getClass());
//        assertEquals(ID, response.getId());
//        assertEquals(NAME, response.getName());
//        assertEquals(EMAIL, response.getEmail());
//        assertEquals(PASSWORD, response.getPassword());
//        assertEquals(personType, response.getPersonType());
//        assertEquals(PHONE, response.getPhone());
//        assertEquals(DOCUMENT_TYPE, response.getDocumentType());
//
//    }

    @Test
    @DisplayName("Quando cadastrar um usuário e não for encontrado então retorne uma exceção")
    void whenCreateThenReturnResourseNotFoundException() {
        when(repository.save(any())).thenThrow(new ResourseNotFoundException(USUARIO_NAO_CADASTRADO));
        try {
            service.create(userRequestDto);
        } catch (Exception e) {
            assertEquals(ResourseNotFoundException.class, e.getClass());
            assertEquals(USUARIO_NAO_CADASTRADO, e.getMessage());
        }
    }


    @Test
    @DisplayName("Quando atualizar então retorne sucesso")
    void whenUpdateThenReturnSucess(){
        when(repository.save(any())).thenReturn(userModel);
        when(repository.findById(anyLong())).thenReturn(optionalUserModel);
        UserModel response = service.update(ID, responseDto);
            assertNotNull(response);
            assertEquals(UserModel.class, response.getClass());
            assertEquals(ID, response.getId());
            assertEquals(NAME, response.getName());
            assertEquals(EMAIL, response.getEmail());
            assertEquals(PASSWORD, response.getPassword());
            assertEquals(personType, response.getPersonType());
            assertEquals(PHONE, response.getPhone());
            assertEquals(DOCUMENT_TYPE, response.getDocumentType());
            assertEquals(SPACES, response.getSpaces());
    }

    @Test
    @DisplayName("quando atualizar um id que não existe, retorne uma exceção")
    void whenUpdateThenReturnResourseNotFoundException(){
        when(repository.findById(anyLong())).thenThrow(new ResourseNotFoundException(USUARIO_NAO_CADASTRADO));
        try {
            service.update(ID, responseDto);
        } catch (Exception e){
            assertEquals(ResourseNotFoundException.class, e.getClass());
            assertEquals(USUARIO_NAO_CADASTRADO, e.getMessage());
        }
    }

    @Test
    @DisplayName("quando deletar um usuário me retorna sucesso")
    void deleteSuccess(){
        when(repository.findById(anyLong())).thenReturn(optionalUserModel);
        doNothing().when(repository).deleteById(anyLong());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("quando deletar um usuário que não existe então me retorne uma exceção")
    void whenDeleteByIdThenReturnResourseNotFoundExcepetion(){
        when(repository.findById(anyLong()))
                .thenThrow(new ResourseNotFoundException(USUARIO_NAO_ENCONTRADO));
        try {
            service.delete(ID);
        } catch (Exception e){
    assertEquals(ResourseNotFoundException.class, e.getClass());
       assertEquals(USUARIO_NAO_ENCONTRADO, e.getMessage());
        }
    }
@DisplayName("método que configura a instanciação de objetos para a realização dos testes")
    private void startUser(){
    userModel = new UserModel(ID, NAME, EMAIL, PASSWORD, personType, PHONE, DOCUMENT_TYPE, SPACES);
    userRequestDto = new UserRequestDto(NAME, EMAIL, PASSWORD, personType, PHONE, DOCUMENT_TYPE);
                    responseDto = new UserResponseDto(ID, NAME, EMAIL, PASSWORD, personType, PHONE, DOCUMENT_TYPE);
                    optionalUserModel = Optional.of(new UserModel(ID, NAME, EMAIL, PASSWORD, personType, PHONE, DOCUMENT_TYPE, SPACES));
}
}
