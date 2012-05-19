package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * User comments for tasks and stories.
 * @author Matthias Wuttke
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Comment
implements Serializable {

	/**
	 * The comment (plain text)
	 */
	@NotNull
    @Size(max = 4000)
    private String comment;

	/**
	 * User name who created the comment
	 */
    @NotNull
    @Size(max = 100)
    private String userName;
    
    /**
     * Commented item (task, story)
     */
    @Enumerated(EnumType.STRING)
    @Column(length=30)
    @NotNull
    private CommentType commentType;

    /**
     * ID of commented item (task, story)
     */
    private Long parentId;

    /**
     * Comment creation date time.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date createDateTime;

	private static final long serialVersionUID = 1L;

}
