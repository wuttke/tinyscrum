package eu.wuttke.tinyscrum.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Team {

    @NotNull
    @Size(max = 300)
    private String name;

    @Size(max = 2000)
    private String description;

}
