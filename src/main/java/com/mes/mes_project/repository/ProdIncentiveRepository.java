package com.mes.mes_project.repository;

import com.mes.mes_project.entity.ProdIncentive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProdIncentiveRepository extends JpaRepository<ProdIncentive, Long> {
    // 작업지시별 조회
    List<ProdIncentive> findByWorkOrderId(Long workOrderId);

    // 작업지시 + 작업자로 조회
    Optional<ProdIncentive> findByWorkOrderIdAndWorker(
            Long workOrderId, String worker
    );

    void deleteByCloseDate(LocalDate closeDate);
}
