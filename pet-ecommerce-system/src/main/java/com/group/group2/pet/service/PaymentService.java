package com.group.group2.pet.service;

import com.group.group2.pet.domain.OrderEntity;
import com.group.group2.pet.domain.OrderItemEntity;
import com.group.group2.pet.domain.PaymentEntity;
import com.group.group2.pet.domain.PetEntity;
import com.group.group2.pet.dto.PaymentDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.OrderItemRepository;
import com.group.group2.pet.repository.OrderRepository;
import com.group.group2.pet.repository.PaymentRepository;
import com.group.group2.pet.repository.PetRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    private final PaymentRepository repository;
    private final ApiMapper mapper;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PetRepository petRepository;

    public PaymentService(PaymentRepository repository, ApiMapper mapper, OrderRepository orderRepository, OrderItemRepository orderItemRepository, PetRepository petRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.petRepository = petRepository;
    }

    @Transactional(readOnly = true)
    public List<PaymentDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toPaymentResponse).toList();
    }

    @Transactional(readOnly = true)
    public PaymentDto.Response findById(UUID id) {
        return mapper.toPaymentResponse(findEntityById(id));
    }

    @Transactional
    public PaymentDto.Response create(PaymentDto.Request request) {
        PaymentEntity payment = repository.save(mapper.toPaymentEntity(request));
        if (isSuccessful(payment)) {
            deductInventory(payment.getOrderId());
        }
        return mapper.toPaymentResponse(payment);
    }

    @Transactional
    public PaymentDto.Response update(UUID id, PaymentDto.Request request) {
        PaymentEntity entity = findEntityById(id);
        mapper.updatePaymentEntity(request, entity);
        return mapper.toPaymentResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private boolean isSuccessful(PaymentEntity payment) {
        String status = payment.getStatus();
        if (status == null) {
            return false;
        }
        String normalized = status.trim().toUpperCase();
        return "SUCCESS".equals(normalized) || "PAID".equals(normalized);
    }

    private void deductInventory(UUID orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        List<OrderItemEntity> items = orderItemRepository.findByOrderId(order.getId());
        for (OrderItemEntity item : items) {
            PetEntity pet = petRepository.findById(item.getPetId())
                    .orElseThrow(() -> new ResourceNotFoundException("Pet", item.getPetId()));
            int quantity = item.getQuantity() == null ? 1 : item.getQuantity();
            int current = pet.getQuantity() == null ? 0 : pet.getQuantity();
            if (current < quantity) {
                throw new IllegalStateException("Không đủ tồn kho cho sản phẩm: " + pet.getName());
            }
            pet.setQuantity(current - quantity);
            if (pet.getQuantity() <= 0) {
                pet.setStatus("OUT_OF_STOCK");
            }
            petRepository.save(pet);
        }
    }

    private PaymentEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment", id));
    }
}
