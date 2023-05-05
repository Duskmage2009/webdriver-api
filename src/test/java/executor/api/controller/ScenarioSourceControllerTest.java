package executor.api.controller;

import executor.api.exeption.ResourceNotFoundException;
import executor.api.model.ScenarioDTO;
import executor.api.service.ScenarioSourceQueueHandlerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ScenarioSourceControllerTest {

    private ScenarioSourceController scenarioSourceController;

    @Mock
    private ScenarioSourceQueueHandlerImpl scenarioSourceQueueHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scenarioSourceController = new ScenarioSourceController(scenarioSourceQueueHandler);
    }

    @Test
    void testOfferScenario() {
        ScenarioDTO scenarioDTO = new ScenarioDTO();
        scenarioSourceQueueHandler.offer(scenarioDTO);
        String result = scenarioSourceController.offerScenario(scenarioDTO);

        assertEquals("Scenario is successfully created", result);
    }

    @Test
    void testGetScenario() {
        ScenarioDTO scenarioDTO = new ScenarioDTO();
        when(scenarioSourceQueueHandler.poll()).thenReturn(scenarioDTO);

        ScenarioDTO result = scenarioSourceController.getScenario();

        verify(scenarioSourceQueueHandler, times(1)).poll();
        assertEquals(scenarioDTO, result);
    }

    @Test
    void testGetScenarioNotFound() {
        when(scenarioSourceQueueHandler.poll()).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            scenarioSourceController.getScenario();
        });

        verify(scenarioSourceQueueHandler, times(1)).poll();
    }
}