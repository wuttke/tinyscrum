package eu.wuttke.tinyscrum.domain;

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
public class Customer {

	/**
	 * Name
	 */
	private String name;
	
}
