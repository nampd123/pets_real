package com.group.group2.pet.controller;

import com.group.group2.pet.dto.CartDto;
import com.group.group2.pet.service.CartService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping
    public List<CartDto.Response> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CartDto.Response findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping("/by-user/{userId}")
    public CartDto.Response findByUserId(@PathVariable UUID userId) {
        return service.findByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto.Response create(@Valid @RequestBody CartDto.Request request) {
        return service.create(request);
    }

    @PostMapping("/by-user/{userId}")
    public CartDto.Response createOrGetByUserId(@PathVariable UUID userId) {
        return service.createOrGetByUserId(userId);
    }

    @PutMapping("/{id}")
    public CartDto.Response update(@PathVariable UUID id, @Valid @RequestBody CartDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
