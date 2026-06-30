package com.group.group2.pet.service;

import com.group.group2.pet.domain.OrderItemEntity;
import com.group.group2.pet.dto.OrderItemDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.OrderItemRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderItemService {
    private final OrderItemRepository repository;
    private final ApiMapper mapper;

    public OrderItemService(OrderItemRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<OrderItemDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toOrderItemResponse).toList();
    }

    @Transactional(readOnly = true)
    public OrderItemDto.Response findById(UUID id) {
        return mapper.toOrderItemResponse(findEntityById(id));
    }

    @Transactional
    public OrderItemDto.Response create(OrderItemDto.Request request) {
        return mapper.toOrderItemResponse(repository.save(mapper.toOrderItemEntity(request)));
    }

    @Transactional
    public OrderItemDto.Response update(UUID id, OrderItemDto.Request request) {
        OrderItemEntity entity = findEntityById(id);
        mapper.updateOrderItemEntity(request, entity);
        return mapper.toOrderItemResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private OrderItemEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("OrderItem", id));
    }
}
