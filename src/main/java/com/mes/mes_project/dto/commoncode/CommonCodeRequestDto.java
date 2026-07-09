package com.mes.mes_project.dto.commoncode;

import com.mes.mes_project.entity.CommonCode;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonCodeRequestDto {

    @Size(max = 20, message = "코드는 20자 이하로 입력해주세요")
    private String code;

    @Size(max = 50, message = "코드명은 50자 이하로 입력해주세요")
    private String codeName;

    @Size(max = 200, message = "설명은 200자 이하로 입력해주세요")
    private String description;

    private int sortOrder;

    private String useYn = "Y";

    public CommonCode toEntity() {
        CommonCode commonCode = new CommonCode();
        commonCode.setCode(this.code);
        commonCode.setCodeName(this.codeName);
        commonCode.setDescription(this.description);
        commonCode.setSortOrder(this.sortOrder);
        commonCode.setUseYn(this.useYn);
        return commonCode;
    }
}
