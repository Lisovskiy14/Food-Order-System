package org.example.foodordersystem.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.foodordersystem.domain.Item;
import org.example.foodordersystem.dto.item.ItemListResponseDto;
import org.example.foodordersystem.dto.item.ItemRequestDto;
import org.example.foodordersystem.dto.item.ItemUpdateRequestDto;
import org.example.foodordersystem.service.ItemService;
import org.example.foodordersystem.web.mapper.ItemMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @GetMapping
    public ResponseEntity<Object> getAllItems() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ItemListResponseDto(
                        itemService.getAllItems().stream()
                                .map(itemMapper::toItemResponseDto)
                                .toList()));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@PathVariable UUID itemId) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemMapper.toItemResponseDto(
                        itemService.getItemById(itemId)));
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@Valid @RequestBody ItemRequestDto itemRequestDto) {
        Item item = itemMapper.toItem(itemRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemMapper.toItemResponseDto(
                        itemService.saveItem(item)));
    }

    @PutMapping
    public ResponseEntity<Object> updateItem(@Valid @RequestBody ItemUpdateRequestDto itemUpdateRequestDto) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemMapper.toItemResponseDto(
                        itemService.updateItem(itemUpdateRequestDto)));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> deleteItemById(@PathVariable UUID itemId) {
        itemService.deleteItemById(itemId);
        return ResponseEntity.noContent().build();
    }
}
