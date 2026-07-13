package com.mes.mes_project.repository;

import com.mes.mes_project.entity.ProdResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdResultRepository extends JpaRepository<ProdResult, Long> {
    //JPA가 메서드 이름 보고 자동으로 쿼리 만듬

    // 작업지시별 조회 (삭제된 실적 제외)
    List<ProdResult> findByWorkOrderIdAndUseYn(Long workOrderId, String useYn);

    // 작업지시 + 작업자 + 날짜로 조회 (스캔할 때 사용, 삭제된 실적 제외)
    Optional<ProdResult> findByWorkOrderIdAndWorkerAndProdDateAndUseYn(
            Long workOrderId, String worker, LocalDate prodDate, String useYn
    );

    // 품목(선택) + 라인(선택) + 생산일자(선택) 조건으로 조회 (삭제된 실적 제외)
    @Query("SELECT r FROM ProdResult r WHERE r.useYn = 'Y'" +
           " AND (:itemId   IS NULL OR r.workOrder.item.id = :itemId)" +
           " AND (:line     IS NULL OR r.workOrder.line = :line)" +
           " AND (CAST(:prodDate AS date) IS NULL OR r.prodDate = :prodDate)" +
           " ORDER BY r.workOrder.workOrderNo , r.workOrder.item.itemName, r.worker ")
    List<ProdResult> search(
            @Param("itemId")   Long itemId,
            @Param("line")     String line,
            @Param("prodDate") LocalDate prodDate
    );
}
