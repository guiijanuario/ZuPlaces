package br.com.catalisa.ZuPlaceApi.service;

import br.com.catalisa.ZuPlaceApi.dto.*;
import br.com.catalisa.ZuPlaceApi.exception.ResourceNotFoudException;
import br.com.catalisa.ZuPlaceApi.exception.SpaceNotFoundException;
import br.com.catalisa.ZuPlaceApi.exception.UserNotFoudException;
import br.com.catalisa.ZuPlaceApi.model.AddressModel;
import br.com.catalisa.ZuPlaceApi.model.ResourceModel;
import br.com.catalisa.ZuPlaceApi.model.SpaceModel;
import br.com.catalisa.ZuPlaceApi.model.UserModel;
import br.com.catalisa.ZuPlaceApi.repository.AddressRepository;
import br.com.catalisa.ZuPlaceApi.repository.ResourceRepository;
import br.com.catalisa.ZuPlaceApi.repository.SpaceRepository;
import br.com.catalisa.ZuPlaceApi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpaceService {

    private static final Logger logger = LoggerFactory.getLogger(SpaceService.class);

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    GoogleMapsService googleMapsService;

    @Autowired
    ModelMapper modelMapper;

    public List<SpaceResponseDto> findAll(){
        try {
            logger.debug("Listando todos os espaços.");

            List<SpaceModel> spaceModelList = spaceRepository.findAll();
            List<SpaceResponseDto> spaceResponseDto = spaceModelList.stream()
                    .map(spaceModel -> modelMapper.map(spaceModel, SpaceResponseDto.class))
                    .collect(Collectors.toList());

            logger.info("Espaços listados com sucesso.");
            return spaceResponseDto;
        } catch (SpaceNotFoundException e) {
            logger.error("Erro ao listar os espaços, não existe espaços cadastrados.");
            throw e;
        } catch (Exception e){
            logger.error("Erro ao listar espaços.", e);
            throw e;
        }
    }

    public SpaceResponseDto findById(Long spaceId) {
        try {
            logger.debug("Buscando espaço pelo ID: {}", spaceId);

            return spaceRepository.findById(spaceId)
                    .map(spaceModel -> modelMapper.map(spaceModel, SpaceResponseDto.class))
                    .orElseThrow(() -> {
                        logger.error("Espaço com ID {} não encontrado.", spaceId);
                        return new SpaceNotFoundException(spaceId);
                    });
        } catch (SpaceNotFoundException e) {
            logger.error("Espaço com ID {} não encontrado.", spaceId);
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao buscar Espaço pelo ID: {}", spaceId, e);
            throw e;
        }
    }

    public SpaceResponseDto create(SpaceRequestDto spaceRequestDto) {
        try {
            logger.debug("Criando um novo espaço");
            logger.debug("Buscando User");
            logger.info("Usuário com o ID: {} , encontrado",spaceRequestDto.getUserId());
            UserModel userModel = userRepository.findById(spaceRequestDto.getUserId())
                    .orElseThrow(() -> new UserNotFoudException(spaceRequestDto.getUserId()));

            logger.debug("Buscando Recurso");
            logger.info("Recurso com o ID: {} , encontrado",spaceRequestDto.getUserId());
            ResourceModel resourceModel = resourceRepository.findById(spaceRequestDto.getResourceId())
                    .orElseThrow(() -> new ResourceNotFoudException(spaceRequestDto.getResourceId()));

            logger.debug("Instanciando os dados para o Address");

            ZipCodeRequestDto zipCodeRequestDto = new ZipCodeRequestDto();
            zipCodeRequestDto.setCep(spaceRequestDto.getAddress().getCep());
            zipCodeRequestDto.setComplement(spaceRequestDto.getAddress().getComplement());
            zipCodeRequestDto.setNumberAddress(spaceRequestDto.getAddress().getNumberAddress());

            logger.info("Buscando o cep {} ", spaceRequestDto.getAddress().getCep());
            AddressResponseDto addressResponseDto = addressService.createAddres(zipCodeRequestDto);
            AddressModel addressModelSave = modelMapper.map(addressResponseDto, AddressModel.class);
            logger.info("Salvando Endereço");
            addressRepository.save(addressModelSave);

            SpaceModel spaceModel = new SpaceModel();
            spaceModel.setName(spaceRequestDto.getName());
            spaceModel.setUser(userModel);
            spaceModel.setResource(resourceModel);
            spaceModel.setAddress(addressModelSave);
            spaceModel.setOpeningHours(spaceRequestDto.getOpeningHours());
            spaceModel.setDescription(spaceRequestDto.getDescription());

            logger.info("Salvando Espaço no banco de dados");
            spaceRepository.save(spaceModel);

            logger.debug("Espaço criado com sucesso. ID: {}", spaceModel.getId());

            SpaceResponseDto spaceResponseDto = modelMapper.map(spaceModel, SpaceResponseDto.class);
            logger.debug("Espaço model foi convertido para SpaceResponseDto");

            return spaceResponseDto;
        } catch (Exception e) {
            logger.error("Erro ao criar espaço.", e);
            throw e;
        }
    }

    public SpaceResponseDto update(Long spaceId, SpaceRequestDto updatedSpaceDto) {
        try {
            logger.debug("Atualizando espaço com ID: {}", spaceId);

            SpaceModel existingSpace = spaceRepository.findById(spaceId)
                    .orElseThrow(() -> new SpaceNotFoundException(spaceId));

            existingSpace.setName(updatedSpaceDto.getName());

            ResourceModel updatedResource = resourceRepository.findById(updatedSpaceDto.getResourceId())
                    .orElseThrow(() -> new ResourceNotFoudException(updatedSpaceDto.getResourceId()));
            existingSpace.setResource(updatedResource);

            ZipCodeRequestDto updatedZipCodeDto = new ZipCodeRequestDto();
            updatedZipCodeDto.setCep(updatedSpaceDto.getAddress().getCep());
            updatedZipCodeDto.setComplement(updatedSpaceDto.getAddress().getComplement());
            updatedZipCodeDto.setNumberAddress(updatedSpaceDto.getAddress().getNumberAddress());

            AddressResponseDto updatedAddressResponseDto = addressService.createAddres(updatedZipCodeDto);
            AddressModel updatedAddressModel = modelMapper.map(updatedAddressResponseDto, AddressModel.class);

            addressRepository.save(updatedAddressModel);

            existingSpace.setAddress(updatedAddressModel);

            spaceRepository.save(existingSpace);

            logger.debug("Espaço atualizado com sucesso. ID: {}", spaceId);

            SpaceResponseDto spaceResponseDto = modelMapper.map(existingSpace, SpaceResponseDto.class);
            logger.debug("Espaço model foi convertido para SpaceResponseDto");

            return spaceResponseDto;
        } catch (Exception e) {
            logger.error("Erro ao atualizar espaço.", e);
            throw e;
        }
    }

    public void delete(Long spaceId){
        try{
            logger.debug("Excluindo espaço pelo ID: {}", spaceId);
            SpaceModel existingSpace = spaceRepository.findById(spaceId)
                    .orElseThrow(() -> new SpaceNotFoundException(spaceId));

            spaceRepository.delete(existingSpace);
            logger.debug("Espaço excluido com sucesso. ID: {}", spaceId);
        } catch (SpaceNotFoundException e){
            logger.error("Erro ao excluir o espaço pelo ID. {}", spaceId, e);
            throw e;
        }
    }
}
