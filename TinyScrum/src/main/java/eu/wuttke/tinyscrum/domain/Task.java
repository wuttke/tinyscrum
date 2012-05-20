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
 * Story task
 * @author Matthias Wuttke
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders={"findTasksByDeveloperOrTester"})
public class Task 
implements Serializable {
	
	/**
	 * Task name
	 */
    @NotNull
    @Size(max = 800)
    private String name;

    /**
     * Task developer
     */
    @Size(max = 100)
    private String developer1;

    /**
     * Task developer
     */
    @Size(max = 100)
    private String developer2;

    /**
     * Task tester
     */
    @Size(max=100)
    private String tester;
    
    /**
     * Description (html)
     */
    @Size(max = 2000)
    private String description;

    /**
     * Story reference
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private UserStory story;

    /**
     * Project reference
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Project project;

    /**
     * Task status
     */
    @Column(length=30)
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus status;

    /**
     * Task effort estimate
     */
    private Double estimate;
    
    /**
     * Returns the developer(s) of the task.
     * @return String
     */
    public String getDeveloper() {
    	if (getDeveloper2() != null && getDeveloper2().length() > 0)
    		return getDeveloper1() + " | " + getDeveloper2();
    	else
    		return getDeveloper1();
    }
    
    /**
     * Sets the main developer of the task.
     * @param developer developer
     */
    public void setDeveloper(String developer) {
    	setDeveloper1(developer);
    	setDeveloper2(null);
    }
    
	private static final long serialVersionUID = 1L;

}
