package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    
    // NEUE FELDER
    
    /**
     * Latest Due date
     */
    @Temporal(TemporalType.DATE)
    private Date latestDueDate;

    /**
     * Current Due Date
     */
    @Temporal(TemporalType.DATE)
    private Date currentDueDate;
    
    /**
     * Priority
     */
    @Enumerated(EnumType.STRING)
    //Nullable
    @Column(length=40)
    private Priority priority;
    
    /**
     * Customer ("UKLFR")
     */
    @ManyToOne
    private Customer customer;
    
    /**
     * Projekt ("Ambulanz")
     */
    @ManyToOne
    private CustomerProject customerProject;
    
    /**
     * Auftrag ("2015/002")
     */
    @ManyToOne
    private Quote quote;
    
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
		setPriority(Priority.MEDIUM);
	}
	
}
