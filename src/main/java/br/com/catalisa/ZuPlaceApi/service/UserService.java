package br.com.catalisa.ZuPlaceApi.service;

import br.com.catalisa.ZuPlaceApi.controller.UserController;
import br.com.catalisa.ZuPlaceApi.dto.UserRequestDto;
import br.com.catalisa.ZuPlaceApi.dto.UserResponseDto;
import br.com.catalisa.ZuPlaceApi.exception.ResourseNotFoundException;
import br.com.catalisa.ZuPlaceApi.model.UserModel;
import br.com.catalisa.ZuPlaceApi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository repository;
    @Autowired
    private ModelMapper mapper;

    public List<UserModel> findAll() {
        return repository.findAll();
    }

    public UserModel findById(Long id) {
        logger.debug("Método findById() chamado");
        Optional<UserModel> user = repository.findById(id);
        return user.orElseThrow(() -> new ResourseNotFoundException("usuário não encontrado"));
    }

    public UserResponseDto create(UserRequestDto userRequestDto) {
        try {
            UserModel userModel = mapper.map(userRequestDto, UserModel.class);
            repository.save(userModel);
            UserResponseDto responseDto = mapper.map(userModel, UserResponseDto.class);
            return responseDto;
        } catch (Exception e){
            System.out.println("erro ao criar usuário: " + e.getMessage());
            throw new ResourseNotFoundException("usuário não cadastrado");
        }
    }

    public List<UserModel> searchUsersByName(String nome) {
        return repository.findByNameContainingIgnoreCase(nome);
    }

    public UserModel findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public UserModel update(Long id, UserResponseDto userResponseDto) {
        UserModel updatedUser  = findById(id);
        if (updatedUser == null){
            throw  new ResourseNotFoundException("Usuário não encontrado");
        }
        updatedUser.setName(userResponseDto.getName());
        updatedUser.setEmail(userResponseDto.getEmail());
        updatedUser.setPassword(userResponseDto.getPassword());
        updatedUser.setPersonType(userResponseDto.getPersonType());
        updatedUser.setPhone(userResponseDto.getPhone());
        updatedUser.setDocumentType(userResponseDto.getDocumentType());
        return  repository.save(updatedUser);
    }

    public void delete(Long id) {
        UserModel validateId = findById(id);
        if (validateId != null) {
            repository.deleteById(validateId.getId());
        } else {
            throw new ResourseNotFoundException("usuário não encontrado");
        }
    }

}

