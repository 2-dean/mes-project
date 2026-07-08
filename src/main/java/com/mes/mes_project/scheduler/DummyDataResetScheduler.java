package com.mes.mes_project.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * 포폴 시연용 - 매일 자정 더미데이터 리셋
 * data.sql을 재실행해 기존 더미데이터를 지우고 원본 상태로 재생성한 뒤,
 * 작업지시 계획일자(plan_date)만 오늘 날짜 기준으로 이동시킨다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DummyDataResetScheduler {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    // data.sql 원본 데이터의 작업지시(IN_PROGRESS) 기준일
    private static final String BASE_DATE = "2024-07-05";

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    public void resetDummyData() {
        log.info("[DummyDataReset] 더미데이터 리셋 시작");

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("data.sql"));
        populator.execute(dataSource);

        jdbcTemplate.update(
                "UPDATE MES_WORK_ORDER SET plan_date = plan_date + (CURRENT_DATE - DATE '" + BASE_DATE + "')"
        );

        log.info("[DummyDataReset] 더미데이터 리셋 완료");
    }
}
