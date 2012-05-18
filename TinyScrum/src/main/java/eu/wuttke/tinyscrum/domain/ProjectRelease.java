package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ProjectRelease 
implements Serializable {

    @NotNull
    @Size(max = 200)
    private String name;

    @NotNull
    @ManyToOne
    private Project project;

    @Temporal(TemporalType.DATE)
    private Date plannedDate;

    private static final long serialVersionUID = 1L;
    
}
