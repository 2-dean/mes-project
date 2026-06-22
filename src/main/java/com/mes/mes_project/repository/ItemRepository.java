package com.mes.mes_project.repository;

import com.mes.mes_project.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
                                                    // Entity, PK 타입
}
