package executor.api.controller;

import executor.api.model.ScenarioDTO;
import executor.api.service.ScenarioSourceQueueHandlerImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/scenarios")
public class ScenarioSourceController {
   private final ScenarioSourceQueueHandlerImpl scenarioSourceQueueHandler;

    public ScenarioSourceController(ScenarioSourceQueueHandlerImpl scenarioSourceQueueHandler) {
        this.scenarioSourceQueueHandler = scenarioSourceQueueHandler;
    }

    @PostMapping
    public ResponseEntity<String> offerScenario(@Valid @RequestBody ScenarioDTO scenarioDTO){
        scenarioSourceQueueHandler.offer(scenarioDTO);
       return new ResponseEntity<String>("Scenario is successfully created", HttpStatus.CREATED);
    }
    @GetMapping
    public ScenarioDTO getScenario(){
        Optional<ScenarioDTO> optional = Optional.ofNullable(scenarioSourceQueueHandler.poll());
        return optional.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
