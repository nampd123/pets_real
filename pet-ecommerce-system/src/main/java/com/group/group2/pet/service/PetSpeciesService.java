package com.group.group2.pet.service;

import com.group.group2.pet.domain.PetSpeciesEntity;
import com.group.group2.pet.dto.PetSpeciesDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.PetSpeciesRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetSpeciesService {
    private final PetSpeciesRepository repository;
    private final ApiMapper mapper;

    public PetSpeciesService(PetSpeciesRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PetSpeciesDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toPetSpeciesResponse).toList();
    }

    @Transactional(readOnly = true)
    public PetSpeciesDto.Response findById(UUID id) {
        return mapper.toPetSpeciesResponse(findEntityById(id));
    }

    @Transactional
    public PetSpeciesDto.Response create(PetSpeciesDto.Request request) {
        return mapper.toPetSpeciesResponse(repository.save(mapper.toPetSpeciesEntity(request)));
    }

    @Transactional
    public PetSpeciesDto.Response update(UUID id, PetSpeciesDto.Request request) {
        PetSpeciesEntity entity = findEntityById(id);
        mapper.updatePetSpeciesEntity(request, entity);
        return mapper.toPetSpeciesResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private PetSpeciesEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PetSpecies", id));
    }
}
