package com.mes.mes_project.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemSearchDto {
    private String itemName;   // 품목명 (부분검색)
    private String useYn;      // 사용여부
}
