package com.mes.mes_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "MES_MONTH_CLOSE")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class MonthClose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 7)
    private String yearMonth;       // 마감년월 ex) "2024-06"

    @Column(length = 1)
    private String closeYn = "N";   // 마감여부

    @Column(length = 50)
    private String closedBy;        // 마감자

    private LocalDateTime closedAt; // 마감일시

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void close(String closedBy, String yearMonth) {
        this.closeYn = "Y";
        this.yearMonth = yearMonth;
        this.closedBy = closedBy;
        this.closedAt = LocalDateTime.now();
    }

    public void cancelClose() {
        this.closeYn = "N";
        this.closedBy = null;
        this.closedAt = null;
    }
}