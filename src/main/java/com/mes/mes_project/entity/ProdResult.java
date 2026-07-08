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
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "MES_PROD_RESULT",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_prod_result_order_worker_date",
                columnNames = {"work_order_id", "worker", "prod_date"}
        )
)
@Getter
@NoArgsConstructor
public class ProdResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작업지시 (FK → MES_WORK_ORDER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private WorkOrder workOrder;

    @Column(length = 50)
    private String worker;          // 작업자

    private int scanQty   = 0;      // 스캔수량 (기본값 0)
    private int manualQty = 0;      // 수동입력수량 (기본값 0)
    private int totalQty;           // 합계수량 (scanQty + manualQty 자동계산)

    @Column(name = "prod_date")
    private LocalDate prodDate;     // 생산일자

    @Column(length = 255)
    private String remark;          // 비고

    private String useYn = "Y";

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

    // ──────────────────────────────────────────
    // 비즈니스 메서드
    // ──────────────────────────────────────────

    /**
     * 생성 메서드
     */
    public static ProdResult create(WorkOrder workOrder, String worker) {
        ProdResult result = new ProdResult();
        result.workOrder = workOrder;
        result.worker = worker;
        result.prodDate = LocalDate.now();
        result.scanQty = 0;
        result.manualQty = 0;
        result.totalQty = 0;
        result.useYn = "Y";
        return result;
    }


    /**
     * 기본 정보 수정
     * totalQty 는 scanQty + manualQty 로 자동 계산됩니다.
     */
    public void update(WorkOrder workOrder, String worker, int scanQty, int manualQty,
                       LocalDate prodDate, String remark, String useYn) {
        this.workOrder = workOrder;
        this.worker    = worker;
        this.scanQty   = scanQty;
        this.manualQty = manualQty;
        this.totalQty  = scanQty + manualQty;
        this.prodDate  = prodDate;
        this.remark    = remark;
        this.useYn     = useYn;
    }

    /**
     * 소프트 삭제 — useYn 을 "N" 으로 변경
     */
    public void delete() {
        this.useYn = "N";
    }

    /**
     * 스캔수량 증가 — 바코드 스캔 1건당 호출
     * totalQty 를 즉시 재계산합니다.
     */
    public void addScanQty(int qty) {
        this.scanQty  += qty;
        this.totalQty  = this.scanQty + this.manualQty;
    }

    /**
     * 수동입력수량 증가 — 수동 입력 시 호출
     * totalQty 를 즉시 재계산합니다.
     */
    public void addManualQty(int qty) {
        this.manualQty += qty;
        this.totalQty   = this.scanQty + this.manualQty;
    }

    /**
     * 수동입력수량 직접 수정 — 그리드 인라인 수정 시 호출
     * totalQty 를 즉시 재계산합니다.
     */
    public void changeManualQty(int manualQty) {
        this.manualQty = manualQty;
        this.totalQty  = this.scanQty + this.manualQty;
    }

    // 인센티브 금액 계산 캡슐화 할경우!!
    /*
    public long calculateAmount() {
        // 총 생산수량 × 단가 × 인센티브율
        // = 인센티브 금액
        int qty = this.totalQty;
        int unitPrice = this.workOrder.getItem().getUnitPrice();
        Double rate = this.workOrder.getItem().getIncentiveRate();

        // rate null 체크!
        if (rate == null) return 0L;

        return (long)(qty * unitPrice * (rate / 100));
    }*/
}