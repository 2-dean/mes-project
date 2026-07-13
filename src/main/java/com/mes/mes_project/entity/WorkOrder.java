package com.mes.mes_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "MES_WORK_ORDER")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "work_order_no", nullable = false, unique = true, length = 30)
    private String workOrderNo;     // 작업지시번호

    // LAZY라서 Item을 JSON으로 변환할 때 오류 남
    @ManyToOne(fetch = FetchType.LAZY) // 작업지시 여러개 > 품목하나 LAZY  → 작업지시 조회할 때 품목은 필요할 때만 조회
    @JoinColumn(name = "item_id", nullable = false) // 테이블에 item_id 컬럼이 생기고 mes_item 테이블의 id 컬럼을 참조함
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Item item;              // 품목 (FK → MES_ITEM)

    private Integer planQty;        // 계획수량

    private LocalDate planDate;     // 작업일자

    @Column(length = 20)
    private String status = "WAIT"; // 상태: WAIT / IN_PROGRESS / DONE

    @Column(length = 50)
    private String line;            // 생산라인

    @Column(length = 255)
    private String remark;          // 비고

    private String useYn = "Y";
    private String confirmYn = "N"; // 확정여부

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 대기 상태로 초기화
    public void initStatus() {
        this.status = "WAIT";
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public void startWork() {
        this.status = "IN_PROGRESS";
    }

    public void done() {
        this.status = "DONE";
    }

    // 확정 메서드
    public void confirm() {
        this.confirmYn = "Y";
    }

    // 확정취소 메서드
    public void cancelConfirm() {
        this.confirmYn = "N";
    }

    public void update(Item item, Integer planQty, LocalDate planDate,
                       String status, String line, String remark, String useYn) {
        this.item = item;
        this.planQty = planQty;
        this.planDate = planDate;
        this.status = status;
        this.line = line;
        this.remark = remark;
        this.useYn = useYn;
    }

    public void delete() {
        this.useYn = "N";
    }
}