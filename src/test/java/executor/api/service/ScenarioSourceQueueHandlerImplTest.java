package executor.api.service;

import executor.api.model.ScenarioDTO;
import executor.api.model.StepDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioSourceQueueHandlerImplTest {

    private ScenarioSourceQueueHandler scenarioSourceQueueHandler;
    private final String name = "Name-1";
    private final String site = "Site-1";
    private List<StepDTO> stepDTOList;
    private ScenarioDTO scenarioDTO;
    @BeforeEach
    public void setUp() {
        scenarioSourceQueueHandler = new ScenarioSourceQueueHandlerImpl();
        stepDTOList = Arrays.asList(new StepDTO("Action-1", "Value-1"), new StepDTO("Action-2", "Value-2"));
        scenarioDTO = new ScenarioDTO(name, site, stepDTOList);
    }

    @Test
    public void pollAndOfferTest() {

        scenarioSourceQueueHandler.offer(scenarioDTO);

        assertEquals(scenarioDTO,scenarioSourceQueueHandler.poll());
    }

    @Test
    public void pollTest() {

        assertNull(scenarioSourceQueueHandler.poll());
    }

    @Test
    public void pollAllSizeTest() {

        scenarioSourceQueueHandler.offer(scenarioDTO);
        scenarioDTO.setName(name + "22");
        scenarioSourceQueueHandler.offer(scenarioDTO);

        int expected = 2;
        int result = scenarioSourceQueueHandler.pollAll().size();

        assertEquals(expected,result);
    }

    @Test
    public void pollAllSizeTest2() {

        assertEquals(0,scenarioSourceQueueHandler.pollAll().size());
    }

    @Test
    public void isEmptyTest() {

        assertTrue(scenarioSourceQueueHandler.isEmpty());
    }

    @Test
    public void isNotEmptyTest() {

        scenarioSourceQueueHandler.offer(scenarioDTO);
        assertFalse(scenarioSourceQueueHandler.isEmpty());
    }
}