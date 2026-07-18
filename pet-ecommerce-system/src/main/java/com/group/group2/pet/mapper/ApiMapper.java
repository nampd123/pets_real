package com.group.group2.pet.mapper;

import com.group.group2.pet.domain.*;
import com.group.group2.pet.dto.*;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class ApiMapper {
    public UserEntity toUserEntity(UserDto.Request request) {
        UserEntity entity = new UserEntity();
        updateUserEntity(request, entity);
        return entity;
    }

    public void updateUserEntity(UserDto.Request request, UserEntity entity) {
        entity.setFullName(request.fullName());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        entity.setPasswordHash(request.passwordHash());
        entity.setRole(request.role());
        entity.setStatus(defaultString(request.status(), "ACTIVE"));
    }

    public UserDto.Response toUserResponse(UserEntity entity) {
        return new UserDto.Response(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getRole(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ShopEntity toShopEntity(ShopDto.Request request) {
        ShopEntity entity = new ShopEntity();
        updateShopEntity(request, entity);
        return entity;
    }

    public void updateShopEntity(ShopDto.Request request, ShopEntity entity) {
        entity.setOwnerId(request.ownerId());
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPhone(request.phone());
        entity.setEmail(request.email());
        entity.setStatus(defaultString(request.status(), "PENDING"));
    }

    public ShopDto.Response toShopResponse(ShopEntity entity) {
        return new ShopDto.Response(
                entity.getId(),
                entity.getOwnerId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public AddressEntity toAddressEntity(AddressDto.Request request) {
        AddressEntity entity = new AddressEntity();
        updateAddressEntity(request, entity);
        return entity;
    }

    public void updateAddressEntity(AddressDto.Request request, AddressEntity entity) {
        entity.setUserId(request.userId());
        entity.setShopId(request.shopId());
        entity.setReceiverName(request.receiverName());
        entity.setPhone(request.phone());
        entity.setProvince(request.province());
        entity.setDistrict(request.district());
        entity.setWard(request.ward());
        entity.setDetailAddress(request.detailAddress());
        entity.setDefaultAddress(defaultBoolean(request.defaultAddress()));
    }

    public AddressDto.Response toAddressResponse(AddressEntity entity) {
        return new AddressDto.Response(
                entity.getId(),
                entity.getUserId(),
                entity.getShopId(),
                entity.getReceiverName(),
                entity.getPhone(),
                entity.getProvince(),
                entity.getDistrict(),
                entity.getWard(),
                entity.getDetailAddress(),
                entity.getDefaultAddress(),
                entity.getCreatedAt()
        );
    }

    public PetSpeciesEntity toPetSpeciesEntity(PetSpeciesDto.Request request) {
        PetSpeciesEntity entity = new PetSpeciesEntity();
        updatePetSpeciesEntity(request, entity);
        return entity;
    }

    public void updatePetSpeciesEntity(PetSpeciesDto.Request request, PetSpeciesEntity entity) {
        entity.setName(request.name());
        entity.setDescription(request.description());
    }

    public PetSpeciesDto.Response toPetSpeciesResponse(PetSpeciesEntity entity) {
        return new PetSpeciesDto.Response(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }

    public PetBreedEntity toPetBreedEntity(PetBreedDto.Request request) {
        PetBreedEntity entity = new PetBreedEntity();
        updatePetBreedEntity(request, entity);
        return entity;
    }

    public void updatePetBreedEntity(PetBreedDto.Request request, PetBreedEntity entity) {
        entity.setSpeciesId(request.speciesId());
        entity.setName(request.name());
        entity.setDescription(request.description());
    }

    public PetBreedDto.Response toPetBreedResponse(PetBreedEntity entity) {
        return new PetBreedDto.Response(
                entity.getId(),
                entity.getSpeciesId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }

    public PetEntity toPetEntity(PetDto.Request request) {
        PetEntity entity = new PetEntity();
        updatePetEntity(request, entity);
        return entity;
    }

    public void updatePetEntity(PetDto.Request request, PetEntity entity) {
        entity.setShopId(request.shopId());
        entity.setSpeciesId(request.speciesId());
        entity.setBreedId(request.breedId());
        entity.setName(request.name());
        entity.setGender(request.gender());
        entity.setAgeMonths(request.ageMonths());
        entity.setColor(request.color());
        entity.setWeightKg(request.weightKg());
        entity.setDescription(request.description());
        entity.setHealthStatus(request.healthStatus());
        entity.setVaccinationStatus(request.vaccinationStatus());
        entity.setQuantity(request.quantity() == null ? 1 : request.quantity());
        entity.setPrice(request.price());
        if (request.status() != null) {
            entity.setStatus(defaultString(request.status(), "AVAILABLE"));
        } else if (entity.getQuantity() != null && entity.getQuantity() > 0) {
            entity.setStatus("AVAILABLE");
        }
    }

    public PetDto.Response toPetResponse(PetEntity entity) {
        return new PetDto.Response(
                entity.getId(),
                entity.getShopId(),
                entity.getSpeciesId(),
                entity.getBreedId(),
                entity.getName(),
                entity.getGender(),
                entity.getAgeMonths(),
                entity.getColor(),
                entity.getWeightKg(),
                entity.getDescription(),
                entity.getHealthStatus(),
                entity.getVaccinationStatus(),
                entity.getQuantity(),
                entity.getPrice(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public PetImageEntity toPetImageEntity(PetImageDto.Request request) {
        PetImageEntity entity = new PetImageEntity();
        updatePetImageEntity(request, entity);
        return entity;
    }

    public void updatePetImageEntity(PetImageDto.Request request, PetImageEntity entity) {
        entity.setPetId(request.petId());
        entity.setImageUrl(request.imageUrl());
        entity.setThumbnail(defaultBoolean(request.thumbnail()));
    }

    public PetImageDto.Response toPetImageResponse(PetImageEntity entity) {
        return new PetImageDto.Response(
                entity.getId(),
                entity.getPetId(),
                entity.getImageUrl(),
                entity.getThumbnail(),
                entity.getCreatedAt()
        );
    }

    public CartEntity toCartEntity(CartDto.Request request) {
        CartEntity entity = new CartEntity();
        updateCartEntity(request, entity);
        return entity;
    }

    public void updateCartEntity(CartDto.Request request, CartEntity entity) {
        entity.setUserId(request.userId());
    }

    public CartDto.Response toCartResponse(CartEntity entity) {
        return new CartDto.Response(
                entity.getId(),
                entity.getUserId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public CartItemEntity toCartItemEntity(CartItemDto.Request request) {
        CartItemEntity entity = new CartItemEntity();
        updateCartItemEntity(request, entity);
        return entity;
    }

    public void updateCartItemEntity(CartItemDto.Request request, CartItemEntity entity) {
        entity.setCartId(request.cartId());
        entity.setPetId(request.petId());
        entity.setQuantity(request.quantity());
        entity.setPrice(request.price());
    }

    public CartItemDto.Response toCartItemResponse(CartItemEntity entity) {
        return new CartItemDto.Response(
                entity.getId(),
                entity.getCartId(),
                entity.getPetId(),
                entity.getQuantity(),
                entity.getPrice(),
                entity.getCreatedAt()
        );
    }

    public OrderEntity toOrderEntity(OrderDto.Request request) {
        OrderEntity entity = new OrderEntity();
        updateOrderEntity(request, entity);
        return entity;
    }

    public void updateOrderEntity(OrderDto.Request request, OrderEntity entity) {
        entity.setCustomerId(request.customerId());
        entity.setShopId(request.shopId());
        entity.setTotalAmount(request.totalAmount());
        entity.setShippingFee(defaultBigDecimal(request.shippingFee()));
        entity.setFinalAmount(request.finalAmount());
        entity.setReceiverName(request.receiverName());
        entity.setReceiverPhone(request.receiverPhone());
        entity.setReceiverAddress(request.receiverAddress());
        entity.setNote(request.note());
        entity.setStatus(defaultString(request.status(), "PENDING"));
    }

    public OrderDto.Response toOrderResponse(OrderEntity entity) {
        return new OrderDto.Response(
                entity.getId(),
                entity.getCustomerId(),
                entity.getShopId(),
                entity.getTotalAmount(),
                entity.getShippingFee(),
                entity.getFinalAmount(),
                entity.getReceiverName(),
                entity.getReceiverPhone(),
                entity.getReceiverAddress(),
                entity.getNote(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public OrderItemEntity toOrderItemEntity(OrderItemDto.Request request) {
        OrderItemEntity entity = new OrderItemEntity();
        updateOrderItemEntity(request, entity);
        return entity;
    }

    public void updateOrderItemEntity(OrderItemDto.Request request, OrderItemEntity entity) {
        entity.setOrderId(request.orderId());
        entity.setPetId(request.petId());
        entity.setPetName(request.petName());
        entity.setQuantity(request.quantity());
        entity.setPrice(request.price());
    }

    public OrderItemDto.Response toOrderItemResponse(OrderItemEntity entity) {
        return new OrderItemDto.Response(
                entity.getId(),
                entity.getOrderId(),
                entity.getPetId(),
                entity.getPetName(),
                entity.getQuantity(),
                entity.getPrice(),
                entity.getCreatedAt()
        );
    }

    public PaymentEntity toPaymentEntity(PaymentDto.Request request) {
        PaymentEntity entity = new PaymentEntity();
        updatePaymentEntity(request, entity);
        return entity;
    }

    public void updatePaymentEntity(PaymentDto.Request request, PaymentEntity entity) {
        entity.setOrderId(request.orderId());
        entity.setPaymentMethod(request.paymentMethod());
        entity.setAmount(request.amount());
        entity.setStatus(defaultString(request.status(), "PENDING"));
        entity.setTransactionCode(request.transactionCode());
        entity.setPaidAt(request.paidAt());
    }

    public PaymentDto.Response toPaymentResponse(PaymentEntity entity) {
        return new PaymentDto.Response(
                entity.getId(),
                entity.getOrderId(),
                entity.getPaymentMethod(),
                entity.getAmount(),
                entity.getStatus(),
                entity.getTransactionCode(),
                entity.getPaidAt(),
                entity.getCreatedAt()
        );
    }

    public ShipmentEntity toShipmentEntity(ShipmentDto.Request request) {
        ShipmentEntity entity = new ShipmentEntity();
        updateShipmentEntity(request, entity);
        return entity;
    }

    public void updateShipmentEntity(ShipmentDto.Request request, ShipmentEntity entity) {
        entity.setOrderId(request.orderId());
        entity.setCarrierName(request.carrierName());
        entity.setTrackingCode(request.trackingCode());
        entity.setShippingStatus(defaultString(request.shippingStatus(), "PENDING"));
        entity.setShippedAt(request.shippedAt());
        entity.setDeliveredAt(request.deliveredAt());
    }

    public ShipmentDto.Response toShipmentResponse(ShipmentEntity entity) {
        return new ShipmentDto.Response(
                entity.getId(),
                entity.getOrderId(),
                entity.getCarrierName(),
                entity.getTrackingCode(),
                entity.getShippingStatus(),
                entity.getShippedAt(),
                entity.getDeliveredAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ReviewEntity toReviewEntity(ReviewDto.Request request) {
        ReviewEntity entity = new ReviewEntity();
        updateReviewEntity(request, entity);
        return entity;
    }

    public void updateReviewEntity(ReviewDto.Request request, ReviewEntity entity) {
        entity.setOrderId(request.orderId());
        entity.setCustomerId(request.customerId());
        entity.setShopId(request.shopId());
        entity.setRating(request.rating());
        entity.setComment(request.comment());
    }

    public ReviewDto.Response toReviewResponse(ReviewEntity entity) {
        return new ReviewDto.Response(
                entity.getId(),
                entity.getOrderId(),
                entity.getCustomerId(),
                entity.getShopId(),
                entity.getRating(),
                entity.getComment(),
                entity.getCreatedAt()
        );
    }

    public FavoriteEntity toFavoriteEntity(FavoriteDto.Request request) {
        FavoriteEntity entity = new FavoriteEntity();
        updateFavoriteEntity(request, entity);
        return entity;
    }

    public void updateFavoriteEntity(FavoriteDto.Request request, FavoriteEntity entity) {
        entity.setUserId(request.userId());
        entity.setPetId(request.petId());
    }

    public FavoriteDto.Response toFavoriteResponse(FavoriteEntity entity) {
        return new FavoriteDto.Response(
                entity.getId(),
                entity.getUserId(),
                entity.getPetId(),
                entity.getCreatedAt()
        );
    }

    private String defaultString(String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }

    private Boolean defaultBoolean(Boolean value) {
        return value != null && value;
    }

    private BigDecimal defaultBigDecimal(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
