package br.com.catalisa.ZuPlaceApi.controller;
import br.com.catalisa.ZuPlaceApi.dto.ContactRequestDto;
import br.com.catalisa.ZuPlaceApi.dto.ContactResponseDto;
import br.com.catalisa.ZuPlaceApi.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(ContactController.class)
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFindAllContacts() throws Exception {
        List<ContactResponseDto> contactResponseDtos = new ArrayList<>();
        ContactResponseDto contact1 = new ContactResponseDto();
        ContactResponseDto contact2 = new ContactResponseDto();
        contact1.setName("Nome1");
        contact2.setName("Nome2");
        contactResponseDtos.add(contact1);
        contactResponseDtos.add(contact2);

        when(contactService.findAll()).thenReturn(contactResponseDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Nome1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value("Nome2"));
    }

    @Test
    void testFindContactById() throws Exception {
        Long contactId = 1L;
        ContactResponseDto contactDto = new ContactResponseDto();
        contactDto.setName("Nome1");

        when(contactService.findById(contactId)).thenReturn(contactDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/contacts/{id}", contactId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome1"));
    }

    @Test
    void testCreateContact() throws Exception {
        ContactRequestDto requestDto = new ContactRequestDto();
        requestDto.setName("Nome1");

        ContactResponseDto responseDto = new ContactResponseDto();
        responseDto.setName("Nome1");
        when(contactService.create(requestDto)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome1"));
    }

    @Test
    void testAlterContact() throws Exception {
        ContactRequestDto requestDto = new ContactRequestDto();
        requestDto.setName("Nome1");

        Long contactId = 1L;
        ContactResponseDto responseDto = new ContactResponseDto();
        responseDto.setName("Nome1");
        when(contactService.update(contactId, requestDto)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/contacts/{id}", contactId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Nome1"));
    }

    @Test
    void testDeleteContact() throws Exception {
        Long contactId = 1L;
        Mockito.doNothing().when(contactService).delete(contactId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/contacts/{id}", contactId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}


