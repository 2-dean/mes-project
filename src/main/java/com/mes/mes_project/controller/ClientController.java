package com.mes.mes_project.controller;

import com.mes.mes_project.dto.ClientRequestDto;
import com.mes.mes_project.entity.Client;
import com.mes.mes_project.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    // 등록
    @PostMapping
    public Client save(@Valid @RequestBody ClientRequestDto request) {
        return clientService.save(request.toEntity());
    }

    // 전체조회
    @GetMapping
    public List<Client> findAll() {
        return clientService.findAll();
    }

    // 단건조회
    @GetMapping("/{id}")
    public Client findById(@PathVariable Long id){
        return clientService.findById(id);
    }

    // 수정
    @PutMapping("/{id}")
    public Client update (@PathVariable Long id,
                          @Valid @RequestBody ClientRequestDto request) {
        return clientService.update(id, request.toEntity());
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}
