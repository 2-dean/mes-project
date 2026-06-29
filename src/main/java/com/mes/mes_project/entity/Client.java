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
@Table(name = "MES_CLIENT")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_code", nullable = false, unique = true, length = 20)
    private String clientCode;   // 거래처코드

    @Column(name = "client_name", nullable = false, length = 100)
    private String clientName;   // 거래처명

    private String bizNo;        // 사업자번호
    private String clientType;   // 거래처유형 (매입/매출)
    private String tel;          // 전화번호
    private String zipCode;
    private String address;      // 주소
    private String addressDetail;// 상세주소
    private Double lat;          // 위도
    private Double lng;          // 경도
    private String useYn = "Y";  // 사용여부
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;    // 생성자

    @LastModifiedBy
    private String updatedBy;    // 수정자

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // 생성일

    @LastModifiedDate
    private LocalDateTime updatedAt; // 수정일

    public void update(String clientName, String bizNo, String clientType,
                       String tel, String zipCode, String address,
                       String addressDetail, Double lat, Double lng,
                       String useYn) {
        this.clientName = clientName;
        this.bizNo = bizNo;
        this.clientType = clientType;
        this.tel = tel;
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.lat = lat;
        this.lng = lng;
        this.useYn = useYn;
        // updatedBy, updatedAt → Auditing으로 나중에 자동화
    }

    public void delete() {
        this.useYn = "N";
        // updatedBy, updatedAt → Auditing으로 나중에 자동화
    }
}
