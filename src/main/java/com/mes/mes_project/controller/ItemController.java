package com.mes.mes_project.controller;

import com.mes.mes_project.dto.item.ItemRequestDto;
import com.mes.mes_project.dto.item.ItemResponseDto;
import com.mes.mes_project.dto.item.ItemSearchDto;
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

    // 조회 (검색조건: 품목명, 사용여부)
    @GetMapping
    public List<ItemResponseDto> search(
            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) String useYn) {

        ItemSearchDto searchDto = new ItemSearchDto();
        searchDto.setItemName(itemName);
        searchDto.setUseYn(useYn);
        return itemService.search(searchDto);
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
