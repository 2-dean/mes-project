package com.mes.mes_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MES_CLIENT")
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
    private Double laxt;          // 위도
    private Double lng;          // 경도
    private String useYn = "Y";  // 사용여부
    private String createdBy;    // 생성자
    private String updatedBy;    // 수정자
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일

    public void update(String clientName, String bizNo, String clientType,
                       String tel, String zipCode, String address,
                       String addressDetail, Double laxt, Double lng,
                       String useYn, String updatedBy) {
        this.clientName = clientName;
        this.bizNo = bizNo;
        this.clientType = clientType;
        this.tel = tel;
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.laxt = laxt;
        this.lng = lng;
        this.useYn = useYn;
        // updatedBy, updatedAt → Auditing으로 나중에 자동화
    }

    public void delete(String updatedBy) {
        this.useYn = "N";
        // updatedBy, updatedAt → Auditing으로 나중에 자동화
    }
}
