package com.mes.mes_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "MES_ITEM")
@Getter @Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // Entity가 저장/수정될 때 JPA한테 알려줌, 저장 전 Auditing처리
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
    private int incentiveRate; // 인센티브율 (%)
    private String useYn = "Y";  // 기본값 Y

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;   // 생성자

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;  // 생성일

    @LastModifiedBy
    private String updatedBy;   // 수정자
    @LastModifiedDate
    private LocalDateTime updatedAt;  // 수정일

    // 수정
    public void update(String itemName, String spec,
                       String unit, int unitPrice,
                       int incentiveRate, String useYn) {
        this.itemName = itemName;
        this.spec = spec;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.incentiveRate = incentiveRate; // 인센티브추가
        this.useYn = useYn;
    }
    // 삭제 (화면에서 삭제 = DB에서 사용여부 N)
    public void delete() {
        this.useYn = "N";
    }
}
