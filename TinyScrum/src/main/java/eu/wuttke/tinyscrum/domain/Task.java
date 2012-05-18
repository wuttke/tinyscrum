package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Task 
implements Serializable {
	
    @NotNull
    @Size(max = 800)
    private String name;

    @Size(max = 100)
    private String owner;

    @Size(max = 2000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private UserStory story;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Project project;

    @Column(length=30)
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus status;

    private Double estimate;
    
    @NotNull
    private int sequenceNumber;
    
	private static final long serialVersionUID = 1L;

}
