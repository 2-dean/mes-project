package com.mes.mes_project.dto;

import com.mes.mes_project.entity.Item;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemRequestDto {
    // 등록/수정할 때 받는 것

    @Size(max = 20, message = "품목코드는 20자 이하로 입력해주세요")
    private String itemCode;

    @Size(max = 100, message = "품목명은 100자 이하로 입력해주세요")
    private String itemName; // 제품명

    @Size(max = 200, message = "규격은 200자 이하로 입력해주세요")
    private String spec; // 스펙

    @Size(max = 10, message = "단위는 10자 이하로 입력해주세요")
    private String unit; // 단위
    private int unitPrice; //단가
    private int incentiveRate; // 인센티브

    private String useYn = "Y";  // 기본값 Y

    //DTO -> Entity 변환
    public Item toEntity() {
        Item item = new Item();
        item.setItemCode(this.itemCode);
        item.setItemName(this.itemName);
        item.setSpec(this.spec);
        item.setUnit(this.unit);
        item.setUnitPrice(this.unitPrice);
        item.setIncentiveRate(this.incentiveRate);
        item.setUseYn(this.useYn);
        return item;
    }
}
