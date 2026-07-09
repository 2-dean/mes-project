package com.mes.mes_project.dto.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientSearchDto {
    private String clientName;  // 거래처명 (부분검색)
    private String useYn;       // 사용여부
}
