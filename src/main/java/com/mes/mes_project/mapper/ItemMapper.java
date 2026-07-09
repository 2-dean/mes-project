package com.mes.mes_project.mapper;

import com.mes.mes_project.dto.item.ItemResponseDto;
import com.mes.mes_project.dto.item.ItemSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    // 품목 검색
    List<ItemResponseDto> search(ItemSearchDto searchDto);
}
