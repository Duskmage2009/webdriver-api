package executor.api.controller;

import executor.api.model.ProxyConfigHolderDTO;
import executor.api.service.impl.ProxySourceQueueHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/proxies")

public class ProxySourceController {

    private final ProxySourceQueueHandler proxySourceQueueHandler;

    public ProxySourceController(ProxySourceQueueHandler proxySourceQueueHandler) {
        this.proxySourceQueueHandler = proxySourceQueueHandler;
    }

    @PostMapping
    public ResponseEntity<String> addProxy(@Valid @RequestBody ProxyConfigHolderDTO proxyConfigHolderDTO) {
        proxySourceQueueHandler.add(proxyConfigHolderDTO);
        return new ResponseEntity<>("Proxy added successfully",HttpStatus.CREATED);
    }

    @GetMapping
    public ProxyConfigHolderDTO getProxy() {
        Optional<ProxyConfigHolderDTO> optional = proxySourceQueueHandler.poll();
       return optional.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}



