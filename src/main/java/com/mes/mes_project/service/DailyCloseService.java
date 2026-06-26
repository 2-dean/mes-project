package com.mes.mes_project.service;

import com.mes.mes_project.entity.DailyClose;
import com.mes.mes_project.entity.ProdIncentive;
import com.mes.mes_project.entity.ProdResult;
import com.mes.mes_project.entity.WorkOrder;
import com.mes.mes_project.repository.DailyCloseRepository;
import com.mes.mes_project.repository.ProdIncentiveRepository;
import com.mes.mes_project.repository.ProdResultRepository;
import com.mes.mes_project.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyCloseService {

    private final DailyCloseRepository dailyCloseRepository;
    private final WorkOrderRepository workOrderRepository;
    private final ProdResultRepository prodResultRepository;
    private final ProdIncentiveRepository prodIncentiveRepository;

    // 일마감
    @Transactional
    public void dailyClose(LocalDate closeDate, String closedBy) {
        // 1. 이미 마감됐는지 체크
        dailyCloseRepository.findByCloseDate(closeDate)
                .ifPresent(close -> {
                    if ("Y".equals(close.getCloseYn())) {
                        throw new RuntimeException("이미 마감된 날짜입니다.");
                    }
                });

        // 2. 오늘 확정된 작업지시 조회
        List<WorkOrder> workOrders = workOrderRepository.findByPlanDateAndConfirmYn(closeDate, "Y");

        // 3. 작업자별 인센티브 계산 → ProdIncentive 저장
        for (WorkOrder workOrder : workOrders) {
            // 작업결과 테이블
            List<ProdResult> results = prodResultRepository.findByWorkOrderId(workOrder.getId());
            for (ProdResult result : results) {
                // 인센티브 금액 = 총 생산수량 × 단가 × 인센티브율
                int qty = result.getTotalQty();
                int unitPrice = workOrder.getItem().getUnitPrice();
                int incentiveRate = workOrder.getItem().getIncentiveRate();

                //long amount = (long)(qty * unitPrice * (incentiveRate / 100));

                ProdIncentive incentive = ProdIncentive.create(
                        workOrder, result.getWorker(),
                        qty, unitPrice, incentiveRate, closeDate
                );
                prodIncentiveRepository.save(incentive);
            }
        }
        // 4. DailyClose 저장

        DailyClose dailyClose = dailyCloseRepository
                                .findByCloseDate(closeDate)
                                .orElse(new DailyClose());
        dailyClose.close(closedBy, closeDate);
        dailyCloseRepository.save(dailyClose);


    }

    // 일마감 취소
    @Transactional
    public void cancelDailyClose(LocalDate closeDate) {
        // 1. 마감 취소
        DailyClose dailyClose = dailyCloseRepository.findByCloseDate(closeDate)
                .orElseThrow(() -> new RuntimeException("마감 정보 없음"));
        dailyClose.cancelClose();
        // 2. 해당일 ProdIncentive 삭제
        prodIncentiveRepository.deleteByCloseDate(closeDate);
    }
    // 전체조회
    public List<DailyClose> findAll() {
        return dailyCloseRepository.findAll();
    }
}
