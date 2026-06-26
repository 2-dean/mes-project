package com.mes.mes_project.contorller;

import com.mes.mes_project.entity.DailyClose;
import com.mes.mes_project.service.DailyCloseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily-close")
public class DailyCloseController {
    private final DailyCloseService dailyCloseService;

    // 일마감
    @PostMapping
    public void dailyClose(@RequestBody Map<String, String> request) {
        LocalDate closeDate = LocalDate.parse(request.get("closeDate"));
        String closedBy = request.get("closedBy");
        dailyCloseService.dailyClose(closeDate, closedBy);
    }

    // 일마감 취소
    @DeleteMapping
    public void cancelDailyClose(@RequestBody Map<String, String> request) {
        LocalDate closeDate = LocalDate.parse(request.get("closeDate"));
        dailyCloseService.cancelDailyClose(closeDate);
    }

    // 전체조회
    @GetMapping
    public List<DailyClose> findAll() {
        return dailyCloseService.findAll();
    }
}
