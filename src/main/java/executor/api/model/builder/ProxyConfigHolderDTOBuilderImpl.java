package executor.api.model.builder;

import executor.api.model.ProxyConfigHolderDTO;
import executor.api.model.ProxyCredentialsDTO;
import executor.api.model.ProxyNetworkConfigDTO;

public class ProxyConfigHolderDTOBuilderImpl implements ProxyConfigHolderDTOBuilder {

    private String hostname;
    private Integer port;
    private String username;
    private String password;

    @Override
    public ProxyConfigHolderDTOBuilderImpl withNetworkConfig(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
        return this;
    }

    @Override
    public ProxyConfigHolderDTOBuilderImpl withCredentials(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    @Override
    public ProxyConfigHolderDTO build() {
        return new ProxyConfigHolderDTO(
                new ProxyNetworkConfigDTO(hostname, port),
                new ProxyCredentialsDTO(username, password)
        );
    }

}