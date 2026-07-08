package com.mes.mes_project.service;

import com.mes.mes_project.dto.workorder.WorkOrderListDto;
import com.mes.mes_project.dto.workorder.WorkOrderSearchDto;
import com.mes.mes_project.entity.Item;
import com.mes.mes_project.entity.WorkOrder;
import com.mes.mes_project.mapper.WorkOrderMapper;
import com.mes.mes_project.repository.ItemRepository;
import com.mes.mes_project.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final ItemRepository itemRepository;
    private final WorkOrderMapper workOrderMapper;

    // 등록
    public WorkOrder save(WorkOrder workOrder) {
        String workOrderNo = generateWorkOrderNo();
        workOrder.setWorkOrderNo(workOrderNo);
        return workOrderRepository.save(workOrder);
    }

    // 검색 (MyBatis)
    public List<WorkOrderListDto> search(WorkOrderSearchDto searchDto) {
        return workOrderMapper.search(searchDto);
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

        if ("IN_PROGRESS".equals(workOrder.getStatus()) || "DONE".equals(workOrder.getStatus())) {
            throw new RuntimeException("진행중 또는 완료된 작업지시는 수정할 수 없습니다.");
        }
        if ("Y".equals(workOrder.getConfirmYn())) {
            throw new RuntimeException("확정된 작업지시는 수정할 수 없습니다.");
        }

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

    // 작업시작 (WAIT → IN_PROGRESS)
    @Transactional
    public void startWork(Long id) {
        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("작업지시 없음"));
        workOrder.startWork();
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

    private String generateWorkOrderNo() {
        // WO-YYYY-MM-001~
        String prefix = "WO-" +  LocalDate.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM"));

        // 이번달 마지막 번호 조회
        String lastNo = workOrderRepository.findLastWorkOrderNoByPrefix(prefix);

        // 다음번호 생성
        int nextSeq = 1;
        if(lastNo != null) {
            nextSeq = Integer.parseInt(lastNo.substring(lastNo.lastIndexOf("-") + 1)) + 1;
        }

        // WO-2026-07-001 형식으로 반환
        return String.format("%s-%03d", prefix, nextSeq);
    }
}
