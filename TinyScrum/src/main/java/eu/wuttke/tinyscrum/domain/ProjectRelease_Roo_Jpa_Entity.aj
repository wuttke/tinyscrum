// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum.domain;

import eu.wuttke.tinyscrum.domain.ProjectRelease;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect ProjectRelease_Roo_Jpa_Entity {
    
    declare @type: ProjectRelease: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ProjectRelease.id;
    
    @Version
    @Column(name = "version")
    private Integer ProjectRelease.version;
    
    public Long ProjectRelease.getId() {
        return this.id;
    }
    
    public void ProjectRelease.setId(Long id) {
        this.id = id;
    }
    
    public Integer ProjectRelease.getVersion() {
        return this.version;
    }
    
    public void ProjectRelease.setVersion(Integer version) {
        this.version = version;
    }
    
}
