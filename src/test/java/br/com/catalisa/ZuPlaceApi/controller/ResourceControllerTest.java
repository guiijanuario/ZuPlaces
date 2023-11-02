package br.com.catalisa.ZuPlaceApi.controller;

import br.com.catalisa.ZuPlaceApi.dto.ResourceRequestDto;
import br.com.catalisa.ZuPlaceApi.dto.ResourceResponseDto;
import br.com.catalisa.ZuPlaceApi.model.ResourceModel;
import br.com.catalisa.ZuPlaceApi.service.ResourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResourceController.class)
public class ResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceController resourceController;

    @MockBean
    private ResourceService resourceService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResourceModel resourceModel;

    @Test
    void testFindAllResources() throws Exception {
        List<ResourceResponseDto> resourceDtoList = new ArrayList<>();
        ResourceResponseDto dto1 = new ResourceResponseDto(1L,"Internet");
        ResourceResponseDto dto2 = new ResourceResponseDto(2L,"Computador");
        resourceDtoList.add(dto1);
        resourceDtoList.add(dto2);

        when(resourceService.findAll()).thenReturn(resourceDtoList);

        mockMvc.perform(get("/api/resources"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").value("Internet"))
                .andExpect(jsonPath("$[1].nome").value("Computador"))
                .andDo(print());
    }

    @Test
    void testFindResourceById() throws Exception {
        Long resourceId = 1L;
        ResourceResponseDto resourceDto = new ResourceResponseDto(1L,"Sample Resource");

        when(resourceService.findById(resourceId)).thenReturn(resourceDto);

        ResultActions resultActions = mockMvc.perform(get("/api/resources/{id}", resourceId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Sample Resource"));

        verify(resourceService, times(1)).findById(resourceId);

        verifyNoMoreInteractions(resourceService);
    }

    @Test
    void testCreateResource() throws Exception {
        ResourceRequestDto resourceRequestDto = new ResourceRequestDto("Sample Resource");

        String jsonContent = objectMapper.writeValueAsString(resourceRequestDto);

        ResourceResponseDto responseDto = new ResourceResponseDto(1L,"Sample Resource");
        when(resourceService.create(any(ResourceRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/resources")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Sample Resource"));

        verify(resourceService, times(1)).create(any(ResourceRequestDto.class));
    }

    @Test
    void testAlterResource() throws Exception {
        ResourceRequestDto updatedResourceDto = new ResourceRequestDto("Updated Resource");

        String jsonContent = objectMapper.writeValueAsString(updatedResourceDto);

        ResourceResponseDto responseDto = new ResourceResponseDto(1L,"Updated Resource");
        when(resourceService.update(any(Long.class), any(ResourceRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/resources/{id}", 1L)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Updated Resource"));

        verify(resourceService, times(1)).update(eq(1L), any(ResourceRequestDto.class));
    }

    @Test
    public void deleteResource() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/resources/{id}", id))
                .andExpect(status().isNoContent());

        verify(resourceService, times(1)).delete(id);
    }
}
