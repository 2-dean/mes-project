package com.mes.mes_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "MES_COMMON_CODE")
@Getter @Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommonCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private CodeGroup group;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "code_name", nullable = false, length = 50)
    private String codeName;

    @Column(length = 200)
    private String description;

    @Column(name = "sort_order")
    private int sortOrder;

    @Column(name = "use_yn", length = 1)
    private String useYn = "Y";

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void update(String codeName, String description, int sortOrder, String useYn) {
        this.codeName = codeName;
        this.description = description;
        this.sortOrder = sortOrder;
        this.useYn = useYn;
    }
}
