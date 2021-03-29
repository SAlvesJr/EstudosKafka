package com.salvesjr.library.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.salvesjr.library.client.FornecedorClient;
import com.salvesjr.library.model.dto.ClienteDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClienteService {

    @Autowired
    private FornecedorClient fornecedorClient;

    @HystrixCommand(fallbackMethod = "realizaCompraFallback")
    public ClienteDto buscaClienteByEmail(String email) {

        log.error("Busca o cliente pelo email {}", email);
        return fornecedorClient.getClienteByEmail(email);
    }

    public ClienteDto metodoFallback(String email) {
        log.error("Callback... da função realizaCompraFallback");
        return new ClienteDto(null, "", email);
    }
}
