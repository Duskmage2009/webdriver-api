package executor.api.service.impl;

import executor.api.model.ProxyConfigHolderDTO;
import executor.api.model.ProxyResponseDTO;
import executor.api.model.builder.ProxyConfigHolderDTOBuilderImpl;
import executor.api.model.mapper.Mapper;
import executor.api.service.QueueHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProxySourceServiceHttpTest {

    private final ProxyResponseDTO proxyResponse = new ProxyResponseDTO("host", 80, "user", "password");
    private final String host = "localhost";
    private final Integer queueLimit = 40;
    @Mock
    private RestTemplateBuilder restTemplateBuilder;
    @Mock
    private Mapper<ProxyResponseDTO, ProxyConfigHolderDTO> proxyConfigHolderDTOMapper;
    @Mock
    private QueueHandler<ProxyConfigHolderDTO> proxyQueueHandler;
    @Mock
    private ParameterizedTypeReference<List<ProxyResponseDTO>> parameterizedTypeReference;
    @Mock
    private RestTemplate restTemplate;
    private ProxySourceServiceHttp proxySourceServiceHttp;

    @BeforeEach
    public void setup() {
        proxySourceServiceHttp = new ProxySourceServiceHttp(
                host,
                queueLimit,
                restTemplateBuilder,
                proxyConfigHolderDTOMapper,
                proxyQueueHandler,
                parameterizedTypeReference
        );
    }

    @Test
    public void shouldExecuteRequestWithExpectedParameters() {
        whenRestTemplateCallsExchange().thenReturn(ResponseEntity.ok(Collections.emptyList()));
        proxySourceServiceHttp.loadProxies();

        verify(restTemplate).exchange(host, HttpMethod.GET, null, parameterizedTypeReference);
    }

    @Test
    public void shouldAddProxiesToQueueHandlerThreeTimes() {
        int quantity = 3;

        whenRestTemplateCallsExchange().thenReturn(ResponseEntity.ok(listWithProxyQuantity(quantity)));
        proxySourceServiceHttp.loadProxies();

        verify(proxyQueueHandler, times(quantity)).add(any());
    }

    @Test
    public void shouldMapAndAddCorrectProxiesTenTimes() {
        int quantity = 10;
        ProxyConfigHolderDTO proxyConfigHolder =
                new ProxyConfigHolderDTOBuilderImpl().withCredentials("host", "port").build();

        whenRestTemplateCallsExchange().thenReturn(ResponseEntity.ok(listWithProxyQuantity(quantity)));
        when(proxyConfigHolderDTOMapper.map(proxyResponse)).thenReturn(proxyConfigHolder);
        proxySourceServiceHttp.loadProxies();

        verify(proxyConfigHolderDTOMapper, times(quantity)).map(proxyResponse);
        verify(proxyQueueHandler, times(quantity)).add(proxyConfigHolder);
    }

    @Test
    public void shouldCheckQueueSizeAndNotInteractIfLimitReached() {
        when(proxyQueueHandler.queueSize()).thenReturn(queueLimit);

        proxySourceServiceHttp.loadProxies();

        verify(proxyQueueHandler).queueSize();
        verifyNoMoreInteractions(proxyQueueHandler, proxyConfigHolderDTOMapper, restTemplateBuilder, restTemplate);
    }

    @Test
    public void shouldThrowRestClientException() {
        whenRestTemplateCallsExchange().thenThrow(RestClientException.class);

        Assertions.assertThrows(RestClientException.class, () -> proxySourceServiceHttp.loadProxies());
    }

    private List<ProxyResponseDTO> listWithProxyQuantity(int proxyQuantity) {
        return IntStream.range(0, proxyQuantity).mapToObj(it -> proxyResponse).toList();
    }

    private OngoingStubbing<ResponseEntity<List<ProxyResponseDTO>>> whenRestTemplateCallsExchange() {
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        return when(restTemplate.exchange(host, HttpMethod.GET, null, parameterizedTypeReference));
    }

}