package com.mes.mes_project.contorller;

import com.mes.mes_project.entity.Item;
import com.mes.mes_project.service.ItemService;
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
    public List<Item> findAll() {
        return itemService.findAll();
    }

    // 단건조회
    @GetMapping("/{id}")
    public Item findById(@PathVariable Long id) {
        return itemService.findById(id);
    }

    // 등록
    @PostMapping
    public Item save(@RequestBody Item item) {
        return  itemService.save(item);
    }

    // 수정
    @PutMapping("/{id}")
    public Item update(@PathVariable Long id,
                       @RequestBody Item item) {
        return itemService.update(id, item);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }
}
