package com.cartwise.cartwise.controller;

import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.service.GroceryItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/grocery-items")
public class GroceryItemController {

    private final GroceryItemService service;

    public GroceryItemController(GroceryItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GroceryItem>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroceryItem> get(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GroceryItem> create(@RequestBody GroceryItem item) {
        if (!item.isAvailable()) item.setAvailable(true);
        var saved = service.save(item);
        return ResponseEntity.created(URI.create("/api/grocery-items/" + saved.getGroceryId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroceryItem> update(@PathVariable Long id, @RequestBody GroceryItem item) {
        var existing = service.findById(id).orElse(null);
        if (existing == null) return ResponseEntity.notFound().build();
        existing.setName(item.getName());
        existing.setAvailable(item.isAvailable());
        var saved = service.save(existing);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBatch(@RequestBody List<Long> ids) {
        service.deleteByIdInBatch(ids);
        return ResponseEntity.noContent().build();
    }
}
