// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum;

import eu.wuttke.tinyscrum.FileUpload;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect FileUpload_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager FileUpload.entityManager;
    
    public static final EntityManager FileUpload.entityManager() {
        EntityManager em = new FileUpload().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long FileUpload.countFileUploads() {
        return entityManager().createQuery("SELECT COUNT(o) FROM FileUpload o", Long.class).getSingleResult();
    }
    
    public static List<FileUpload> FileUpload.findAllFileUploads() {
        return entityManager().createQuery("SELECT o FROM FileUpload o", FileUpload.class).getResultList();
    }
    
    public static FileUpload FileUpload.findFileUpload(Long id) {
        if (id == null) return null;
        return entityManager().find(FileUpload.class, id);
    }
    
    public static List<FileUpload> FileUpload.findFileUploadEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM FileUpload o", FileUpload.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void FileUpload.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void FileUpload.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            FileUpload attached = FileUpload.findFileUpload(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void FileUpload.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void FileUpload.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public FileUpload FileUpload.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        FileUpload merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
