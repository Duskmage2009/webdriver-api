package executor.api.service.impl;

import executor.api.model.ProxyConfigHolderDTO;
import executor.api.model.ProxyCredentialsDTO;
import executor.api.model.ProxyNetworkConfigDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProxySourceQueueHandlerTest {

    private ProxySourceQueueHandler proxySourceQueueHandler;
    private ProxyCredentialsDTO proxyCredentials;
    private ProxyNetworkConfigDTO proxyNetworkConfig;
    private ProxyConfigHolderDTO proxyConfigHolder;

    @BeforeEach
    public void setUp() {
        proxySourceQueueHandler = new ProxySourceQueueHandler();
        proxyCredentials = new ProxyCredentialsDTO("user", "password");
        proxyNetworkConfig = new ProxyNetworkConfigDTO("hostname", 8080);
        proxyConfigHolder = new ProxyConfigHolderDTO(proxyNetworkConfig, proxyCredentials);
    }

    @Test
    public void testAdd() {
        boolean actual = proxySourceQueueHandler.add(proxyConfigHolder);
        assertTrue(actual);
    }

    @Test
    public void testPoll() {
        proxySourceQueueHandler.add(proxyConfigHolder);
        Optional<ProxyConfigHolderDTO> optional = proxySourceQueueHandler.poll();
        ProxyConfigHolderDTO actual = optional.get();
        assertEquals("user", actual.getProxyCredentials().getUser());
    }

    @Test
    public void testQueueSize() {
        proxySourceQueueHandler.add(proxyConfigHolder);
        assertEquals(1, proxySourceQueueHandler.queueSize());
        proxySourceQueueHandler.add(proxyConfigHolder);
        proxySourceQueueHandler.add(proxyConfigHolder);
        assertEquals(3, proxySourceQueueHandler.queueSize());
    }

}