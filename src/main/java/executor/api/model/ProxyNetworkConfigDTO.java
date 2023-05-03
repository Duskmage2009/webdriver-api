package executor.api.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class ProxyNetworkConfigDTO {
    @NotBlank(message = "hostname mustn't be empty field")
    private String hostname;
    @NotNull(message = "port mustn't be empty field")
    @Min(value = 0, message ="port must be more than 0")
    @Max(value = 65535,message = "port must be less than 65535")
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
