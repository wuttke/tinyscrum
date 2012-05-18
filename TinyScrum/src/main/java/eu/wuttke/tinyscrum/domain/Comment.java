package eu.wuttke.tinyscrum.domain;

import java.util.Date;

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
public class Comment {

    @NotNull
    @Size(max = 4000)
    private String comment;

    @NotNull
    @Size(max = 100)
    private String userName;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date createDateTime;
    
}
