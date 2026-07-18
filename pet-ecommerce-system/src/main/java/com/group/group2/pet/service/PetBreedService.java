package com.group.group2.pet.service;

import com.group.group2.pet.domain.PetBreedEntity;
import com.group.group2.pet.dto.PetBreedDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.PetBreedRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetBreedService {
    private final PetBreedRepository repository;
    private final ApiMapper mapper;

    public PetBreedService(PetBreedRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PetBreedDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toPetBreedResponse).toList();
    }

    @Transactional(readOnly = true)
    public PetBreedDto.Response findById(UUID id) {
        return mapper.toPetBreedResponse(findEntityById(id));
    }

    @Transactional
    public PetBreedDto.Response create(PetBreedDto.Request request) {
        return mapper.toPetBreedResponse(repository.save(mapper.toPetBreedEntity(request)));
    }

    @Transactional
    public PetBreedDto.Response update(UUID id, PetBreedDto.Request request) {
        PetBreedEntity entity = findEntityById(id);
        mapper.updatePetBreedEntity(request, entity);
        return mapper.toPetBreedResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private PetBreedEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PetBreed", id));
    }
}
