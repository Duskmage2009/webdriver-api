package executor.api.model.mapper;

import executor.api.model.ProxyConfigHolderDTO;
import executor.api.model.ProxyResponseDTO;
import executor.api.model.builder.ProxyConfigHolderDTOBuilder;
import executor.api.model.builder.ProxyConfigHolderDTOBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyResponseToProxyConfigHolderDTOMapperTest {

    private ProxyResponseToProxyConfigHolderDTOMapper mapper;
    private ProxyConfigHolderDTOBuilder builder;

    @BeforeEach
    public void setup() {
        mapper = new ProxyResponseToProxyConfigHolderDTOMapper();
        builder = new ProxyConfigHolderDTOBuilderImpl();
    }

    @Test
    public void shouldReturnInstanceOfProxyConfigHolderDTO() {
        ProxyResponseDTO proxyResponseDTO = new ProxyResponseDTO();
        ProxyConfigHolderDTO result = mapper.map(proxyResponseDTO);
        assertInstanceOf(ProxyConfigHolderDTO.class, result);
    }

    @Test
    public void shouldReturnNotNull() {
        ProxyResponseDTO proxyResponseDTO = new ProxyResponseDTO();
        ProxyConfigHolderDTO result = mapper.map(proxyResponseDTO);
        assertNotNull(result);
    }

    @Test
    public void shouldMapObjectsCorrectly() {
        ProxyResponseDTO proxyResponseDTO = new ProxyResponseDTO("host", 80, "user", "pass");
        ProxyConfigHolderDTO expected = builder
                .withNetworkConfig(proxyResponseDTO.getHostname(), proxyResponseDTO.getPort())
                .withCredentials(proxyResponseDTO.getUsername(), proxyResponseDTO.getPassword())
                .build();

        ProxyConfigHolderDTO actual = mapper.map(proxyResponseDTO);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldMapCorrectlyWithNullCredentials() {
        ProxyResponseDTO proxyResponse = new ProxyResponseDTO("host", 80, null, null);
        ProxyConfigHolderDTO expected = builder
                .withNetworkConfig(proxyResponse.getHostname(), proxyResponse.getPort())
                .build();

        ProxyConfigHolderDTO actual = mapper.map(proxyResponse);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowNullPointerWithNullParameter() {
        ProxyResponseDTO nullableProxy = null;
        assertThrows(NullPointerException.class, () -> mapper.map(nullableProxy));
    }

}