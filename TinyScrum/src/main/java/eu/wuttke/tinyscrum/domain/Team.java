package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Team
implements Serializable {

    @NotNull
    @Size(max = 300)
    private String name;

    @Size(max = 2000)
    private String description;

	private static final long serialVersionUID = 1L;

}
