package br.com.catalisa.ZuPlaceApi.controller;

import br.com.catalisa.ZuPlaceApi.dto.ContactRequestDto;
import br.com.catalisa.ZuPlaceApi.dto.ContactResponseDto;
import br.com.catalisa.ZuPlaceApi.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@Tag(name = "Feature - Contacts")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class ContactController {
    @Autowired
    ContactService contactService;

    @GetMapping
    @Operation(summary = " : Lista todos os contatos cadastrados", method = "GET")
    public ResponseEntity<List<ContactResponseDto>> findAllContacts(){
        List<ContactResponseDto> contactResponseDtoList = contactService.findAll();
        return new ResponseEntity<>(contactResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = " : Busca um contato cadastrado por ID", method = "GET")
    public ResponseEntity<ContactResponseDto> findContactById(@PathVariable Long id){
        ContactResponseDto responseDto = contactService.findById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = " : Cadastra um novo contato", method = "POST")
    public ResponseEntity<ContactResponseDto> createContact(@RequestBody ContactRequestDto contactRequestDto){
        ContactResponseDto contactResponseDto = contactService.create(contactRequestDto);
        return new ResponseEntity<>(contactResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @Operation(summary = " : Altera um contato cadastrado por ID", method = "PUT")
    public ResponseEntity<ContactResponseDto> alterContact(@PathVariable Long id,
                                                           @RequestBody ContactRequestDto contactRequestDto){
        ContactResponseDto contactResponseDto = contactService.update(id, contactRequestDto);
        return new ResponseEntity<>(contactResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = " : Deleta um contato cadastrado por ID", method = "DELETE")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id){
        contactService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
