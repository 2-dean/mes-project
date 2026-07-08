package com.mes.mes_project.service;

import com.mes.mes_project.entity.ProdResult;
import com.mes.mes_project.entity.WorkOrder;
import com.mes.mes_project.repository.DailyCloseRepository;
import com.mes.mes_project.repository.ProdResultRepository;
import com.mes.mes_project.repository.WorkOrderRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdResultService {

    private final ProdResultRepository prodResultRepository;
    private final WorkOrderRepository workOrderRepository;
    private final DailyCloseRepository dailyCloseRepository;

    //1. 전체조회
    public List<ProdResult> findAll() {
        return prodResultRepository.findAll();
    }
    //3. 작업지시별 실적 조회
    public List<ProdResult> findByWorkOrderId (Long workOrderId) {
        return prodResultRepository.findByWorkOrderIdAndUseYn(workOrderId, "Y");
    }

    // 품목(선택) + 라인(선택) + 생산일자(선택) 조건으로 조회
    public List<ProdResult> search(Long itemId, String line, LocalDate prodDate) {
        return prodResultRepository.search(itemId, line, prodDate);
    }

    //4. 스캔 (scan) - 오늘 실적 있으면 수량 누적, 없으면 자동생성
    @Transactional
    public void scan(Long workOrderId, String worker, int qty) {
        WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new RuntimeException("작업지시 없음"));

        if (!"IN_PROGRESS".equals(workOrder.getStatus())) {
            throw new RuntimeException("진행중 상태의 작업지시만 스캔 가능합니다.");
        }

        int totalProdQty = prodResultRepository
                .findByWorkOrderIdAndUseYn(workOrderId, "Y")
                .stream()
                .mapToInt(ProdResult::getTotalQty)
                .sum();

        if (totalProdQty + qty > workOrder.getPlanQty()) {
            throw new RuntimeException("계획수량 초과! 계획: "
                    + workOrder.getPlanQty() + "개, 현재: " + totalProdQty + "개");
        }

        ProdResult result = prodResultRepository
                .findByWorkOrderIdAndWorkerAndProdDateAndUseYn(workOrderId, worker, LocalDate.now(), "Y")
                .orElseGet(() -> prodResultRepository.save(ProdResult.create(workOrder, worker)));

        result.addScanQty(qty);

        if (totalProdQty + qty >= workOrder.getPlanQty()) {
            workOrder.done();
        }
    }

    //5. 수동입력 (manual)
    @Transactional
    public void manual(Long workOrderId, String worker, int qty) {
        WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new RuntimeException("작업지시 없음"));

        if (!"IN_PROGRESS".equals(workOrder.getStatus())) {
            throw new RuntimeException("진행중 상태의 작업지시만 수량 입력 가능합니다.");
        }

        int totalProdQty = prodResultRepository
                .findByWorkOrderIdAndUseYn(workOrderId, "Y")
                .stream()
                .mapToInt(ProdResult::getTotalQty)
                .sum();

        if (totalProdQty + qty > workOrder.getPlanQty()) {
            throw new RuntimeException("계획수량 초과! 계획: "
                    + workOrder.getPlanQty() + "개, 현재: " + totalProdQty + "개");
        }

        ProdResult result = prodResultRepository
                .findByWorkOrderIdAndWorkerAndProdDateAndUseYn(workOrderId, worker, LocalDate.now(), "Y")
                .orElseGet(() -> prodResultRepository.save(ProdResult.create(workOrder, worker)));

        result.addManualQty(qty);

        if (totalProdQty + qty >= workOrder.getPlanQty()) {
            workOrder.done();
        }
    }


    //6. 삭제 (useYn = N)
    @Transactional
    public void delete(Long id) {
        ProdResult result = prodResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("실적 없음"));
        // 확정 여부 체크
        if ("Y".equals(result.getWorkOrder().getConfirmYn())) {
            throw new RuntimeException("확정된 작업지시는 삭제 불가");
        }

        // 스캔수량 체크 추가!
        if (result.getScanQty() > 0) {
            throw new RuntimeException("스캔 실적이 있는 행은 삭제할 수 없습니다");
        }
        result.delete(); // useYn = N
    }

    //7. 수동수량 인라인 수정
    @Transactional
    public void updateManualQty(Long id, int manualQty) {
        ProdResult result = prodResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("실적 없음"));

        assertEditable(result, "수정");

        WorkOrder workOrder = result.getWorkOrder();
        int otherTotalQty = prodResultRepository
                .findByWorkOrderIdAndUseYn(workOrder.getId(), "Y")
                .stream()
                .filter(r -> !r.getId().equals(id))
                .mapToInt(ProdResult::getTotalQty)
                .sum();
        int newRowTotal = result.getScanQty() + manualQty;

        if (otherTotalQty + newRowTotal > workOrder.getPlanQty()) {
            throw new RuntimeException("계획수량 초과! 계획: "
                    + workOrder.getPlanQty() + "개, 현재: " + otherTotalQty + "개");
        }

        result.changeManualQty(manualQty);

        if (otherTotalQty + newRowTotal >= workOrder.getPlanQty()) {
            workOrder.done();
        }
    }

    // 확정/일마감 여부 체크 (마감·확정된 실적은 ADMIN이라도 수정/삭제 불가)
    private void assertEditable(ProdResult result, String action) {
        if ("Y".equals(result.getWorkOrder().getConfirmYn())) {
            throw new RuntimeException("확정된 작업지시는 " + action + " 불가");
        }
        boolean closed = dailyCloseRepository.findByCloseDate(result.getProdDate())
                .map(dc -> "Y".equals(dc.getCloseYn()))
                .orElse(false);
        if (closed) {
            throw new RuntimeException("일마감된 생산실적은 " + action + " 불가");
        }
    }

}