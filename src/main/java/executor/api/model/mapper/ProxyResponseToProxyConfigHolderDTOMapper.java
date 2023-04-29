package executor.api.model.mapper;

import executor.api.model.ProxyConfigHolderDTO;
import executor.api.model.ProxyResponseDTO;
import executor.api.model.builder.ProxyConfigHolderDTOBuilderImpl;
import org.springframework.stereotype.Component;

@Component("responseToProxyConfigMapper")
public class ProxyResponseToProxyConfigHolderDTOMapper implements Mapper<ProxyResponseDTO, ProxyConfigHolderDTO> {

    @Override
    public ProxyConfigHolderDTO map(ProxyResponseDTO from) {
        return new ProxyConfigHolderDTOBuilderImpl()
                .withNetworkConfig(from.getHostname(), from.getPort())
                .withCredentials(from.getUsername(), from.getPassword())
                .build();
    }

}
