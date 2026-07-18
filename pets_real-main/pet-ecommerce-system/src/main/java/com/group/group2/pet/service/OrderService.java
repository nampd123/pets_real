package com.group.group2.pet.service;

import com.group.group2.pet.domain.OrderEntity;
import com.group.group2.pet.dto.OrderDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final ApiMapper mapper;

    public OrderService(OrderRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<OrderDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toOrderResponse).toList();
    }

    @Transactional(readOnly = true)
    public OrderDto.Response findById(UUID id) {
        return mapper.toOrderResponse(findEntityById(id));
    }

    @Transactional
    public OrderDto.Response create(OrderDto.Request request) {
        return mapper.toOrderResponse(repository.save(mapper.toOrderEntity(request)));
    }

    @Transactional
    public OrderDto.Response update(UUID id, OrderDto.Request request) {
        OrderEntity entity = findEntityById(id);
        mapper.updateOrderEntity(request, entity);
        return mapper.toOrderResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private OrderEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", id));
    }
}
