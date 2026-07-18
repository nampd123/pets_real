package com.group.group2.pet.service;

import com.group.group2.pet.domain.AddressEntity;
import com.group.group2.pet.dto.AddressDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.AddressRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {
    private final AddressRepository repository;
    private final ApiMapper mapper;

    public AddressService(AddressRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<AddressDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toAddressResponse).toList();
    }

    @Transactional(readOnly = true)
    public AddressDto.Response findById(UUID id) {
        return mapper.toAddressResponse(findEntityById(id));
    }

    @Transactional
    public AddressDto.Response create(AddressDto.Request request) {
        return mapper.toAddressResponse(repository.save(mapper.toAddressEntity(request)));
    }

    @Transactional
    public AddressDto.Response update(UUID id, AddressDto.Request request) {
        AddressEntity entity = findEntityById(id);
        mapper.updateAddressEntity(request, entity);
        return mapper.toAddressResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private AddressEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address", id));
    }
}
