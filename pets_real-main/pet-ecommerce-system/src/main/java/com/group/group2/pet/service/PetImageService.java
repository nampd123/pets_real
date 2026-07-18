package com.group.group2.pet.service;

import com.group.group2.pet.domain.PetImageEntity;
import com.group.group2.pet.dto.PetImageDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.PetImageRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class PetImageService {
    private final PetImageRepository repository;
    private final ApiMapper mapper;

    public PetImageService(PetImageRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PetImageDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toPetImageResponse).toList();
    }

    @Transactional(readOnly = true)
    public PetImageDto.Response findById(UUID id) {
        return mapper.toPetImageResponse(findEntityById(id));
    }

    @Transactional
    public PetImageDto.Response create(PetImageDto.Request request) {
        return mapper.toPetImageResponse(repository.save(mapper.toPetImageEntity(request)));
    }

    @Transactional
    public PetImageDto.Response upload(UUID petId, MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file must be provided");
        }

        try {
            Path uploadsDir = Paths.get("uploads").toAbsolutePath();
            Files.createDirectories(uploadsDir);

            String originalFilename = Paths.get(imageFile.getOriginalFilename()).getFileName().toString();
            String filename = UUID.randomUUID() + "-" + originalFilename;
            Path destination = uploadsDir.resolve(filename);
            Files.copy(imageFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(filename)
                    .toUriString();

            PetImageDto.Request request = new PetImageDto.Request(petId, imageUrl, true);
            return create(request);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to store uploaded image", exception);
        }
    }

    @Transactional
    public PetImageDto.Response update(UUID id, PetImageDto.Request request) {
        PetImageEntity entity = findEntityById(id);
        mapper.updatePetImageEntity(request, entity);
        return mapper.toPetImageResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private PetImageEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PetImage", id));
    }
}
