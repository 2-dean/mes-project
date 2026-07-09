package com.mes.mes_project.controller;

import com.mes.mes_project.dto.prodresult.ManualQtyUpdateDto;
import com.mes.mes_project.dto.prodresult.ScanRequestDto;
import com.mes.mes_project.entity.ProdResult;
import com.mes.mes_project.service.ProdResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prod-results")
@RequiredArgsConstructor
public class ProdResultController {

    private final ProdResultService prodResultService;

    // 조회 (품목/라인/생산일자 선택 조건, 모두 없으면 전체조회)
    @GetMapping()
    public List<ProdResult> search(
            @RequestParam(required = false) Long itemId,
            @RequestParam(required = false) String line,
            @RequestParam(required = false) String prodDate) {
        LocalDate date = (prodDate != null && !prodDate.isBlank()) ? LocalDate.parse(prodDate) : null;
        return prodResultService.search(itemId, line, date);
    }

    // 작업지시별 조회
    @GetMapping("/{workOrderId}")
    public List<ProdResult> findByWorkOrderId(@PathVariable Long workOrderId) {
        return prodResultService.findByWorkOrderId(workOrderId);
    }

    // 스캔
    @PostMapping("/scan")
    public void scan (@RequestBody ScanRequestDto dto) {
        prodResultService.scan(dto.getWorkOrderId(), dto.getWorker(), dto.getQty());
    }
    // 수동입력
    @PostMapping("/manual")
    public void manual (@RequestBody ScanRequestDto dto) {
        prodResultService.manual(dto.getWorkOrderId(), dto.getWorker(), dto.getQty());
    }
    // 수동수량 인라인 수정
    @PatchMapping("/{id}/manual-qty")
    public void updateManualQty(@PathVariable Long id, @RequestBody ManualQtyUpdateDto dto) {
        prodResultService.updateManualQty(id, dto.getManualQty());
    }
    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        prodResultService.delete(id);
    }
}
