package eu.wuttke.tinyscrum.domain;

/**
 * Task status
 * @author Matthias Wuttke
 */
public enum TaskStatus {

	TASK_OPEN,
	TASK_TEST,
	TASK_DONE;
	
    public String toString() {
    	switch (this) {
    	case TASK_DONE: return "Done";
    	case TASK_OPEN: return "Open";
    	case TASK_TEST: return "Test";
    	default: return "Unknown";
    	}
    }
	
}
