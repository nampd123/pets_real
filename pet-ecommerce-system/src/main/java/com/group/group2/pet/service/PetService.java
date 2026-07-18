package com.group.group2.pet.service;

import com.group.group2.pet.domain.PetEntity;
import com.group.group2.pet.dto.PetDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.PetRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetService {
    private final PetRepository repository;
    private final ApiMapper mapper;

    public PetService(PetRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PetDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toPetResponse).toList();
    }

    @Transactional(readOnly = true)
    public PetDto.Response findById(UUID id) {
        return mapper.toPetResponse(findEntityById(id));
    }

    @Transactional
    public PetDto.Response create(PetDto.Request request) {
        return mapper.toPetResponse(repository.save(mapper.toPetEntity(request)));
    }

    @Transactional
    public PetDto.Response update(UUID id, PetDto.Request request) {
        PetEntity entity = findEntityById(id);
        mapper.updatePetEntity(request, entity);
        return mapper.toPetResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private PetEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet", id));
    }
}
