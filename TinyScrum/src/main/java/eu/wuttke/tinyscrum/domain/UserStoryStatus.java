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
    
}
