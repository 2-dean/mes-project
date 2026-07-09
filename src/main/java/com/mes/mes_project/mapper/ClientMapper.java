package com.mes.mes_project.mapper;

import com.mes.mes_project.dto.client.ClientResponseDto;
import com.mes.mes_project.dto.client.ClientSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClientMapper {
    // 고객 검색
    List<ClientResponseDto> search(ClientSearchDto searchDto);
}
