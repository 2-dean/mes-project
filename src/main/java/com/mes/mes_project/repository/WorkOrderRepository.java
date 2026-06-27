package com.mes.mes_project.repository;

import com.mes.mes_project.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

    // 일자 + 확정여부로 조회
    List<WorkOrder> findByPlanDateAndConfirmYn(LocalDate planDate, String confirmYn);
    /*
    SELECT * FROM MES_WORK_ORDER
    WHERE plan_date = ?
    AND confirm_yn = ?
     */

    // 해당 월 작업일자 목록 조회
    //JPQL사용
    @Query("SELECT DISTINCT w.planDate FROM WorkOrder w " +
            "WHERE FUNCTION('TO_CHAR', w.planDate, 'YYYY-MM') = :yearMonth")
    List<LocalDate> findDistinctPlanDateByYearMonth(
            @Param("yearMonth") String yearMonth
    );
}
