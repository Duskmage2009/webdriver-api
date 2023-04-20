package executor.api.service;

import executor.api.model.ScenarioDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ScenarioSourceQueueHandlerImpl implements ScenarioSourceQueueHandler{

    private Queue<ScenarioDTO> queue;

    public ScenarioSourceQueueHandlerImpl() {
        queue = new LinkedBlockingQueue<>();
    }

    @Override
    public synchronized ScenarioDTO poll() {
        return queue.poll();
    }

    @Override
    public void offer(ScenarioDTO scenarioDTO) {
        queue.offer(scenarioDTO);
    }

    @Override
    public List<ScenarioDTO> pollAll() {

        List<ScenarioDTO> list = new ArrayList<>(queue);
        queue.clear();
        return list;
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
