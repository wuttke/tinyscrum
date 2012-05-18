package eu.wuttke.tinyscrum.domain;

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
public class Project {

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
    
}
