package executor.api.model.builder;

import executor.api.model.ProxyConfigHolderDTO;

public interface ProxyConfigHolderDTOBuilder {

    ProxyConfigHolderDTOBuilder withNetworkConfig(String hostname, Integer port);

    ProxyConfigHolderDTOBuilder withCredentials(String username, String password);

    ProxyConfigHolderDTO build();

}