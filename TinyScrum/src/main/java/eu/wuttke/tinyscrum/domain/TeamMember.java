package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class TeamMember 
implements Serializable {

    @NotNull
    @ManyToOne
    private Team team;

    @NotNull
    @ManyToOne
    private ScrumUser scrumUser;
    
	private static final long serialVersionUID = 1L;

}
