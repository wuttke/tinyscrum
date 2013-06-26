package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Iteration
 * @author Matthias Wuttke
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Iteration
implements Serializable {

	/**
	 * Name
	 */
    @NotNull
    private String name;

    /**
     * Start date
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * Duration (days)
     */
    @NotNull
    private int durationDays = 14;

    /**
     * Status
     */
    @Column(length=30)
    @Enumerated(EnumType.STRING)
    @NotNull
    private IterationStatus status;
    
    /**
     * Project reference
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
    
    /**
     * Iteration team
     */
    @ManyToOne
    private Team team;

    /**
     * Returns true if the iteration contains the passed date.
     * @param date Date
     * @return true/false
     */
	public boolean containsDate(Date date) {
		if (date.before(getStartDate()))
			return false;
		Date date2 = new Date(getStartDate().getTime() + 86400000L * durationDays);
		return date.before(date2);
	}

	public Iteration() {
	}
	
	public Iteration(String name) {
		setName(name);
	}
	
	/**
	 * Returns the name.
	 */
    public String toString() {
    	return getName();
    }

    @Override
    public boolean equals(Object obj) {
    	Long myId = getId();
    	if (obj != null && obj instanceof Iteration) {
	    	Long otherId = ((Iteration)obj).getId();
	    	if (myId != null && otherId != null)
	    		return myId.longValue() == otherId.longValue();
    	}
		return super.equals(obj);
    }

	private static final long serialVersionUID = 1L;

}
