package com.mes.mes_project.mapper;

import com.mes.mes_project.dto.workorder.WorkOrderListDto;
import com.mes.mes_project.dto.workorder.WorkOrderSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorkOrderMapper {
    // @Mapper
    //→ MyBatis Mapper라고 Spring한테 알려주는 것
    //→ WorkOrderMapper.xml이랑 자동으로 연결됨

    // 작업지시 검색
    List<WorkOrderListDto> search(WorkOrderSearchDto searchDto);

}
