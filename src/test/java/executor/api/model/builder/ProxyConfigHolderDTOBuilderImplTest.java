package executor.api.model.builder;

import executor.api.model.ProxyConfigHolderDTO;
import executor.api.model.ProxyCredentialsDTO;
import executor.api.model.ProxyNetworkConfigDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProxyConfigHolderDTOBuilderImplTest {

    private final String hostname = "hostname";
    private final Integer port = 8080;
    private final String username = "username";
    private final String password = "password";

    private ProxyConfigHolderDTOBuilder proxyConfigHolderDTOBuilder;

    @BeforeEach
    public void setUp() {
        proxyConfigHolderDTOBuilder = new ProxyConfigHolderDTOBuilderImpl();
    }

    @Test
    public void testWithNetworkConfig() {

        assertEquals(proxyConfigHolderDTOBuilder, proxyConfigHolderDTOBuilder.withNetworkConfig(hostname, port));
    }

    @Test
    public void testWithCredentials() {

        assertEquals(proxyConfigHolderDTOBuilder, proxyConfigHolderDTOBuilder.withCredentials(username, password));
    }

    @Test
    public void testBuild() {

        ProxyConfigHolderDTO actualBuildResult = proxyConfigHolderDTOBuilder.build();

        ProxyCredentialsDTO proxyCredentials = actualBuildResult.getProxyCredentials();
        assertNull(proxyCredentials.getUser());
        assertNull(proxyCredentials.getPassword());

        ProxyNetworkConfigDTO proxyNetworkConfig = actualBuildResult.getProxyNetworkConfig();
        assertNull(proxyNetworkConfig.getPort());
        assertNull(proxyNetworkConfig.getHostname());
    }

    @Test
    public void testBuild2() {

        ProxyConfigHolderDTO proxyConfigHolderDTO = proxyConfigHolderDTOBuilder
                .withCredentials(username,password)
                .withNetworkConfig(hostname,port)
                .build();

        ProxyNetworkConfigDTO proxyNetworkConfig = proxyConfigHolderDTO.getProxyNetworkConfig();
        assertEquals(port,proxyNetworkConfig.getPort());
        assertEquals(hostname,proxyNetworkConfig.getHostname());

        ProxyCredentialsDTO proxyCredentials = proxyConfigHolderDTO.getProxyCredentials();
        assertEquals(username,proxyCredentials.getUser());
        assertEquals(password,proxyCredentials.getPassword());
    }
}

