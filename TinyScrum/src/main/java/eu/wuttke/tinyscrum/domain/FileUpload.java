package eu.wuttke.tinyscrum.domain;

import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findFileUploadsByFileNameEquals" })
public class FileUpload {

    @Size(max = 500)
    private String fileName;
}
