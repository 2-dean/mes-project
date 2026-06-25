package com.mes.mes_project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScanRequestDto {
    private Long workOrderId;
    private String worker;
    private int qty;
}
