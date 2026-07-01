package com.mes.mes_project.dto.workorder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkOrderSearchDto {
    private String startDate;  // 시작일자
    private String endDate;    // 종료일자
    private String status;     // 상태
    private String confirmYn;  // 확정여부
}
