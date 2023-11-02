package br.com.catalisa.ZuPlaceApi.service;

import br.com.catalisa.ZuPlaceApi.controller.ResourceController;
import br.com.catalisa.ZuPlaceApi.dto.ResourceRequestDto;
import br.com.catalisa.ZuPlaceApi.dto.ResourceResponseDto;
import br.com.catalisa.ZuPlaceApi.model.ResourceModel;
import br.com.catalisa.ZuPlaceApi.model.UserModel;
import br.com.catalisa.ZuPlaceApi.repository.ResourceRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<ResourceResponseDto> findAll(){
        try {
            logger.debug("Método findAll() no service chamado.");
            List<ResourceModel> resourceModelList = resourceRepository.findAll();
            List<ResourceResponseDto> resourceResponseDtoList = resourceModelList.stream()
                    .map(resourceModel -> modelMapper.map(resourceModel, ResourceResponseDto.class))
                    .collect(Collectors.toList());
            logger.info("Número de recursos encontrados no service: {}", resourceResponseDtoList.size());
            return resourceResponseDtoList;
        } catch (Exception e){
            throw e;
        }
    }

    public List<ResourceModel> searchResourceByName(String nome) {
        return resourceRepository.findByNameContainingIgnoreCase(nome);
    }


    public ResourceResponseDto findById(Long id) {
        try {
            logger.debug("Método findById() no service chamado.");
            Optional<ResourceModel> optionalResourceModel = resourceRepository.findById(id);

            if (optionalResourceModel.isPresent()) {
                ResourceModel resourceModel = optionalResourceModel.get();
                ResourceResponseDto resourceResponseDto = modelMapper.map(resourceModel, ResourceResponseDto.class);
                logger.info("Recurso com ID: {}", id + " Encontrado pelo service");
                return resourceResponseDto;
            } else {
                return null;
            }
        } catch (Exception e){
            throw e;
        }
    }

    public ResourceResponseDto create(ResourceRequestDto resourceRequestDto){
       try {
           logger.debug("Método create() no service chamado.");
           ResourceModel resourceModel = modelMapper.map(resourceRequestDto, ResourceModel.class);
           resourceModel = resourceRepository.save(resourceModel);
           ResourceResponseDto resourceResponseDto = modelMapper.map(resourceModel, ResourceResponseDto.class);
           logger.info("Recurso {}", resourceResponseDto.getName() + " criado pelo service com sucesso!");
           return resourceResponseDto;
       } catch (Exception e){
           throw e;
       }
    }

    public ResourceResponseDto update(Long id, ResourceRequestDto updatedResourceDto) {
        try {
            logger.debug("Método update() no service chamado.");
            Optional<ResourceModel> optionalResourceModel = resourceRepository.findById(id);

            if (optionalResourceModel.isPresent()) {
                ResourceModel resourceModel = optionalResourceModel.get();
                modelMapper.map(updatedResourceDto, resourceModel);

                resourceModel = resourceRepository.save(resourceModel);

                ResourceResponseDto resourceResponseDto = modelMapper.map(resourceModel, ResourceResponseDto.class);
                logger.info("Recurso {}", resourceResponseDto.getName() + " alterado pelo service com sucesso!");
                return resourceResponseDto;
            } else {
                return null;
            }
        } catch (Exception e){
            throw e;
        }
    }

    public void delete(Long id){
        logger.debug("Método delete() no service chamado.");
        logger.info("Recurso com id {}", id + " deletado pelo service com sucesso!");
        resourceRepository.deleteById(id);
    }
}
