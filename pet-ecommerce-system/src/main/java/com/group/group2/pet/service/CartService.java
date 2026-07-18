package com.group.group2.pet.service;

import com.group.group2.pet.domain.CartEntity;
import com.group.group2.pet.dto.CartDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.CartRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private final CartRepository repository;
    private final ApiMapper mapper;

    public CartService(CartRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<CartDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toCartResponse).toList();
    }

    @Transactional(readOnly = true)
    public CartDto.Response findById(UUID id) {
        return mapper.toCartResponse(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public CartDto.Response findByUserId(UUID userId) {
        return mapper.toCartResponse(repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", userId)));
    }

    @Transactional
    public CartDto.Response createOrGetByUserId(UUID userId) {
        return mapper.toCartResponse(repository.findByUserId(userId)
                .orElseGet(() -> repository.save(mapper.toCartEntity(new CartDto.Request(userId)))));
    }

    @Transactional
    public CartDto.Response create(CartDto.Request request) {
        return mapper.toCartResponse(repository.save(mapper.toCartEntity(request)));
    }

    @Transactional
    public CartDto.Response update(UUID id, CartDto.Request request) {
        CartEntity entity = findEntityById(id);
        mapper.updateCartEntity(request, entity);
        return mapper.toCartResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private CartEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart", id));
    }
}
