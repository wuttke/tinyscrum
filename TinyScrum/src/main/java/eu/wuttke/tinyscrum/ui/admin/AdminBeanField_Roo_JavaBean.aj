// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum.ui.admin;

import eu.wuttke.tinyscrum.ui.admin.AdminBeanField;
import javax.persistence.EntityManager;

privileged aspect AdminBeanField_Roo_JavaBean {
    
    public String AdminBeanField.getPropertyId() {
        return this.propertyId;
    }
    
    public void AdminBeanField.setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
    
    public String AdminBeanField.getCaption() {
        return this.caption;
    }
    
    public void AdminBeanField.setCaption(String caption) {
        this.caption = caption;
    }
    
    public AdminBeanFieldType AdminBeanField.getFieldType() {
        return this.fieldType;
    }
    
    public void AdminBeanField.setFieldType(AdminBeanFieldType fieldType) {
        this.fieldType = fieldType;
    }
    
    public String AdminBeanField.getWidth() {
        return this.width;
    }
    
    public void AdminBeanField.setWidth(String width) {
        this.width = width;
    }
    
    public Boolean AdminBeanField.getRequired() {
        return this.required;
    }
    
    public void AdminBeanField.setRequired(Boolean required) {
        this.required = required;
    }
    
    public EntityManager AdminBeanField.getEntityManager() {
        return this.entityManager;
    }
    
    public void AdminBeanField.setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
}
