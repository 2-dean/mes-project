package com.mes.mes_project.service;

import com.mes.mes_project.entity.Item;
import com.mes.mes_project.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 등록
    public Item save(Item item){
        return itemRepository.save(item);
    }

    // 전체조회
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    // 단건조회
    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("품목 없음"));
    }

    // 수정
    public Item update(Long id, Item updateItem) {
                //              ↑               ↑
                //          URL의 id      body로 받은 데이터
        // 1. id로 기존 데이터 조회
        Item item = findById(id);

        // 2. 새로운 값으로 업데이트
        item.updateItem(
                updateItem.getItemName(),
                updateItem.getSpec(),
                updateItem.getUnit(),
                updateItem.getUnitPrice(),
                updateItem.getUseYn()
        );

        // 3. 저장 후 반환
        return itemRepository.save(item);
    }

    // 삭제 (db에서 사용여부만 변경)
    public void delete(Long id) {
        Item item = findById(id);
        item.delete();
    }
}
