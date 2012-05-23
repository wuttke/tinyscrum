package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Scrum user
 * @author Matthias Wuttke
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ScrumUser 
implements Serializable {

	/**
	 * User name
	 */
    @NotNull
    @Column(unique = true)
    @Size(max = 100)
    private String userName;

    /**
     * Password (plaintext)
     */
    @Size(max = 50)
    private String password;

    /**
     * Full name
     */
    @Size(max = 300)
    private String fullName;

    /**
     * E-mail
     */
    @Size(max = 300)
    private String email;

    /**
     * Active flag
     */
    @NotNull
    private Boolean active;
    
    /**
     * Return full name as String representation.
     */
    @Override
    public String toString() {
    	return getFullName();
    }
    
    /**
     * Compare by (unique) user name.
     */
    @Override
    public boolean equals(Object obj) {
    	if (obj == null || !(obj instanceof ScrumUser) || getUserName() == null)
    		return super.equals(obj);
    	else
    		return getUserName().equals(((ScrumUser)obj).getUserName());
    }
    
    /**
     * Get user name hash.
     */
    @Override
    public int hashCode() {
    	return getUserName().hashCode();
    }
    
	private static final long serialVersionUID = 1L;

}
