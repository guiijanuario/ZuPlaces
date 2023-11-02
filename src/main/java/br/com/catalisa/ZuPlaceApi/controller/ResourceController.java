package br.com.catalisa.ZuPlaceApi.controller;

import br.com.catalisa.ZuPlaceApi.dto.ResourceRequestDto;
import br.com.catalisa.ZuPlaceApi.dto.ResourceResponseDto;
import br.com.catalisa.ZuPlaceApi.model.ResourceModel;
import br.com.catalisa.ZuPlaceApi.model.UserModel;
import br.com.catalisa.ZuPlaceApi.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@Tag(name = "Feature - Resources")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    ResourceService resourceService;

    @GetMapping
    @Operation(summary = " : Lista todos os recursos cadastrados", method = "GET")
    public ResponseEntity<List<ResourceResponseDto>> findAllResources(){
        logger.debug("Método findAllResources() chamado, Bateu no Controller");
        List<ResourceResponseDto> resourceResponseDtoList = resourceService.findAll();
        logger.info("Total de recursos encontrados: {}", resourceResponseDtoList.size() + " Bateu no Controller");
        return new ResponseEntity<>(resourceResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = " : Busca um recurso cadastrado por ID", method = "GET")
    public ResponseEntity<ResourceResponseDto> findResourceById(@PathVariable Long id){
        logger.debug("Método findResourceById() chamado Bateu no Controller");
        ResourceResponseDto responseDto = resourceService.findById(id);
        logger.info("Recurso com ID: {}", id  + " Encontrado com sucesso! Bateu no Controller");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResourceModel>> searchRecources(@RequestParam("nome") String nome) {
        List<ResourceModel> resourceModels = resourceService.searchResourceByName(nome);
        return new ResponseEntity<>(resourceModels, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = " : Cadastra um novo recurso", method = "POST")
    public ResponseEntity<ResourceResponseDto> registerResource(@RequestBody ResourceRequestDto resourceRequestDto){
        logger.debug("Método registerResource() chamado");
        ResourceResponseDto resourceResponseDto = resourceService.create(resourceRequestDto);
        logger.info("Recurso {} ,registrado com sucesso: ", resourceResponseDto.getName() + " Bateu no controller");
        return new ResponseEntity<>(resourceResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = " : Altera um recurso cadastrado por ID", method = "PUT")
    public ResponseEntity<ResourceResponseDto> alterResource(@PathVariable Long id,
                                                             @RequestBody ResourceRequestDto resourceRequestDto){
        logger.debug("Método alterResource() chamado");
        ResourceResponseDto resourceResponseDto = resourceService.update(id, resourceRequestDto);
        logger.info("Recurso com ID: {}", id  + " Alterado com sucesso! Bateu no Controller");
        return new ResponseEntity<>(resourceResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = " : Deleta um recurso cadastrado por ID", method = "DELETE")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id){
        logger.debug("Método deleteResource() chamado, Bateu no Controller" );
        resourceService.delete(id);
        logger.info("Recurso com ID: {}", id  + " Deletado com sucesso!, Bateu no Controller");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
