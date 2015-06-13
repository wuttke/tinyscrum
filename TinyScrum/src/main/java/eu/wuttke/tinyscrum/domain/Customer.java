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
implements Serializable {

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
	
}
