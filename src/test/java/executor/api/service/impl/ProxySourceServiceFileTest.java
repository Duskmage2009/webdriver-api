package executor.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import executor.api.captor.ResultCaptor;
import executor.api.model.ProxyConfigHolderDTO;
import executor.api.model.builder.ProxyConfigHolderDTOBuilderImpl;
import executor.api.service.ProxyValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProxySourceServiceFileTest {

    @Mock
    private ProxyValidationService proxyValidationService;
    @Mock
    private ProxySourceQueueHandler proxySourceQueueHandler;
    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();
    private ProxySourceServiceFile proxySourceServiceFile;

    @BeforeEach
    public void setup() {
        String proxySourceFile = "src/test/resources/proxy/proxySourceFile.json";
        proxySourceServiceFile =
                new ProxySourceServiceFile(proxySourceFile, proxyValidationService, proxySourceQueueHandler, objectMapper);
    }

    @Test
    public void shouldAddProxyTenTimesToQueueHandler() throws JsonProcessingException {
        ProxyConfigHolderDTO proxy = new ProxyConfigHolderDTO();
        int proxyAmount = 10;
        List<ProxyConfigHolderDTO> proxyList = IntStream.range(0, proxyAmount).mapToObj(num -> proxy).toList();

        doReturn(proxyList)
                .when(objectMapper).readValue(anyString(), ArgumentMatchers.<TypeReference<List<ProxyConfigHolderDTO>>>any());
        when(proxyValidationService.validateProxy(any())).thenReturn(true);
        proxySourceServiceFile.loadProxies();

        verify(proxySourceQueueHandler, times(proxyAmount)).add(proxy);
    }

    @Test
    public void shouldReadFileWithFourProxiesAndAddToQueueHandler() {
        when(proxyValidationService.validateProxy(any())).thenReturn(true);
        proxySourceServiceFile.loadProxies();

        verify(proxySourceQueueHandler, times(4)).add(any(ProxyConfigHolderDTO.class));
    }

    @Test
    public void shouldReadAndSkipFourInvalidProxies() {
        when(proxyValidationService.validateProxy(any())).thenReturn(false);
        proxySourceServiceFile.loadProxies();

        verify(proxyValidationService, times(4)).validateProxy(any());
        verifyNoInteractions(proxySourceQueueHandler);
    }

    @Test
    public void shouldAddOnlyValidProxies() {
        when(proxyValidationService.validateProxy(any()))
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(true)
                .thenReturn(true);

        proxySourceServiceFile.loadProxies();

        verify(proxyValidationService, times(4)).validateProxy(any());
        verify(proxySourceQueueHandler, times(2)).add(any());
    }

    @Test
    public void shouldReadExpectedProxyDTOsFromFile() throws JsonProcessingException {
        ProxyConfigHolderDTO expectedProxyDTO = new ProxyConfigHolderDTOBuilderImpl()
                .withNetworkConfig("localhost", 80)
                .withCredentials("user", "password")
                .build();
        ResultCaptor<List<ProxyConfigHolderDTO>> listCaptor = new ResultCaptor<>();

        doAnswer(listCaptor)
                .when(objectMapper).readValue(anyString(), ArgumentMatchers.<TypeReference<List<ProxyConfigHolderDTO>>>any());
        proxySourceServiceFile.loadProxies();

        listCaptor.getResult().forEach(actualProxyDTO -> Assertions.assertEquals(expectedProxyDTO, actualProxyDTO));
    }


    @Test
    public void shouldThrowRuntimeExceptionWhenFileNotExists() {
        String fileNotExists = "notExists";
        proxySourceServiceFile =
                new ProxySourceServiceFile(fileNotExists, proxyValidationService, proxySourceQueueHandler, objectMapper);

        Assertions.assertThrows(RuntimeException.class, () -> proxySourceServiceFile.loadProxies());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenFileIsEmpty() {
        String emptyProxySource = "src/test/resources/proxy/emptyProxy.json";
        proxySourceServiceFile =
                new ProxySourceServiceFile(emptyProxySource, proxyValidationService, proxySourceQueueHandler, objectMapper);

        Assertions.assertThrows(RuntimeException.class, () -> proxySourceServiceFile.loadProxies());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenJsonFileIsBroken() {
        String brokenProxySource = "src/test/resources/proxy/brokenProxy.json";
        proxySourceServiceFile =
                new ProxySourceServiceFile(brokenProxySource, proxyValidationService, proxySourceQueueHandler, objectMapper);

        Assertions.assertThrows(RuntimeException.class, () -> proxySourceServiceFile.loadProxies());
    }
}