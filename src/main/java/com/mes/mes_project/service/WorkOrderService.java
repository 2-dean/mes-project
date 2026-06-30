package com.mes.mes_project.service;

import com.mes.mes_project.entity.Item;
import com.mes.mes_project.entity.WorkOrder;
import com.mes.mes_project.repository.ItemRepository;
import com.mes.mes_project.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final ItemRepository itemRepository;

    // 등록
    public WorkOrder save(WorkOrder workOrder) {
        return workOrderRepository.save(workOrder);
    }

    // 조회 (전체)
    public List<WorkOrder> findAll() {
        return workOrderRepository.findAll();
    }

    // 조회 (검색조건)
    public List<WorkOrder> search(LocalDate startDate, LocalDate endDate,
                                  String status, String confirmYn) {
        return workOrderRepository.search(startDate, endDate, status, confirmYn);
    }

    // 단건조회
    public WorkOrder findById(Long id) {
        return workOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("업무 지시 없음"));
    }

    // 수정
    @Transactional
    public WorkOrder update(Long id, WorkOrder updateWorkOrder) {
        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("업무 지시 없음"));

        Item item = itemRepository.findById(updateWorkOrder.getItem().getId())
                .orElseThrow(() -> new RuntimeException("품목 없음"));

        workOrder.update(
                item,
                updateWorkOrder.getPlanQty(),
                updateWorkOrder.getPlanDate(),
                updateWorkOrder.getStatus(),
                updateWorkOrder.getLine(),
                updateWorkOrder.getRemark(),
                updateWorkOrder.getUseYn()
        );

        return workOrder;
    }

    // 확정
    @Transactional
    public void confirm(Long id) {
        WorkOrder workOrder = findById(id);
        workOrder.confirm(); // confirmYn = Y
        // 인센티브 계산 추가
    }

    // 확정취소
    @Transactional
    public void cancelConfirm(Long id) {
        WorkOrder workOrder = findById(id);
        workOrder.cancelConfirm(); // confirmYn = N
    }
    // 삭제
    @Transactional
    public void delete(Long id) {
        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("업무 지시 없음"));
        workOrder.delete();
    }
}
