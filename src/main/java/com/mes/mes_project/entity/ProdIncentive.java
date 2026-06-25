package com.mes.mes_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "MES_PROD_INCENTIVE")
@Getter
@NoArgsConstructor
public class ProdIncentive {

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

    private int qty;                // 수량

    private int unitPrice;          // 단가

    private Double incentiveRate;   // 인센티브율

    private Long amount;            // 인센티브 금액 (qty * unitPrice * incentiveRate)

    private String confirmYn = "N"; // 확정여부 (기본값 N)

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ──────────────────────────────────────────
    // 비즈니스 메서드
    // ──────────────────────────────────────────

    /**
     * 생성 메서드 — amount 는 qty * unitPrice * incentiveRate 로 자동 계산
     */
    public static ProdIncentive create(WorkOrder workOrder, String worker,
                                       int qty, int unitPrice, Double incentiveRate) {
        ProdIncentive incentive = new ProdIncentive();
        incentive.workOrder     = workOrder;
        incentive.worker        = worker;
        incentive.qty           = qty;
        incentive.unitPrice     = unitPrice;
        incentive.incentiveRate = incentiveRate;
        incentive.amount        = calcAmount(qty, unitPrice, incentiveRate);
        incentive.confirmYn     = "N";
        return incentive;
    }

    /**
     * 수정 메서드 — amount 자동 재계산
     */
    public void update(WorkOrder workOrder, String worker,
                       int qty, int unitPrice, Double incentiveRate) {
        this.workOrder     = workOrder;
        this.worker        = worker;
        this.qty           = qty;
        this.unitPrice     = unitPrice;
        this.incentiveRate = incentiveRate;
        this.amount        = calcAmount(qty, unitPrice, incentiveRate);
    }

    /**
     * 확정 — confirmYn 을 "Y" 로 변경
     */
    public void confirm() {
        this.confirmYn = "Y";
    }

    /**
     * 확정 취소 — confirmYn 을 "N" 으로 변경
     */
    public void cancelConfirm() {
        this.confirmYn = "N";
    }

    // amount = qty * unitPrice * incentiveRate
    private static long calcAmount(int qty, int unitPrice, Double incentiveRate) {
        if (incentiveRate == null) return 0L;
        return Math.round(qty * unitPrice * incentiveRate);
    }
}