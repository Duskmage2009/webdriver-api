package executor.api.service;

import com.solacesystems.jcsmp.JCSMPException;

public interface ProxySourceServiceUrl {
    void sendMessage(String message) throws JCSMPException;
}