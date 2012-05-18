package eu.wuttke.tinyscrum.domain;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ProjectFeature {

    @NotNull
    @Size(max = 200)
    private String name;

    @NotNull
    @ManyToOne
    private Project project;
    
}
