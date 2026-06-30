package com.group.group2.pet.service;

import com.group.group2.pet.domain.PaymentEntity;
import com.group.group2.pet.dto.PaymentDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.PaymentRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    private final PaymentRepository repository;
    private final ApiMapper mapper;

    public PaymentService(PaymentRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
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
        return mapper.toPaymentResponse(repository.save(mapper.toPaymentEntity(request)));
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

    private PaymentEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment", id));
    }
}
