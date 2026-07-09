package com.mes.mes_project.service;

import com.mes.mes_project.dto.item.ItemRequestDto;
import com.mes.mes_project.dto.item.ItemResponseDto;
import com.mes.mes_project.dto.item.ItemSearchDto;
import com.mes.mes_project.entity.Item;
import com.mes.mes_project.mapper.ItemMapper;
import com.mes.mes_project.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    // 등록
    public ItemResponseDto save(ItemRequestDto requestDto){
        Item item = requestDto.toEntity(); // DTO -> Entity
        Item saved = itemRepository.save(item); // DB 저장
        return ItemResponseDto.from(saved); // Entity -> ResponseDTO
    }

    // 검색 (MyBatis, 품목명/사용여부)
    public List<ItemResponseDto> search(ItemSearchDto searchDto) {
        return itemMapper.search(searchDto);
    }

    // 단건조회
    public ItemResponseDto findById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("품목 없음"));
        return ItemResponseDto.from(item); // Entity → Dto 변환 메서드
    }

    // 수정
    @Transactional
    public ItemResponseDto update(Long id, ItemRequestDto updateItem) {
                //              ↑               ↑
                //          URL의 id      body로 받은 데이터
        // 1. id로 기존 데이터 조회(Entity로 조회)
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("품목 없음"));
        // --> JPA가 관리(Dirty Checking)

        // 2. 새로운 값으로 업데이트
        item.update(
                updateItem.getItemName(),
                updateItem.getSpec(),
                updateItem.getUnit(),
                updateItem.getUnitPrice(),
                updateItem.getIncentiveRate(),
                updateItem.getUseYn()
        );

        // 3. save 없이 바로 ResponseDto로 변환
        return ItemResponseDto.from(item);
        // 트랜잭션 끝나면 JPA가 알아서 UPDATE 실행!

        // @Transactional 사용 전 => 저장 후 ResponseDto로 변환 후 반환
        //return ItemResponseDto.from(itemRepository.save(item));
    }

    // 삭제 (db에서 사용여부만 변경)
    @Transactional
    public void delete(Long id) {
        Item item = itemRepository.findById(id) // JPA가 이 객체를 "감시"시작(영속상태)
                        .orElseThrow(() -> new RuntimeException("품목 없음"));
        item.delete(); // useYn = N 으로 변경
        // JPA가 값 변경 감지(Dirty Checking)

        //  @Transactional 사용으로 save 없어도 DB에 반영됨
        // itemRepository.save(item); // 저장
    }
}
