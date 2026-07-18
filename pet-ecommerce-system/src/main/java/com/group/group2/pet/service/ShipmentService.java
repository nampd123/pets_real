package com.group.group2.pet.service;

import com.group.group2.pet.domain.ShipmentEntity;
import com.group.group2.pet.dto.ShipmentDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.ShipmentRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShipmentService {
    private final ShipmentRepository repository;
    private final ApiMapper mapper;

    public ShipmentService(ShipmentRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<ShipmentDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toShipmentResponse).toList();
    }

    @Transactional(readOnly = true)
    public ShipmentDto.Response findById(UUID id) {
        return mapper.toShipmentResponse(findEntityById(id));
    }

    @Transactional
    public ShipmentDto.Response create(ShipmentDto.Request request) {
        return mapper.toShipmentResponse(repository.save(mapper.toShipmentEntity(request)));
    }

    @Transactional
    public ShipmentDto.Response update(UUID id, ShipmentDto.Request request) {
        ShipmentEntity entity = findEntityById(id);
        mapper.updateShipmentEntity(request, entity);
        return mapper.toShipmentResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private ShipmentEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shipment", id));
    }
}
