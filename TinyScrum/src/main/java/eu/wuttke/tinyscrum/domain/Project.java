package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Project
 * @author Matthias Wuttke
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Project
implements Serializable {

	// should be renamed to "Client" = Mandant
	
	private static final long serialVersionUID = 1L;

	/**
	 * Name
	 */
    @NotNull
    @Size(max = 200)
    private String name;

    /**
     * Description
     */
    @Size(max = 2000)
    private String description;
    
    /**
     * Unit for story effort estimations
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(length=30)
    private TimeUnit storyEstimateUnit = TimeUnit.POINTS;

    /**
     * Unit for task effort estimations
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(length=30)
    private TimeUnit taskEstimateUnit = TimeUnit.HOURS;
    
    /**
     * Calculate story estimate based on task estimates
     */
    private boolean calculateStoryEstimates;
    
    public String toString() {
    	return getName();
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj != null && obj instanceof Project && getId() != null)
    		return getId().equals(((Project)obj).getId());
    	else
    		return super.equals(obj);
    }
    
}
