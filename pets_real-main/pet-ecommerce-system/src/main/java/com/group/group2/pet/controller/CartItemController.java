package com.group.group2.pet.controller;

import com.group.group2.pet.dto.CartItemDto;
import com.group.group2.pet.service.CartItemService;
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
@RequestMapping("/api/cart-items")
public class CartItemController {
    private final CartItemService service;

    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<CartItemDto.Response> findAll() {
        return service.findAll();
    }

    @GetMapping("/by-cart/{cartId}")
    public List<CartItemDto.Response> findByCartId(@PathVariable UUID cartId) {
        return service.findByCartId(cartId);
    }

    @GetMapping("/{id}")
    public CartItemDto.Response findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemDto.Response create(@Valid @RequestBody CartItemDto.Request request) {
        return service.create(request);
    }

    @PostMapping("/upsert")
    public CartItemDto.Response createOrUpdateByCartAndPet(@Valid @RequestBody CartItemDto.Request request) {
        return service.createOrUpdateByCartAndPet(request);
    }

    @PutMapping("/{id}")
    public CartItemDto.Response update(@PathVariable UUID id, @Valid @RequestBody CartItemDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
