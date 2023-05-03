package executor.api.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

public class ScenarioDTO {
    private String name;
    @NotBlank(message = "site is required ,this mustn't be empty field")
    private String site;
    @Valid
    @Size(min = 1 ,message = "need put at least one step")
    private List<StepDTO> steps;

    public ScenarioDTO() {
    }

    public ScenarioDTO(String name, String site, List<StepDTO> steps) {
        this.name = name;
        this.site = site;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<StepDTO> getSteps() {
        return steps;
    }

    public void setSteps(List<StepDTO> steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScenarioDTO that = (ScenarioDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(site, that.site) && Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, site, steps);
    }
}
