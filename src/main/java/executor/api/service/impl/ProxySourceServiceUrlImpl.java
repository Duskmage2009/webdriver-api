package executor.api.service.impl;

import com.solacesystems.jcsmp.*;
import executor.api.service.ProxySourceServiceUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProxySourceServiceUrlImpl implements ProxySourceServiceUrl {
    @Value("${solace.host}")
    private String solaceHost;
    @Value("${solace.vpn}")
    private String solaceVpn;

    @Value("${solace.username}")
    private String solaceUsername;

    @Value("${solace.password}")
    private String solacePassword;

    private final JCSMPSession session;

    public ProxySourceServiceUrlImpl() throws JCSMPException {
        JCSMPProperties properties = new JCSMPProperties();
        properties.setProperty(JCSMPProperties.HOST, solaceHost);
        properties.setProperty(JCSMPProperties.VPN_NAME, solaceVpn);
        properties.setProperty(JCSMPProperties.USERNAME, solaceUsername);
        properties.setProperty(JCSMPProperties.PASSWORD, solacePassword);

        session = JCSMPFactory.onlyInstance().createSession(properties);
        session.connect();
    }

    @Override
    public void sendMessage(String message) throws JCSMPException {
        TextMessage msg = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
        msg.setText(message);
        XMLMessageProducer producer = session.getMessageProducer(new JCSMPStreamingPublishEventHandler() {
            @Override
            public void handleError(String messageID, JCSMPException cause, long timestamp) {
                throw new RuntimeException("Error publishing message: " + cause.getMessage(), cause);
            }

            @Override
            public void responseReceived(String messageID) {
                throw new RuntimeException("Message published successfully. Message ID: " + messageID);
            }
        });
        producer.send(msg, JCSMPFactory.onlyInstance().createTopic("queue/sampleQueue"));
    }
}