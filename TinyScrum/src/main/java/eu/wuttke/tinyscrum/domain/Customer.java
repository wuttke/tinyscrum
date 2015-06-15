package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Kunde (z.B. UKLFR)
 * @author Wuttke
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Customer
implements Serializable, Comparable<Customer> {

	private static final long serialVersionUID = 1L;

	/**
	 * Name
	 */
	@Column(length=200)
	private String name;
	
	/**
	 * Project
	 */
	@ManyToOne
	private Project project;
	
	public String toString() {
		return getName();
	}
	
	public Customer() {
	}
	
	public Customer(String name) {
		setName(name);
	}
	
    @Override
    public boolean equals(Object obj) {
    	Long myId = getId();
    	if (obj != null && obj instanceof Customer) {
	    	Long otherId = ((Customer)obj).getId();
	    	if (myId != null && otherId != null)
	    		return myId.longValue() == otherId.longValue();
    	}
		return super.equals(obj);
    }

    @Override
    public int compareTo(Customer o) {
    	if (o == null)
    		return 0;
    	return getName().compareTo(o.getName());
    }

}
