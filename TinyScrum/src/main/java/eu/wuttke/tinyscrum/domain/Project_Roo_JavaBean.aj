// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum.domain;

import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.TimeUnit;

privileged aspect Project_Roo_JavaBean {
    
    public String Project.getName() {
        return this.name;
    }
    
    public void Project.setName(String name) {
        this.name = name;
    }
    
    public String Project.getDescription() {
        return this.description;
    }
    
    public void Project.setDescription(String description) {
        this.description = description;
    }
    
    public TimeUnit Project.getStoryEstimateUnit() {
        return this.storyEstimateUnit;
    }
    
    public void Project.setStoryEstimateUnit(TimeUnit storyEstimateUnit) {
        this.storyEstimateUnit = storyEstimateUnit;
    }
    
    public TimeUnit Project.getTaskEstimateUnit() {
        return this.taskEstimateUnit;
    }
    
    public void Project.setTaskEstimateUnit(TimeUnit taskEstimateUnit) {
        this.taskEstimateUnit = taskEstimateUnit;
    }
    
}
