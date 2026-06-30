package com.group.group2.pet.service;

import com.group.group2.pet.domain.UserEntity;
import com.group.group2.pet.dto.UserDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository repository;
    private final ApiMapper mapper;

    public UserService(UserRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<UserDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toUserResponse).toList();
    }

    @Transactional(readOnly = true)
    public UserDto.Response findById(UUID id) {
        return mapper.toUserResponse(findEntityById(id));
    }

    @Transactional
    public UserDto.Response create(UserDto.Request request) {
        return mapper.toUserResponse(repository.save(mapper.toUserEntity(request)));
    }

    @Transactional
    public UserDto.Response update(UUID id, UserDto.Request request) {
        UserEntity entity = findEntityById(id);
        mapper.updateUserEntity(request, entity);
        return mapper.toUserResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private UserEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
    }
}
