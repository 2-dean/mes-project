package com.mes.mes_project.controller;

import com.mes.mes_project.dto.ItemRequestDto;
import com.mes.mes_project.dto.ItemResponseDto;
import com.mes.mes_project.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * 전체조회 get
     */
    @GetMapping
    //public List<Item> findAll() {
    public List<ItemResponseDto> findAll() {
        return itemService.findAll();
    }

    // 단건조회
    @GetMapping("/{id}")
    public ItemResponseDto findById(@PathVariable Long id) {
        return itemService.findById(id);
    }

    // 등록
    @PostMapping
    public ItemResponseDto save(@Valid @RequestBody ItemRequestDto item) {
        return  itemService.save(item);
    }

    // 수정
    @PutMapping("/{id}")
    public ItemResponseDto update(@PathVariable Long id,
                       @Valid @RequestBody ItemRequestDto item) {
        return itemService.update(id, item);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }
}
