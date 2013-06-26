package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Project feature
 * @author Matthias Wuttke
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ProjectFeature 
implements Serializable {

	/**
	 * Feature name
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

    public ProjectFeature() {
    }
    
    public ProjectFeature(String name) {
    	setName(name);
    }
    
    public String toString() {
    	return getName();
    }
    
    @Override
    public boolean equals(Object obj) {
    	Long myId = getId();
    	if (obj != null && obj instanceof ProjectFeature) {
	    	Long otherId = ((ProjectFeature)obj).getId();
	    	if (myId != null && otherId != null)
	    		return myId.longValue() == otherId.longValue();
    	}
		return super.equals(obj);
    }

	private static final long serialVersionUID = 1L;

}
