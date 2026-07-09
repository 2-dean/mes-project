package com.mes.mes_project.controller;

import com.mes.mes_project.dto.client.ClientRequestDto;
import com.mes.mes_project.dto.client.ClientResponseDto;
import com.mes.mes_project.dto.client.ClientSearchDto;
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

    // 조회 (검색조건: 거래처명, 사용여부)
    @GetMapping
    public List<ClientResponseDto> search(
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) String useYn
    ) {
        ClientSearchDto searchDto = new ClientSearchDto();
        searchDto.setClientName(clientName);
        searchDto.setUseYn(useYn);
        return clientService.search(searchDto);
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
