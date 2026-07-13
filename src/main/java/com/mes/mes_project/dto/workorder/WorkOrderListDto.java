package com.mes.mes_project.dto.workorder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkOrderListDto {
    private Long id;
    private String workOrderNo; // 작업지시번호
    private String itemCode;    // 품목코드
    private String itemName;    // 품목명
    private Integer planQty;    // 계획수량
    private String planDate;    // 작업일자
    private String status;      // 상태
    private String line;        // 생산라인
    private String remark;      // 비고
    private String confirmYn;   // 확정여부
    private String createdBy;   // 생성자
    private String createdAt;   // 생성일시
    private String updatedBy;   // 수정자
    private String updatedAt;   // 수정일시
}
