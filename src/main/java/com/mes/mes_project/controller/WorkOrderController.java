package com.mes.mes_project.controller;

import com.mes.mes_project.dto.workorder.WorkOrderListDto;
import com.mes.mes_project.dto.workorder.WorkOrderSearchDto;
import com.mes.mes_project.entity.WorkOrder;
import com.mes.mes_project.service.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workorders")
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    // 등록
    @PostMapping
    public WorkOrder save(@RequestBody WorkOrder workOrder) {
        return workOrderService.save(workOrder);
    }

    // 조회 (검색조건)
    @GetMapping
    public List<WorkOrderListDto> search(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String confirmYn) {

        WorkOrderSearchDto searchDto = new WorkOrderSearchDto();
        searchDto.setStartDate(startDate);
        searchDto.setEndDate(endDate);
        searchDto.setStatus(status);
        searchDto.setConfirmYn(confirmYn);
        return workOrderService.search(searchDto);
    }

    // 단건조회
    @GetMapping("/{id}")
    public WorkOrder findById(@PathVariable Long id) {
        return workOrderService.findById(id);
    }

    // 수정
    @PutMapping("/{id}")
    public WorkOrder update(@PathVariable Long id,
                            @RequestBody WorkOrder workOrder) {
        return workOrderService.update(id, workOrder);
    }
    // 작업시작
    @PatchMapping("/{id}/start")
    public void startWork(@PathVariable Long id) {
        workOrderService.startWork(id);
    }

    // 확정
    @PatchMapping("/{id}/confirm")
    public void confirm(@PathVariable Long id) {
        workOrderService.confirm(id);
    }

    // 확정취소
    @PatchMapping("/{id}/cancel-confirm")
    public void cancelConfirm(@PathVariable Long id) {
        workOrderService.cancelConfirm(id);
    }
    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workOrderService.delete(id);
    }
}