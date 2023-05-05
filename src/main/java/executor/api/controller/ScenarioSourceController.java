package executor.api.controller;

import executor.api.exeption.ResourceNotFoundException;
import executor.api.model.ScenarioDTO;
import executor.api.service.ScenarioSourceQueueHandlerImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/scenarios")
public class ScenarioSourceController {
   private final ScenarioSourceQueueHandlerImpl scenarioSourceQueueHandler;

    public ScenarioSourceController(ScenarioSourceQueueHandlerImpl scenarioSourceQueueHandler) {
        this.scenarioSourceQueueHandler = scenarioSourceQueueHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String offerScenario(@Valid @RequestBody ScenarioDTO scenarioDTO){
        scenarioSourceQueueHandler.offer(scenarioDTO);
       return "Scenario is successfully created";
    }
    @GetMapping
    public ScenarioDTO getScenario(){
        Optional<ScenarioDTO> optional = Optional.ofNullable(scenarioSourceQueueHandler.poll());
        return optional.orElseThrow(()-> new ResourceNotFoundException("Scenario not found"));
    }
}
