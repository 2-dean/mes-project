package com.mes.mes_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MES_ITEM")
@Getter @Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동증가
    private Long id;

    @Column(name = "item_code", nullable = false, unique = true, length = 20)
    private String itemCode; // 제품코드

    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName; // 제품명

    private String spec; // 스펙
    private String unit; // 단위
    private int unitPrice; //단가
    private String useYn = "Y";  // 기본값 Y
    private String createdBy;   // 생성자
    private String updatedBy;   // 수정자
    private LocalDateTime createdAt;  // 생성일
    private LocalDateTime updatedAt;  // 수정일

    // 수정
    public void updateItem(String itemName, String spec,
                           String unit, int unitPrice, String useYn) {
        this.itemName = itemName;
        this.spec = spec;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.useYn = useYn;
    }

    // 삭제 (화면에서 삭제 = DB에서 사용여부 N)
    public void delete() {
        this.useYn = "N";
    }
}
