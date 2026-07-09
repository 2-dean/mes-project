package com.mes.mes_project.dto.item;

import com.mes.mes_project.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ItemResponseDto {
    // 조회할때 프론트에게 보내줄 데이터
    private Long id; // 수정/삭제할 때 필요

    private String itemCode;
    private String itemName; // 제품명

    private String spec; // 스펙
    private String unit; // 단위
    private int unitPrice; //단가
    private int incentiveRate; // 인센티브
    private String useYn = "Y";  // 기본값 Y

    private String createdBy;   // 생성자
    private String updatedBy;   // 수정자
    private LocalDateTime createdAt;  // 생성일
    private LocalDateTime updatedAt;  // 수정일

    // Entity → Dto 변환 메서드
    public static ItemResponseDto from(Item item) {
        ItemResponseDto dto = new ItemResponseDto();
        dto.id = item.getId();
        dto.itemCode = item.getItemCode();
        dto.itemName = item.getItemName();
        dto.spec = item.getSpec();
        dto.unit = item.getUnit();
        dto.unitPrice = item.getUnitPrice();
        dto.incentiveRate = item.getIncentiveRate();
        dto.useYn = item.getUseYn();
        dto.createdBy = item.getCreatedBy();
        dto.updatedBy = item.getUpdatedBy();
        dto.createdAt = item.getCreatedAt();
        dto.updatedAt = item.getUpdatedAt();
        return dto;
    }

}
