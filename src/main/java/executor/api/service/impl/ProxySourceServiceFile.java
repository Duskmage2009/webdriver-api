package executor.api.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import executor.api.model.ProxyConfigHolderDTO;
import executor.api.service.ProxySourceService;
import executor.api.service.ProxyValidationService;
import executor.api.service.QueueHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ProxySourceServiceFile implements ProxySourceService {

    private final String proxySourceFile;
    private final ProxyValidationService proxyValidationService;
    private final QueueHandler<ProxyConfigHolderDTO> proxyQueueHandler;
    private final ObjectMapper objectMapper;

    public ProxySourceServiceFile(
            @Value("${service.proxy.source-file}") String proxySourceFile,
            ProxyValidationService proxyValidationService,
            QueueHandler<ProxyConfigHolderDTO> proxyQueueHandler,
            ObjectMapper objectMapper) {
        this.proxySourceFile = proxySourceFile;
        this.proxyValidationService = proxyValidationService;
        this.proxyQueueHandler = proxyQueueHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public void loadProxies() {
        proxyListFromFile().stream()
                .filter(proxyValidationService::validateProxy)
                .forEach(proxyQueueHandler::add);
    }

    private List<ProxyConfigHolderDTO> proxyListFromFile() {
        try {
            return objectMapper.readValue(Files.readString(Path.of(proxySourceFile)), new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error reading proxy source file", e);
        }
    }

}