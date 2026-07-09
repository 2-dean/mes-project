package com.mes.mes_project.dto.codegroup;

import com.mes.mes_project.entity.CodeGroup;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CodeGroupRequestDto {

    @Size(max = 20, message = "그룹코드는 20자 이하로 입력해주세요")
    private String groupCode;

    @Size(max = 50, message = "그룹명은 50자 이하로 입력해주세요")
    private String groupName;

    @Size(max = 200, message = "설명은 200자 이하로 입력해주세요")
    private String description;

    private String useYn = "Y";

    public CodeGroup toEntity() {
        CodeGroup group = new CodeGroup();
        group.setGroupCode(this.groupCode);
        group.setGroupName(this.groupName);
        group.setDescription(this.description);
        group.setUseYn(this.useYn);
        return group;
    }
}
