package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * User Story
 * @author Matthias Wuttke
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord()
public class UserStory
implements Serializable {

	/**
	 * Title
	 */
    @NotNull
    @Size(max = 400)
    private String title;

    /**
     * Description
     */
    @Size(max = 20000)
    private String description;

    /**
     * Owner; NULL = unowned
     */
    @Size(max = 100)
    private String owner;

    /**
     * Status
     */
    @Column(length=30)
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStoryStatus status;

    /**
     * Iteration; NULL = backlog
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Iteration iteration;
    
    /**
     * Project
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    
    /**
     * Sequence number (global for all user stories)
     */
    @NotNull
    private int sequenceNumber;
    
    /**
     * Feature
     */
    @ManyToOne
    private ProjectFeature projectFeature;
    
    /**
     * Release
     */
    @ManyToOne
    private ProjectRelease projectRelease;
   
    /**
     * Story effort estimate (story points)
     */
    private double estimate;
    
    /**
     * Actual story effort
     */
    private Double actualEffort;
    
    public String getIterationName() {
    	return iteration != null ? iteration.getName() : "Backlog";
    }
    
	private static final long serialVersionUID = 1L;

	public UserStory() {
		setId(0L);
		setTitle("");
		setDescription("");
		setStatus(UserStoryStatus.STORY_OPEN);
		setSequenceNumber(0);
	}
	
}
