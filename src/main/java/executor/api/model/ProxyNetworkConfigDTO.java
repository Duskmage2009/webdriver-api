package executor.api.model;

import java.util.Objects;

public class ProxyNetworkConfigDTO {
    private String hostname;
    private Integer port;

    public ProxyNetworkConfigDTO() {
    }

    public ProxyNetworkConfigDTO(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProxyNetworkConfigDTO that = (ProxyNetworkConfigDTO) o;
        return hostname.equals(that.hostname) && port.equals(that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname, port);
    }
}
