package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Project release
 * @author Matthias Wuttke
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ProjectRelease 
implements Serializable {

	/**
	 * Release name (version)
	 */
    @NotNull
    @Size(max = 200)
    private String name;

    /**
     * Description (html)
     */
    @Size(max = 2000)
    private String description;

    /**
     * Project reference
     */
    @NotNull
    @ManyToOne
    private Project project;

    /**
     * Planned release date
     */
    @Temporal(TemporalType.DATE)
    private Date plannedDate;

    public ProjectRelease() {
	}
    
    public ProjectRelease(String name) {
    	setName(name);
	}
    
    public String toString() {
    	return getName();
    }
    
    @Override
    public boolean equals(Object obj) {
    	Long myId = getId();
    	if (obj != null && obj instanceof ProjectRelease) {
	    	Long otherId = ((ProjectRelease)obj).getId();
	    	if (myId != null && otherId != null)
	    		return myId.longValue() == otherId.longValue();
    	}
		return super.equals(obj);
    }

    private static final long serialVersionUID = 1L;
    
}
