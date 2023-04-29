package executor.api.service.impl;

import executor.api.model.ProxyConfigHolderDTO;
import executor.api.model.ProxyResponseDTO;
import executor.api.model.mapper.Mapper;
import executor.api.service.ProxySourceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProxySourceServiceHttp implements ProxySourceService {

    private final String proxySourceUrl;
    private final RestTemplateBuilder restTemplateBuilder;
    private final Mapper<ProxyResponseDTO, ProxyConfigHolderDTO> responseToProxyConfigMapper;
    private final ProxySourceQueueHandler proxySourceQueueHandler;
    private final ParameterizedTypeReference<List<ProxyResponseDTO>> typeReference;

    public ProxySourceServiceHttp(@Value("${service.proxy.source-url}") String proxySourceUrl,
                                  RestTemplateBuilder restTemplateBuilder,
                                  Mapper<ProxyResponseDTO, ProxyConfigHolderDTO> responseToProxyConfigMapper,
                                  ProxySourceQueueHandler proxySourceQueueHandler) {
        this.proxySourceUrl = proxySourceUrl;
        this.proxySourceQueueHandler = proxySourceQueueHandler;
        this.restTemplateBuilder = restTemplateBuilder;
        this.responseToProxyConfigMapper = responseToProxyConfigMapper;
        this.typeReference = new ParameterizedTypeReference<>() {};
    }

    @Override
    @Scheduled(fixedRateString = "${service.proxy.schedule.url}")
    public void loadProxies() {
        proxyListFromUrl().stream()
                .map(responseToProxyConfigMapper::map)
                .forEach(proxySourceQueueHandler::add);
    }

    private List<ProxyResponseDTO> proxyListFromUrl() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.exchange(proxySourceUrl, HttpMethod.GET, null, typeReference).getBody();
    }

}