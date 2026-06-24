package com.mes.mes_project.service;


import com.mes.mes_project.entity.Client;

import com.mes.mes_project.repository.ClientRepository;
import com.mes.mes_project.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    // 전체조회
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    // 단건조회
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("고객 없음"));
    }

    //등록
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    // 수정
    @Transactional
    public Client update(Long id, Client updateClient) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("고객 없음"));

        client.update(
                updateClient.getClientName(),
                updateClient.getBizNo(),
                updateClient.getClientType(),
                updateClient.getTel(),
                updateClient.getZipCode(),
                updateClient.getAddress(),
                updateClient.getAddressDetail(),
                updateClient.getLat(),
                updateClient.getLng(),
                updateClient.getUseYn()
        );

        //저장
        return client;
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("고객 없음"));

        client.delete();

        //clientRepository.save(client);
    }
}
