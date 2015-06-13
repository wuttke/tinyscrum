package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;

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
	private String name;
	
	/**
	 * Project
	 */
	private Project project;
	
}
