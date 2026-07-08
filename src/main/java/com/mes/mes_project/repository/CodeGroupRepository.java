package com.mes.mes_project.repository;

import com.mes.mes_project.entity.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, Long> {

    Optional<CodeGroup> findByGroupCode(String groupCode);

    List<CodeGroup> findAllByOrderByGroupCode();
}
