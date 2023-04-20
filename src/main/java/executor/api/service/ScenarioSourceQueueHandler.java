package executor.api.service;

import executor.api.model.ScenarioDTO;

import java.util.List;

public interface ScenarioSourceQueueHandler {

    ScenarioDTO poll();

    void offer(ScenarioDTO scenarioDTO);

    List<ScenarioDTO> pollAll();

    boolean isEmpty();
}
