// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum.domain;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.ProjectFeature;
import eu.wuttke.tinyscrum.domain.ProjectRelease;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.domain.UserStoryStatus;

privileged aspect UserStory_Roo_JavaBean {
    
    public String UserStory.getTitle() {
        return this.title;
    }
    
    public void UserStory.setTitle(String title) {
        this.title = title;
    }
    
    public String UserStory.getDescription() {
        return this.description;
    }
    
    public void UserStory.setDescription(String description) {
        this.description = description;
    }
    
    public String UserStory.getOwner() {
        return this.owner;
    }
    
    public void UserStory.setOwner(String owner) {
        this.owner = owner;
    }
    
    public UserStoryStatus UserStory.getStatus() {
        return this.status;
    }
    
    public void UserStory.setStatus(UserStoryStatus status) {
        this.status = status;
    }
    
    public Iteration UserStory.getIteration() {
        return this.iteration;
    }
    
    public void UserStory.setIteration(Iteration iteration) {
        this.iteration = iteration;
    }
    
    public Project UserStory.getProject() {
        return this.project;
    }
    
    public void UserStory.setProject(Project project) {
        this.project = project;
    }
    
    public int UserStory.getSequenceNumber() {
        return this.sequenceNumber;
    }
    
    public void UserStory.setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
    public ProjectFeature UserStory.getProjectFeature() {
        return this.projectFeature;
    }
    
    public void UserStory.setProjectFeature(ProjectFeature projectFeature) {
        this.projectFeature = projectFeature;
    }
    
    public ProjectRelease UserStory.getProjectRelease() {
        return this.projectRelease;
    }
    
    public void UserStory.setProjectRelease(ProjectRelease projectRelease) {
        this.projectRelease = projectRelease;
    }
    
    public double UserStory.getEstimate() {
        return this.estimate;
    }
    
    public void UserStory.setEstimate(double estimate) {
        this.estimate = estimate;
    }
    
}
