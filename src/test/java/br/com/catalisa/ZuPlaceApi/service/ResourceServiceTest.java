package br.com.catalisa.ZuPlaceApi.service;

import br.com.catalisa.ZuPlaceApi.dto.ResourceRequestDto;
import br.com.catalisa.ZuPlaceApi.dto.ResourceResponseDto;
import br.com.catalisa.ZuPlaceApi.model.ResourceModel;
import br.com.catalisa.ZuPlaceApi.repository.ResourceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ResourceServiceTest {

    @InjectMocks
    private ResourceService resourceService;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private ModelMapper modelMapper;

    public ResourceServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        ResourceModel resource1 = new ResourceModel(1L, "Resource 1", null);
        ResourceModel resource2 = new ResourceModel(2L, "Resource 2", null);

        when(resourceRepository.findAll()).thenReturn(Arrays.asList(resource1, resource2));

        when(modelMapper.map(resource1, ResourceResponseDto.class)).thenReturn(new ResourceResponseDto(1L, "Resource 1"));
        when(modelMapper.map(resource2, ResourceResponseDto.class)).thenReturn(new ResourceResponseDto(2L,"Resource 2"));

        List<ResourceResponseDto> resourceResponseDtoList = resourceService.findAll();
        assertEquals(2, resourceResponseDtoList.size());

        for (ResourceResponseDto resourceResponseDto : resourceResponseDtoList) {
            assertNotNull(resourceResponseDto);
            assertNotNull(resourceResponseDto.getName());
        }
    }

    @Test
    public void testFindResourceById() {
        Long resourceId = 1L;

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.empty());

        ResourceResponseDto resourceResponseDto = resourceService.findById(resourceId);

        assertNull(resourceResponseDto);
    }

    @Test
    public void testCreateResource() {
        ResourceRequestDto requestDto = new ResourceRequestDto();
        requestDto.setName("Exemplo de recurso");

        ResourceModel resourceModel = new ResourceModel();
        resourceModel.setId(1L);
        resourceModel.setName("Exemplo de recurso");

        ResourceResponseDto responseDto = new ResourceResponseDto();
        responseDto.setName("Exemplo de recurso");

        when(modelMapper.map(requestDto, ResourceModel.class)).thenReturn(resourceModel);
        when(modelMapper.map(resourceModel, ResourceResponseDto.class)).thenReturn(responseDto);

        when(resourceRepository.save(any(ResourceModel.class))).thenReturn(resourceModel);

        ResourceResponseDto createdResource = resourceService.create(requestDto);

        verify(resourceRepository).save(resourceModel);

        assertEquals(responseDto, createdResource);
    }

    @Test
    public void testUpdateResource() {
        Long resourceId = 1L;

        ResourceRequestDto updatedResourceRequestDto = new ResourceRequestDto();
        updatedResourceRequestDto.setName("Novo Nome");

        ResourceResponseDto updatedResourceResponseDto = new ResourceResponseDto();
        updatedResourceResponseDto.setName("Nome Atualizado");

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(new ResourceModel()));
        when(modelMapper.map(updatedResourceRequestDto, ResourceModel.class)).thenReturn(new ResourceModel());
        when(resourceRepository.save(any(ResourceModel.class))).thenReturn(new ResourceModel());
        when(modelMapper.map(any(ResourceModel.class), eq(ResourceResponseDto.class))).thenReturn(updatedResourceResponseDto);

        ResourceResponseDto result = resourceService.update(resourceId, updatedResourceRequestDto);

        assertNotNull(result);
        assertEquals("Nome Atualizado", result.getName());
    }

    @Test
    public void testDeleteResource() {
        Long resourceId = 1L;

        resourceService.delete(resourceId);

        verify(resourceRepository, times(1)).deleteById(resourceId);
    }
}
