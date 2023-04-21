package executor.api.model;

import java.util.Objects;

public class ProxyConfigHolderDTO {
    private ProxyNetworkConfigDTO proxyNetworkConfig;
    private ProxyCredentialsDTO proxyCredentialsDTO;

    public ProxyConfigHolderDTO() {}

    public ProxyConfigHolderDTO(ProxyNetworkConfigDTO proxyNetworkConfig, ProxyCredentialsDTO proxyCredentialsDTO) {
        this.proxyNetworkConfig = proxyNetworkConfig;
        this.proxyCredentialsDTO = proxyCredentialsDTO;
    }

    public ProxyNetworkConfigDTO getProxyNetworkConfig() {
        return proxyNetworkConfig;
    }

    public void setProxyNetworkConfig(ProxyNetworkConfigDTO proxyNetworkConfig) {
        this.proxyNetworkConfig = proxyNetworkConfig;
    }

    public ProxyCredentialsDTO getProxyCredentials() {
        return proxyCredentialsDTO;
    }

    public void setProxyCredentials(ProxyCredentialsDTO proxyCredentialsDTO) {
        this.proxyCredentialsDTO = proxyCredentialsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProxyConfigHolderDTO proxyConfigHolder = (ProxyConfigHolderDTO) o;
        return Objects.equals(proxyNetworkConfig, proxyConfigHolder.proxyNetworkConfig) && Objects.equals(proxyCredentialsDTO, proxyConfigHolder.proxyCredentialsDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proxyNetworkConfig, proxyCredentialsDTO);
    }
}
