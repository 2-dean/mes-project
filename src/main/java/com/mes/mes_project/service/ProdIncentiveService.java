package com.mes.mes_project.service;

import com.mes.mes_project.entity.ProdIncentive;
import com.mes.mes_project.repository.ProdIncentiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdIncentiveService {
    private final ProdIncentiveRepository prodIncentiveRepository;

    public List<ProdIncentive> findAll() {
        return prodIncentiveRepository.findAll();
    }

    public List<ProdIncentive> findByWorkOrderId(Long workOrderId) {
        return prodIncentiveRepository.findByWorkOrderId(workOrderId);
    }
}
