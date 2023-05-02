package executor.api.service;

import executor.api.model.ProxyConfigHolderDTO;

public interface ProxyValidationService {

    boolean validateProxy(ProxyConfigHolderDTO proxyConfigHolderDTO);

}
