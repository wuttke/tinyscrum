// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum.domain;

import eu.wuttke.tinyscrum.domain.Project;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Project_Roo_Jpa_Entity {
    
    declare @type: Project: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Project.id;
    
    @Version
    @Column(name = "version")
    private Integer Project.version;
    
    public Long Project.getId() {
        return this.id;
    }
    
    public void Project.setId(Long id) {
        this.id = id;
    }
    
    public Integer Project.getVersion() {
        return this.version;
    }
    
    public void Project.setVersion(Integer version) {
        this.version = version;
    }
    
}
