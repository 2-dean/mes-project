package com.mes.mes_project.repository;

import com.mes.mes_project.entity.DailyClose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyCloseRepository extends JpaRepository<DailyClose, Long> {

    Optional<DailyClose> findByCloseDate(LocalDate closeDate);

}
