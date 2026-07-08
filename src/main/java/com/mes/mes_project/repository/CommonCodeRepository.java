package com.mes.mes_project.repository;

import com.mes.mes_project.entity.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {

    List<CommonCode> findByGroup_GroupCodeAndUseYnOrderBySortOrder(String groupCode, String useYn);

    List<CommonCode> findByGroup_IdOrderBySortOrder(Long groupId);

    List<CommonCode> findByGroup_Id(Long groupId);
}
