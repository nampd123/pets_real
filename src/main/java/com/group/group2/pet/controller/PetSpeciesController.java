package com.group.group2.pet.controller;

import com.group.group2.pet.dto.PetSpeciesDto;
import com.group.group2.pet.service.PetSpeciesService;
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
@RequestMapping("/api/pet-species")
public class PetSpeciesController {
    private final PetSpeciesService service;

    public PetSpeciesController(PetSpeciesService service) {
        this.service = service;
    }

    @GetMapping
    public List<PetSpeciesDto.Response> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PetSpeciesDto.Response findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetSpeciesDto.Response create(@Valid @RequestBody PetSpeciesDto.Request request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public PetSpeciesDto.Response update(@PathVariable UUID id, @Valid @RequestBody PetSpeciesDto.Request request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
