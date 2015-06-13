package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class CustomerProject 
implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Project Name
	 */
	@Column(length=200)
	private String name;
	
	/**
	 * Customer
	 */
	@ManyToOne
	@NotNull
	private Customer customer;
	
	/**
	 * Project
	 */
	private Project project;
	
}
