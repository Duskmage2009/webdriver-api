package executor.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import executor.api.captor.ResultCaptor;
import executor.api.model.ProxyConfigHolderDTO;
import executor.api.model.builder.ProxyConfigHolderDTOBuilder;
import executor.api.model.builder.ProxyConfigHolderDTOBuilderImpl;
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
public class ProxySourceServiceFileImplTest {

    @Mock
    private ProxySourceQueueHandler proxySourceQueueHandler;
    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();
    private ProxySourceServiceFileImpl proxySourceServiceFile;

    @BeforeEach
    public void setup() {
        String proxySourceFile = "src/test/resources/proxy/proxySourceFile.json";
        proxySourceServiceFile = new ProxySourceServiceFileImpl(proxySourceFile, proxySourceQueueHandler, objectMapper);
    }

    @Test
    public void shouldAddProxyTenTimesToQueueHandler() throws JsonProcessingException {
        ProxyConfigHolderDTO proxy = new ProxyConfigHolderDTO();
        int proxyAmount = 10;
        List<ProxyConfigHolderDTO> proxyList = IntStream.range(0, proxyAmount).mapToObj(num -> proxy).toList();

        doReturn(proxyList)
                .when(objectMapper).readValue(anyString(), ArgumentMatchers.<TypeReference<List<ProxyConfigHolderDTO>>>any());
        proxySourceServiceFile.preloadProxies();

        verify(proxySourceQueueHandler, times(proxyAmount)).add(proxy);
    }

    @Test
    public void shouldReadFileWithFourProxiesAndAddToQueueHandler() {
        proxySourceServiceFile.preloadProxies();

        verify(proxySourceQueueHandler, times(4)).add(any(ProxyConfigHolderDTO.class));
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
        proxySourceServiceFile.preloadProxies();

        listCaptor.getResult().forEach(actualProxyDTO -> Assertions.assertEquals(expectedProxyDTO, actualProxyDTO));
    }


    @Test
    public void shouldThrowRuntimeExceptionWhenFileNotExists() {
        String fileNotExists = "notExists";
        proxySourceServiceFile =
                new ProxySourceServiceFileImpl(fileNotExists, proxySourceQueueHandler, objectMapper);

        Assertions.assertThrows(RuntimeException.class, () -> proxySourceServiceFile.preloadProxies());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenFileIsEmpty() {
        String emptyProxySource = "src/test/resources/proxy/emptyProxy.json";
        proxySourceServiceFile =
                new ProxySourceServiceFileImpl(emptyProxySource, proxySourceQueueHandler, objectMapper);

        Assertions.assertThrows(RuntimeException.class, () -> proxySourceServiceFile.preloadProxies());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenJsonFileIsBroken() {
        String brokenProxySource = "src/test/resources/proxy/brokenProxy.json";
        proxySourceServiceFile =
                new ProxySourceServiceFileImpl(brokenProxySource, proxySourceQueueHandler, objectMapper);

        Assertions.assertThrows(RuntimeException.class, () -> proxySourceServiceFile.preloadProxies());
    }
}