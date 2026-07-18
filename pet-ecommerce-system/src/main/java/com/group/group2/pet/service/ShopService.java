package com.group.group2.pet.service;

import com.group.group2.pet.domain.ShopEntity;
import com.group.group2.pet.dto.ShopDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.ShopRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopService {
    private final ShopRepository repository;
    private final ApiMapper mapper;

    public ShopService(ShopRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<ShopDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toShopResponse).toList();
    }

    @Transactional(readOnly = true)
    public ShopDto.Response findById(UUID id) {
        return mapper.toShopResponse(findEntityById(id));
    }

    @Transactional
    public ShopDto.Response create(ShopDto.Request request) {
        return mapper.toShopResponse(repository.save(mapper.toShopEntity(request)));
    }

    @Transactional
    public ShopDto.Response update(UUID id, ShopDto.Request request) {
        ShopEntity entity = findEntityById(id);
        mapper.updateShopEntity(request, entity);
        return mapper.toShopResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private ShopEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shop", id));
    }
}
