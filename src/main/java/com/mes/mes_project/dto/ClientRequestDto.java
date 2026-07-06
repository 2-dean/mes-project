package com.mes.mes_project.dto;

import com.mes.mes_project.entity.Client;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientRequestDto {
    // 등록/수정할 때 받는 것

    @Size(max = 20, message = "거래처코드는 20자 이하로 입력해주세요")
    private String clientCode;

    @Size(max = 100, message = "거래처명은 100자 이하로 입력해주세요")
    private String clientName;

    @Size(max = 10, message = "사업자번호는 10자 이하로 입력해주세요")
    private String bizNo;

    private String clientType; // 거래처유형 (매입/매출)

    @Size(max = 20, message = "전화번호는 20자 이하로 입력해주세요")
    private String tel;

    private String zipCode;

    @Size(max = 200, message = "주소는 200자 이하로 입력해주세요")
    private String address;

    @Size(max = 100, message = "상세주소는 100자 이하로 입력해주세요")
    private String addressDetail;

    private Double lat;
    private Double lng;
    private String useYn = "Y";  // 기본값 Y

    // DTO -> Entity 변환
    public Client toEntity() {
        Client client = new Client();
        client.setClientCode(this.clientCode);
        client.setClientName(this.clientName);
        client.setBizNo(this.bizNo);
        client.setClientType(this.clientType);
        client.setTel(this.tel);
        client.setZipCode(this.zipCode);
        client.setAddress(this.address);
        client.setAddressDetail(this.addressDetail);
        client.setLat(this.lat);
        client.setLng(this.lng);
        client.setUseYn(this.useYn);
        return client;
    }
}
