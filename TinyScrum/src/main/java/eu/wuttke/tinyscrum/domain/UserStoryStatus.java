package eu.wuttke.tinyscrum.domain;

/**
 * Story status
 * @author Matthias Wuttke
 */
public enum UserStoryStatus {

	/**
	 * New story
	 */
    STORY_OPEN, 
    
    /**
     * Story to be tested
     */
    STORY_TEST, 
    
    /**
     * Finished story
     */
    STORY_DONE;
 
    public String toString() {
    	switch (this) {
    	case STORY_DONE: return "Done";
    	case STORY_OPEN: return "Open";
    	case STORY_TEST: return "Test";
    	default: return "Unknown";
    	}
    }
    
}
