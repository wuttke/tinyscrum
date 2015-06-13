package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Angebot
 * @author Wuttke
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Quote 
implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Datum der Ausgabe an den Kunden (Angebotsdatum)
	 */
	@Temporal(TemporalType.DATE)
	private Date issueDate;
	
	/**
	 * Datum der Beauftragung (Bestelldatum)
	 */
	@Temporal(TemporalType.DATE)
	private Date orderDate;
	
	/**
	 * Versprochenes Lieferdatum
	 */
	@Temporal(TemporalType.DATE)
	private Date deliveryDate;
	
	/**
	 * Angebotsnummer
	 */
	@Column(length=80)
	private String quoteNumber;

	/**
	 * Auftragsnummer
	 */
	@Column(length=80)
	private String orderNumber;
	
	/**
	 * Externe Bestellnummer
	 */
	@Column(length=80)
	private String externalOrderNumber;
	
	/**
	 * Titel
	 */
	@Column(length=400)
	private String title;
	
	/**
	 * Status
	 */
	@Column(length=80)
	@Enumerated(EnumType.STRING)
	private QuoteStatus status;

	/**
	 * Kommentar
	 * (in Zukunft 1:n?)
	 */
	@Column(length=2000)
	private String comment;
	
	/**
	 * Project
	 */
	@NotNull
	@ManyToOne
	private Project project;
	
	/**
	 * Customer
	 */
	@ManyToOne
	private Customer customer;
	
	public String toString() {
		return getQuoteNumber() + ": " + getTitle();
	}
	
}
