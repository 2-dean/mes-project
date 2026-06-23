package com.mes.mes_project.contorller;

import com.mes.mes_project.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

}
