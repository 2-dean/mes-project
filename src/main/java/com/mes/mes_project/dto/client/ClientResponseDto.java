package com.mes.mes_project.dto.client;

import com.mes.mes_project.entity.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ClientResponseDto {
    // 조회할때 프론트에게 보내줄데이터
    private Long id; //수정/삭제시 필요

    private String clientCode;   // 거래처코드
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

    private String createdBy;    // 생성자
    private String updatedBy;    // 수정자
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일


    // Entity -> Dto 변환
    public static ClientResponseDto from(Client client) {
        ClientResponseDto dto = new ClientResponseDto();
        dto.id = client.getId();
        dto.clientCode = client.getClientCode();
        dto.clientName = client.getClientName();
        dto.bizNo = client.getBizNo();
        dto.clientType = client.getClientType();
        dto.tel = client.getTel();
        dto.zipCode = client.getZipCode();
        dto.address = client.getAddress();
        dto.addressDetail = client.getAddressDetail();
        dto.lat = client.getLat();
        dto.lng = client.getLng();
        dto.useYn = client.getUseYn();
        dto.createdBy = client.getCreatedBy();
        dto.updatedBy = client.getUpdatedBy();
        dto.createdAt = client.getCreatedAt();
        dto.updatedAt = client.getUpdatedAt();
        return dto;
    }
}
