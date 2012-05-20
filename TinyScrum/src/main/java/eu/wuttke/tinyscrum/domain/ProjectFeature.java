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
    
    public String toString() {
    	return getName();
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (getId() != null && ((ProjectFeature)obj).getId() != null)
    		return ((ProjectFeature)obj).getId().intValue() == getId().intValue();
		else
			return super.equals(obj);
    }

	private static final long serialVersionUID = 1L;

}
