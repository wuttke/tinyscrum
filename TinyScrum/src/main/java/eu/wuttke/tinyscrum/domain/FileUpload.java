package eu.wuttke.tinyscrum.domain;

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

@RooJavaBean
@RooToString
@RooJpaActiveRecord()
public class FileUpload {

	/**
	 * File name
	 */
    @Size(max = 500)
    private String fileName;
    
    /**
     * Mimetype
     */
    @Size(max = 200)
    private String mimeType;
    
    /**
     * Blob
     */
    @Column(length=1000000)
    private byte[] binaryData;
    
    /**
     * Parent type
     */
    @Enumerated(EnumType.STRING)
    @Column(length=30)
    private CommentType commentType;
    
    /**
     * Parent ID
     */
    @NotNull
    private long parentId;
    
    /**
     * File size (bytes)
     */
    @NotNull
    private long fileSize;
    
    /**
     * Upload Date Time
     */
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date createDateTime;
    
	/**
	 * User who did the upload
	 */
    @NotNull
    @Size(max = 100)
    private String userName;
    
}
