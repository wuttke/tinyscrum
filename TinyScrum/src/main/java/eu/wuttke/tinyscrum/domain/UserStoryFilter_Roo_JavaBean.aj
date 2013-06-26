// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum.domain;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.ProjectFeature;
import eu.wuttke.tinyscrum.domain.ProjectRelease;
import eu.wuttke.tinyscrum.domain.UserStoryFilter;
import eu.wuttke.tinyscrum.domain.UserStoryStatus;

privileged aspect UserStoryFilter_Roo_JavaBean {
    
    public Project UserStoryFilter.getProject() {
        return this.project;
    }
    
    public void UserStoryFilter.setProject(Project project) {
        this.project = project;
    }
    
    public String UserStoryFilter.getTitleContains() {
        return this.titleContains;
    }
    
    public void UserStoryFilter.setTitleContains(String titleContains) {
        this.titleContains = titleContains;
    }
    
    public UserStoryStatus UserStoryFilter.getStatusEquals() {
        return this.statusEquals;
    }
    
    public void UserStoryFilter.setStatusEquals(UserStoryStatus statusEquals) {
        this.statusEquals = statusEquals;
    }
    
    public boolean UserStoryFilter.isFilterOwner() {
        return this.filterOwner;
    }
    
    public void UserStoryFilter.setFilterOwner(boolean filterOwner) {
        this.filterOwner = filterOwner;
    }
    
    public String UserStoryFilter.getOwnerEquals() {
        return this.ownerEquals;
    }
    
    public void UserStoryFilter.setOwnerEquals(String ownerEquals) {
        this.ownerEquals = ownerEquals;
    }
    
    public boolean UserStoryFilter.isFilterIteration() {
        return this.filterIteration;
    }
    
    public void UserStoryFilter.setFilterIteration(boolean filterIteration) {
        this.filterIteration = filterIteration;
    }
    
    public Iteration UserStoryFilter.getIterationEquals() {
        return this.iterationEquals;
    }
    
    public void UserStoryFilter.setIterationEquals(Iteration iterationEquals) {
        this.iterationEquals = iterationEquals;
    }
    
    public boolean UserStoryFilter.isFilterFeature() {
        return this.filterFeature;
    }
    
    public void UserStoryFilter.setFilterFeature(boolean filterFeature) {
        this.filterFeature = filterFeature;
    }
    
    public ProjectFeature UserStoryFilter.getFeatureEquals() {
        return this.featureEquals;
    }
    
    public void UserStoryFilter.setFeatureEquals(ProjectFeature featureEquals) {
        this.featureEquals = featureEquals;
    }
    
    public boolean UserStoryFilter.isFilterRelease() {
        return this.filterRelease;
    }
    
    public void UserStoryFilter.setFilterRelease(boolean filterRelease) {
        this.filterRelease = filterRelease;
    }
    
    public ProjectRelease UserStoryFilter.getReleaseEquals() {
        return this.releaseEquals;
    }
    
    public void UserStoryFilter.setReleaseEquals(ProjectRelease releaseEquals) {
        this.releaseEquals = releaseEquals;
    }
    
}