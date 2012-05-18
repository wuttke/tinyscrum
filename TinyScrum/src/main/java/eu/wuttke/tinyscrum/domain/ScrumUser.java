package eu.wuttke.tinyscrum.domain;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ScrumUser {

    @NotNull
    @Column(unique = true)
    @Size(max = 100)
    private String userName;

    @Size(max = 50)
    private String password;

    @Size(max = 300)
    private String fullName;

    @Size(max = 300)
    private String email;

    @NotNull
    private Boolean active;
    
}
