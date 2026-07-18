package com.group.group2.pet.service;

import com.group.group2.pet.domain.CartItemEntity;
import com.group.group2.pet.dto.CartItemDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.CartItemRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartItemService {
    private final CartItemRepository repository;
    private final ApiMapper mapper;

    public CartItemService(CartItemRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<CartItemDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toCartItemResponse).toList();
    }

    @Transactional(readOnly = true)
    public CartItemDto.Response findById(UUID id) {
        return mapper.toCartItemResponse(findEntityById(id));
    }

    @Transactional
    public CartItemDto.Response create(CartItemDto.Request request) {
        return mapper.toCartItemResponse(repository.save(mapper.toCartItemEntity(request)));
    }

    @Transactional
    public CartItemDto.Response createOrUpdateByCartAndPet(CartItemDto.Request request) {
        CartItemEntity entity = repository.findAll().stream()
                .filter(item -> request.cartId().equals(item.getCartId()) && request.petId().equals(item.getPetId()))
                .findFirst()
                .orElseGet(CartItemEntity::new);

        if (entity.getId() == null) {
            entity.setCartId(request.cartId());
            entity.setPetId(request.petId());
        }

        mapper.updateCartItemEntity(request, entity);
        return mapper.toCartItemResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<CartItemDto.Response> findByCartId(UUID cartId) {
        return repository.findAll().stream()
                .filter(item -> cartId.equals(item.getCartId()))
                .map(mapper::toCartItemResponse)
                .toList();
    }

    @Transactional
    public CartItemDto.Response update(UUID id, CartItemDto.Request request) {
        CartItemEntity entity = findEntityById(id);
        mapper.updateCartItemEntity(request, entity);
        return mapper.toCartItemResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private CartItemEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("CartItem", id));
    }
}
