package com.mes.mes_project.repository;

import com.mes.mes_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);

    List<User> findByUseYnOrderByUsername(String useYn);
}

