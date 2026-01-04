package com.cartwise.cartwise.mapper;

import com.cartwise.cartwise.dto.GroceryItemCreateDto;
import com.cartwise.cartwise.dto.GroceryItemDto;
import com.cartwise.cartwise.dto.GroceryItemUpdateDto;
import com.cartwise.cartwise.model.GroceryItem;
import org.springframework.stereotype.Component;

@Component
public class GroceryItemMapper {

    public GroceryItemDto toDto(GroceryItem entity) {
        if (entity == null) return null;
        return new GroceryItemDto(
                entity.getGroceryId(),
                entity.getName(),
                entity.isAvailable()
        );
    }

    public GroceryItem fromCreateDto(GroceryItemCreateDto dto) {
        if (dto == null) return null;
        GroceryItem entity = new GroceryItem();
        entity.setName(dto.getName());
        entity.setAvailable(dto.isAvailable());
        return entity;
    }

    public GroceryItem fromUpdateDto(Long id, GroceryItemUpdateDto dto) {
        if (dto == null) return null;
        GroceryItem entity = new GroceryItem();
        entity.setGroceryId(id);
        entity.setName(dto.getName());
        entity.setAvailable(dto.isAvailable());
        return entity;
    }
}
