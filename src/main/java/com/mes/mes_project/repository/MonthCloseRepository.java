package com.mes.mes_project.repository;

import com.mes.mes_project.entity.MonthClose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthCloseRepository extends JpaRepository<MonthClose, Long> {
    Optional<MonthClose> findByYearMonth(String yearMonth);
}

