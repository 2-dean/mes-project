package com.mes.mes_project.service;

import com.mes.mes_project.entity.ProdResult;
import com.mes.mes_project.entity.WorkOrder;
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

    //1. 전체조회
    public List<ProdResult> findAll() {
        return prodResultRepository.findAll();
    }
    //3. 작업지시별 실적 조회
    public List<ProdResult> findByWorkOrderId (Long workOrderId) {
        return prodResultRepository.findByWorkOrderId(workOrderId);
    }

    //4. 스캔 (scan) - 오늘 실적 있으면 수량 누적, 없으면 자동생성
    @Transactional
    public void scan(Long workOrderId, String worker, int qty) {
        // 1. 작업지시 조회
        WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new RuntimeException("작업지시 없음"));

        // 2. 확정 여부 체크
        if ("Y".equals(workOrder.getConfirmYn())) {
            throw new RuntimeException("확정된 작업지시는 수정 불가");
        }

        // 3. 계획수량 초과 체크 ← 추가!
        int totalProdQty = prodResultRepository
                .findByWorkOrderId(workOrderId)
                .stream()
                .mapToInt(ProdResult::getTotalQty)
                .sum();

        if (totalProdQty + qty > workOrder.getPlanQty()) {
            throw new RuntimeException("계획수량 초과! 계획: "
                    + workOrder.getPlanQty() + "개, 현재: " + totalProdQty + "개");
        }

        // 4. 오늘 실적 조회 (없으면 자동생성)
        ProdResult result = prodResultRepository
                .findByWorkOrderIdAndWorkerAndProdDate(
                        workOrderId, worker, LocalDate.now()
                )
                .orElseGet(() -> {
                    return prodResultRepository.save(
                            ProdResult.create(workOrder, worker)
                    );
                });

        // 4. 수량 누적
        result.addScanQty(qty);

    }

    //5. 수동입력 (manual) - scan이랑 같은 로직
    @Transactional
    public void manual(Long workOrderId, String worker, int qty) {
        // 1. 작업지시 조회
        WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new RuntimeException("작업지시 없음"));

        // 2. 확정 여부 체크
        if ("Y".equals(workOrder.getConfirmYn())) {
            throw new RuntimeException("확정된 작업지시는 수정 불가");
        }
        // 3. 계획수량 초과 체크 ← 추가!
        int totalProdQty = prodResultRepository
                .findByWorkOrderId(workOrderId)
                .stream()
                .mapToInt(ProdResult::getTotalQty)
                .sum();

        if (totalProdQty + qty > workOrder.getPlanQty()) {
            throw new RuntimeException("계획수량 초과! 계획: "
                    + workOrder.getPlanQty() + "개, 현재: " + totalProdQty + "개");
        }

        // 4. 오늘 실적 조회 (없으면 자동생성)
        ProdResult result = prodResultRepository
                .findByWorkOrderIdAndWorkerAndProdDate(
                        workOrderId, worker, LocalDate.now()
                )
                .orElseGet(() -> {
                    return prodResultRepository.save(
                            ProdResult.create(workOrder, worker)
                    );
                });

        // 수기입력
        result.addManualQty(qty);
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

        result.delete(); // useYn = N
    }


}