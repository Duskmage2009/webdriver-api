package executor.api.service.impl;

import executor.api.model.ProxyConfigHolderDTO;
import executor.api.service.QueueHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ProxySourceQueueHandler implements QueueHandler<ProxyConfigHolderDTO> {

    private final BlockingQueue<ProxyConfigHolderDTO> proxyQueue;

    public ProxySourceQueueHandler() {
        this.proxyQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public boolean add(ProxyConfigHolderDTO proxyConfigHolder) {
        return proxyQueue.add(proxyConfigHolder);
    }

    @Override
    public Optional<ProxyConfigHolderDTO> poll() {
        return Optional.ofNullable(proxyQueue.poll());
    }

    @Override
    public int queueSize() {
        return proxyQueue.size();
    }
}