package com.salvesjr.library.client;

import com.salvesjr.library.model.dto.ClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("cliente")
public interface FornecedorClient {

    @GetMapping("/clientes/{email}")
    ClienteDto getClienteByEmail(@PathVariable String email);
}
