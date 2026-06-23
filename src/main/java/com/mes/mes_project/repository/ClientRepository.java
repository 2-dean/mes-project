package com.mes.mes_project.repository;

import com.mes.mes_project.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
