package executor.api.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ProxyResponseDTOTest {

    private final String hostname = "Hostname-1";
    private final int port = 8080;
    private final String username = "Username-1";
    private final String password = "Password-1";


    @Test
    public void testConstructor() {

        ProxyResponseDTO proxyResponseDTO = new ProxyResponseDTO(hostname,port,username,password);

        assertNotNull(proxyResponseDTO.getHostname());
        assertNotEquals(0,proxyResponseDTO.getPort());
        assertNotNull(proxyResponseDTO.getUsername());
        assertNotNull(proxyResponseDTO.getPassword());
    }

    @Test
    public void testConstructor2() {

        ProxyResponseDTO proxyResponseDTO = new ProxyResponseDTO(hostname,port,username,password);

        assertEquals(hostname,proxyResponseDTO.getHostname());
        assertEquals(port,proxyResponseDTO.getPort());
        assertEquals(username,proxyResponseDTO.getUsername());
        assertEquals(password,proxyResponseDTO.getPassword());
    }

    @Test
    public void testSettersAndGetters() {

        ProxyResponseDTO proxyResponseDTO = new ProxyResponseDTO();

        proxyResponseDTO.setHostname(hostname);
        proxyResponseDTO.setPort(port);
        proxyResponseDTO.setUsername(username);
        proxyResponseDTO.setPassword(password);

        assertEquals(hostname,proxyResponseDTO.getHostname());
        assertEquals(password,proxyResponseDTO.getPassword());
        assertEquals(port,proxyResponseDTO.getPort());
        assertEquals(username,proxyResponseDTO.getUsername());
    }

    @Test
    public void testNullValues() {

        ProxyResponseDTO proxyResponseDTO = new ProxyResponseDTO();

        proxyResponseDTO.setPassword(null);
        proxyResponseDTO.setUsername(null);
        proxyResponseDTO.setHostname(null);

        assertNull(proxyResponseDTO.getUsername());
        assertNull(proxyResponseDTO.getPassword());
        assertNull(proxyResponseDTO.getHostname());
    }

    @Test
    public void testNotEqualsObjects() {

        ProxyResponseDTO proxyResponseDTO = new ProxyResponseDTO(hostname,port,username,password);
        ProxyResponseDTO proxyResponseDTO2 = new ProxyResponseDTO(hostname+"h",port,username,password);

        assertFalse(proxyResponseDTO.equals(proxyResponseDTO2));
    }

    @Test
    public void testHashCode() {

        ProxyResponseDTO proxyResponseDTO = new ProxyResponseDTO(hostname,port,username,password);
        ProxyResponseDTO proxyResponseDTO2 = new ProxyResponseDTO(hostname,port,username,password);

        assertEquals(proxyResponseDTO.hashCode(), proxyResponseDTO2.hashCode());
    }

    @Test
    public void testEquals() {

        ProxyResponseDTO proxyResponseDTO = new ProxyResponseDTO(hostname,port,username,password);
        ProxyResponseDTO proxyResponseDTO2 = new ProxyResponseDTO(hostname,port,username,password);

        assertTrue(proxyResponseDTO.equals(proxyResponseDTO2));
    }

    @Test
    public void testToString() {

        ProxyResponseDTO proxyResponseDTO = new ProxyResponseDTO(hostname,port,username,password);

        String expected = "ProxyResponseDTO{" +
                "hostname='" + hostname + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';

        assertEquals(expected,proxyResponseDTO.toString());
    }
}