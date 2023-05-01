package executor.api.service;

import java.io.IOException;
import java.net.*;
import java.util.Base64;

import executor.api.model.ProxyConfigHolderDTO;
import org.springframework.stereotype.Service;

@Service
public class ProxyValidationService {

    private static final String DEFAULT_URL = "http://info.cern.ch/";

    public boolean validateProxy(ProxyConfigHolderDTO proxyConfig) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyConfig.getProxyNetworkConfig().getHostname(), proxyConfig.getProxyNetworkConfig().getPort()));
        HttpURLConnection connection = null;
        try {
            URL url = new URL(DEFAULT_URL);
            connection = (HttpURLConnection) url.openConnection(proxy);
            if (proxyConfig.getProxyCredentials() != null) {
                String user = proxyConfig.getProxyCredentials().getUser();
                String password = proxyConfig.getProxyCredentials().getPassword();
                String encoded = Base64.getEncoder().encodeToString((user + ":" + password).getBytes());
                connection.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
            }
            connection.connect();
            int responseCode = connection.getResponseCode();
            return (responseCode >= 200 && responseCode < 300);
        } catch (IOException e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
