package com.mes.mes_project.contorller;

import com.mes.mes_project.entity.Client;
import com.mes.mes_project.service.ClientService;
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
    public Client save(@RequestBody Client client) {
        return clientService.save(client);
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
                          @RequestBody Client client) {
        return clientService.update(id, client);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}
