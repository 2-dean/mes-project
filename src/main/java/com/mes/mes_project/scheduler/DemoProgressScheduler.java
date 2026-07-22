package com.mes.mes_project.scheduler;

import com.mes.mes_project.entity.Item;
import com.mes.mes_project.entity.ProdResult;
import com.mes.mes_project.entity.WorkOrder;
import com.mes.mes_project.repository.ItemRepository;
import com.mes.mes_project.repository.ProdResultRepository;
import com.mes.mes_project.repository.WorkOrderRepository;
import com.mes.mes_project.service.DailyCloseService;
import com.mes.mes_project.service.WorkOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 포폴 시연용 - 매일 자정 데모 데이터 자동 진행
 * 전날 작업지시를 완료/확정/마감 처리하고, 오늘자 신규 작업지시와 작업자별 스캔실적을 생성한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DemoProgressScheduler {

    private final WorkOrderRepository workOrderRepository;
    private final ItemRepository itemRepository;
    private final ProdResultRepository prodResultRepository;
    private final WorkOrderService workOrderService;
    private final DailyCloseService dailyCloseService;

    private static final String[] LINES = {"A라인", "B라인", "C라인"};
    private static final String[] WORKERS = {"user1", "user2", "user3"};
    private static final int NEW_WORK_ORDER_COUNT = 2;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    @Transactional
    public void runDailyDemoProgress() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate today = LocalDate.now();

        log.info("[DemoProgress] 시작 - 전날({}) 마감 처리, 오늘({}) 데이터 생성", yesterday, today);

        completeInProgressWorkOrders(yesterday);
        confirmDoneWorkOrders(yesterday);
        closeDailyAndCalcIncentive(yesterday);
        List<WorkOrder> newWorkOrders = createTodayWorkOrders(today);
        createTodayScanResults(newWorkOrders);

        log.info("[DemoProgress] 완료");
    }

    // 1. 전날 IN_PROGRESS 작업지시 자동완료 (status = DONE)
    private void completeInProgressWorkOrders(LocalDate targetDate) {
        List<WorkOrder> workOrders = workOrderRepository.search(targetDate, targetDate, "IN_PROGRESS", null);
        workOrders.forEach(WorkOrder::done);
        log.info("[DemoProgress] 전날 작업완료 처리: {}건", workOrders.size());
    }

    // 2. 전날 DONE + confirmYn=N 작업지시 자동확정 (confirmYn = Y)
    private void confirmDoneWorkOrders(LocalDate targetDate) {
        List<WorkOrder> workOrders = workOrderRepository.search(targetDate, targetDate, "DONE", "N");
        workOrders.forEach(WorkOrder::confirm);
        log.info("[DemoProgress] 전날 작업확정 처리: {}건", workOrders.size());
    }

    // 3, 4. 전날 일마감 + 인센티브 자동계산 (DailyCloseService가 두 처리를 함께 수행)
    private void closeDailyAndCalcIncentive(LocalDate targetDate) {
        try {
            dailyCloseService.dailyClose(targetDate, "SYSTEM");
            log.info("[DemoProgress] 전날 일마감/인센티브 계산 완료: {}", targetDate);
        } catch (RuntimeException e) {
            log.info("[DemoProgress] 전날 일마감 스킵 - {}", e.getMessage());
        }
    }

    // 5. 오늘 날짜로 새 작업지시 2개 자동생성 (품목/라인 랜덤, status = IN_PROGRESS, 계획수량 200~500 랜덤)
    private List<WorkOrder> createTodayWorkOrders(LocalDate targetDate) {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            log.warn("[DemoProgress] 품목 데이터 없음 - 작업지시 생성 스킵");
            return List.of();
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<WorkOrder> created = new ArrayList<>();

        for (int i = 0; i < NEW_WORK_ORDER_COUNT; i++) {
            Item item = items.get(random.nextInt(items.size()));
            String line = LINES[random.nextInt(LINES.length)];
            int planQty = random.nextInt(200, 501);

            WorkOrder workOrder = new WorkOrder();
            workOrder.update(item, planQty, targetDate, "WAIT", line, item.getItemName() + " 생산", "Y");

            WorkOrder saved = workOrderService.save(workOrder);
            saved.startWork(); // WAIT → IN_PROGRESS
            created.add(saved);
        }

        log.info("[DemoProgress] 오늘 신규 작업지시 {}건 생성", created.size());
        return created;
    }

    // 6. 오늘 작업지시 작업자별 스캔수량 자동생성 (user1~3, 계획수량의 30~60% 랜덤)
    private void createTodayScanResults(List<WorkOrder> workOrders) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int count = 0;

        for (WorkOrder workOrder : workOrders) {
            for (String worker : WORKERS) {
                int scanQty = (int) (workOrder.getPlanQty() * (random.nextInt(30, 61) / 100.0));
                if (scanQty <= 0) {
                    continue;
                }

                ProdResult result = ProdResult.create(workOrder, worker);
                result.addScanQty(scanQty);
                prodResultRepository.save(result);
                count++;
            }
        }

        log.info("[DemoProgress] 오늘 작업자별 스캔실적 {}건 생성", count);
    }
}
