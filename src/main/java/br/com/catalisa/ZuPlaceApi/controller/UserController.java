package br.com.catalisa.ZuPlaceApi.controller;

import br.com.catalisa.ZuPlaceApi.dto.LoginRequestDto;
import br.com.catalisa.ZuPlaceApi.dto.UserRequestDto;
import br.com.catalisa.ZuPlaceApi.dto.UserResponseDto;
import br.com.catalisa.ZuPlaceApi.model.UserModel;
import br.com.catalisa.ZuPlaceApi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "User Feature")
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class UserController {
    public static final String ID = "/{id}";

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    @Operation(summary = "lista todos os usuarios cadastrados", method = "GET")
    public ResponseEntity<List<UserResponseDto>> findAllUsers(){
        logger.debug("Método findAllUsers() chamado");
        List<UserResponseDto> userResponseDtoList = service.findAll().stream()
                .map(u -> mapper.map(u, UserResponseDto.class))
                .collect(Collectors.toList());
        logger.info("Total de usuários encontrados: {}", userResponseDtoList.size());
        return ResponseEntity.ok().body(userResponseDtoList);
    }

    @GetMapping(value = ID)
    @Operation(summary = "busca um usuario cadastrado por id", method = "GET")
    public ResponseEntity<UserResponseDto> findById (@PathVariable Long id){
        logger.debug("Método findById() chamado");

        logger.info("Encontrado usuário com ID: {}", id + " bateu no controller");
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserResponseDto.class));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserModel>> searchUsers(@RequestParam("nome") String nome) {
        List<UserModel> users = service.searchUsersByName(nome);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = " : Cadastra um novo usuario", method = "POST")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto){
        logger.debug("Método createUser() chamado, Bateu no Controller");
        UserResponseDto userResponseDto = service.create(userRequestDto);
        logger.info("Usuário {} ,registrado com sucesso: ", userResponseDto.getName() + " Bateu no controller.");
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> logIn(@RequestBody LoginRequestDto loginRequestDto){
        UserModel userExistente = service.findByEmail(loginRequestDto.getEmail());
        if (userExistente != null && userExistente.getPassword().equals(loginRequestDto.getPassword())) {
            return ResponseEntity.ok("Parabéns, você está logado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas. Verifique seu e-mail e senha.");
        }
    }

    @DeleteMapping(value = ID)
    @Operation(summary = "deleta um usuario", method = "DELETE")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        logger.debug("Método delete() chamado, Bateu no Controller");
        service.delete(id);
        logger.info("Usuário com id {} ,deletado com sucesso: ", id + " Bateu no controller.");
        return ResponseEntity.noContent().build();
    }
    @PutMapping(value = ID)
    @Operation(summary = "atualiza um usuario existente", method = "PUT")
    public ResponseEntity<UserResponseDto> update( @PathVariable Long id, @RequestBody UserResponseDto userResponseDto){
        logger.debug("Método delete() chamado, Bateu no Controller");
        UserModel updateUser = service.update(id, userResponseDto);
        logger.info("Usuário com id {} ,alterado com sucesso: ", id + " Bateu no controller.");
        return ResponseEntity.ok().body(mapper.map(updateUser, UserResponseDto.class));
    }
}
