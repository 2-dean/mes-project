package com.mes.mes_project.contorller;

import com.mes.mes_project.entity.WorkOrder;
import com.mes.mes_project.service.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // 전체조회
    @GetMapping
    public List<WorkOrder> findAll() {
        return workOrderService.findAll();
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

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workOrderService.delete(id);
    }
}