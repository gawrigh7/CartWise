package com.cartwise.cartwise.controller;

import com.cartwise.cartwise.dto.GroceryItemCreateDto;
import com.cartwise.cartwise.dto.GroceryItemDto;
import com.cartwise.cartwise.dto.GroceryItemUpdateDto;
import com.cartwise.cartwise.mapper.GroceryItemMapper;
import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.service.GroceryItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/grocery-item")
public class GroceryItemController {

    private final GroceryItemService groceryItemService;
    private final GroceryItemMapper groceryItemMapper;

    public GroceryItemController(GroceryItemService groceryItemService,
                                 GroceryItemMapper groceryItemMapper) {
        this.groceryItemService = groceryItemService;
        this.groceryItemMapper = groceryItemMapper;
    }

    @GetMapping
    public ResponseEntity<List<GroceryItemDto>> getMyItems() {
        var items = groceryItemService.findAll()
                .stream().map(groceryItemMapper::toDto).toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<GroceryItemDto> getMyItemByName(@PathVariable String name) {
        return groceryItemService.findByName(name)
                .map(groceryItemMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroceryItemDto> getMyItemById(@PathVariable Long id) {
        return groceryItemService.findById(id)
                .map(groceryItemMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GroceryItemDto> createItem(@RequestBody @Valid GroceryItemCreateDto req) {
        var entity = groceryItemMapper.fromCreateDto(req);
        entity.setAvailable(true);
        var saved = groceryItemService.save(entity);
        var dto = groceryItemMapper.toDto(saved);
        return ResponseEntity
                .created(URI.create("/api/grocery-items/" + dto.getGroceryId()))
                .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroceryItemDto> updateItem(@PathVariable Long id, @RequestBody @Valid GroceryItemUpdateDto req) {
        var toUpdate = groceryItemMapper.fromUpdateDto(id, req);
        var saved = groceryItemService.save(toUpdate);
        return ResponseEntity.ok(groceryItemMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        groceryItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteItems(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) return ResponseEntity.noContent().build();
        groceryItemService.deleteByIdInBatch(ids);
        return ResponseEntity.noContent().build();
    }

}
