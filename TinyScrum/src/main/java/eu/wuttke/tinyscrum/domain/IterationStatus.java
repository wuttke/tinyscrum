package eu.wuttke.tinyscrum.domain;

/**
 * Iteration status
 * @author Matthias Wuttke
 */
public enum IterationStatus {

	ITERATION_NEW,
	ITERATION_STARTED,
	ITERATION_DONE;
	
	public String toString() {
		if (this == ITERATION_NEW)
			return "New";
		else if (this == ITERATION_STARTED)
			return "In Progress";
		else if (this == ITERATION_DONE)
			return "Completed";
		else
			return "Unknown";
	}
	
}
