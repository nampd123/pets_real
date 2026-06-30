package com.group.group2.pet.service;

import com.group.group2.pet.domain.PetImageEntity;
import com.group.group2.pet.dto.PetImageDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.PetImageRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetImageService {
    private final PetImageRepository repository;
    private final ApiMapper mapper;

    public PetImageService(PetImageRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PetImageDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toPetImageResponse).toList();
    }

    @Transactional(readOnly = true)
    public PetImageDto.Response findById(UUID id) {
        return mapper.toPetImageResponse(findEntityById(id));
    }

    @Transactional
    public PetImageDto.Response create(PetImageDto.Request request) {
        return mapper.toPetImageResponse(repository.save(mapper.toPetImageEntity(request)));
    }

    @Transactional
    public PetImageDto.Response update(UUID id, PetImageDto.Request request) {
        PetImageEntity entity = findEntityById(id);
        mapper.updatePetImageEntity(request, entity);
        return mapper.toPetImageResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private PetImageEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PetImage", id));
    }
}
