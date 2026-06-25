package com.mes.mes_project.repository;

import com.mes.mes_project.entity.ProdResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdResultRepository extends JpaRepository<ProdResult, Long> {
    //JPA가 메서드 이름 보고 자동으로 쿼리 만듬

    // 작업지시별 조회
    List<ProdResult> findByWorkOrderId(Long workOrderId);


    // 작업지시 + 작업자 + 날짜로 조회 (스캔할 때 사용)
    Optional<ProdResult> findByWorkOrderIdAndWorkerAndProdDate(
            Long workOrderId, String worker, LocalDate prodDate
    );
}
