package executor.api.model;

import java.util.Objects;

public class StepDTO {

	private String action;
	private String value;

	public StepDTO() {}

	public StepDTO(String action, String value) {
		this.action = action;
		this.value = value;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StepDTO stepDTO = (StepDTO) o;
		return Objects.equals(action, stepDTO.action) && Objects.equals(value, stepDTO.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(action, value);
	}
}