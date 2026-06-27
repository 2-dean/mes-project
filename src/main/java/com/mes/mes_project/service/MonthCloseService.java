package com.mes.mes_project.service;

import com.mes.mes_project.entity.DailyClose;
import com.mes.mes_project.entity.MonthClose;
import com.mes.mes_project.repository.DailyCloseRepository;
import com.mes.mes_project.repository.MonthCloseRepository;
import com.mes.mes_project.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthCloseService {

    private final MonthCloseRepository monthCloseRepository;
    private final DailyCloseRepository dailyCloseRepository;
    private final WorkOrderRepository workOrderRepository;

    // 월마감
    @Transactional
    public void monthClose(String yearMonth, String closedBy) {
        // 1. 이미 마감됐는지 확인
        monthCloseRepository.findByYearMonth(yearMonth)
                .ifPresent(close -> {
                    if ("Y".equals(close.getCloseYn())) {
                        throw new RuntimeException("이미 마감된 월입니다");
                    }
                });

        // 2. 해당 월에 작업지시가 있는 날짜 조회
        List<LocalDate> workDates = workOrderRepository
                .findDistinctPlanDateByYearMonth(yearMonth);

        // 3. 작업한 날 중 일마감 안 된 날 체크
        for (LocalDate workDate : workDates) {
            // 일마감 데이터가 아예없는경우
            DailyClose dailyClose = dailyCloseRepository
                    .findByCloseDate(workDate)
                    .orElseThrow(() -> new RuntimeException(
                            workDate + " 일마감이 되지 않았습니다"
                    ));
            // 일 마감 후 취소한 것이있는경우
            if ("N".equals(dailyClose.getCloseYn())) {
                throw new RuntimeException(
                        workDate + " 일마감이 되지 않았습니다"
                );
            }
        }

        // 4. 월마감 저장
        MonthClose monthClose = new MonthClose();
        monthClose.close(closedBy, yearMonth);
        monthCloseRepository.save(monthClose);
    }

    // 월마감 취소
    @Transactional
    public void cancelMonthClose(String yearMonth) {
        MonthClose monthClose = monthCloseRepository
                .findByYearMonth(yearMonth)
                .orElseThrow(() -> new RuntimeException("마감 정보 없음"));
        // 이미 해당객체가 뭔지 알고있음
        monthClose.cancelClose();
    }
    // 전체조회
    public List<MonthClose> findAll() {
        return monthCloseRepository.findAll();
    }
}