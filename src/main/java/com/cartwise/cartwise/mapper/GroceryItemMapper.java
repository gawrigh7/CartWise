package com.cartwise.cartwise.mapper;

import com.cartwise.cartwise.dto.GroceryItemCreateDto;
import com.cartwise.cartwise.dto.GroceryItemDto;
import com.cartwise.cartwise.dto.GroceryItemUpdateDto;
import com.cartwise.cartwise.model.GroceryItem;
import org.springframework.stereotype.Component;

@Component
public class GroceryItemMapper {

    public GroceryItemDto toDto(GroceryItem e) {
        if (e == null) return null;
        GroceryItemDto dto = new GroceryItemDto();
        dto.setId(e.getGroceryId());
        dto.setName(e.getName());
        dto.setAvailable(e.isAvailable());
        return dto;
    }

    public GroceryItem fromCreateDto(GroceryItemCreateDto d) {
        if (d == null) return null;
        GroceryItem e = new GroceryItem();
        e.setName(d.getName());
        e.setAvailable(d.getAvailable() != null ? d.getAvailable() : true);
        return e;
    }

    public void applyUpdate(GroceryItem e, GroceryItemUpdateDto d) {
        e.setName(d.getName());
        if (d.getAvailable() != null) e.setAvailable(d.getAvailable());
    }
}

