package executor.api.controller;

import executor.api.exeption.ResourceNotFoundException;
import executor.api.model.ProxyConfigHolderDTO;
import executor.api.service.impl.ProxySourceQueueHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/proxies")

public class ProxySourceController {

    private final ProxySourceQueueHandler proxySourceQueueHandler;

    public ProxySourceController(ProxySourceQueueHandler proxySourceQueueHandler) {
        this.proxySourceQueueHandler = proxySourceQueueHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addProxy(@Valid @RequestBody ProxyConfigHolderDTO proxyConfigHolderDTO) {
        proxySourceQueueHandler.add(proxyConfigHolderDTO);
        return "Proxy added successfully";
    }

    @GetMapping
    public ProxyConfigHolderDTO getProxy() {
        Optional<ProxyConfigHolderDTO> optional = proxySourceQueueHandler.poll();
        return optional.orElseThrow(() -> new ResourceNotFoundException("Proxy not found"));
    }

}



