// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum.domain;

import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.TaskStatus;
import eu.wuttke.tinyscrum.domain.UserStory;

privileged aspect Task_Roo_JavaBean {
    
    public String Task.getName() {
        return this.name;
    }
    
    public void Task.setName(String name) {
        this.name = name;
    }
    
    public String Task.getDeveloper1() {
        return this.developer1;
    }
    
    public void Task.setDeveloper1(String developer1) {
        this.developer1 = developer1;
    }
    
    public String Task.getDeveloper2() {
        return this.developer2;
    }
    
    public void Task.setDeveloper2(String developer2) {
        this.developer2 = developer2;
    }
    
    public String Task.getTester() {
        return this.tester;
    }
    
    public void Task.setTester(String tester) {
        this.tester = tester;
    }
    
    public String Task.getDescription() {
        return this.description;
    }
    
    public void Task.setDescription(String description) {
        this.description = description;
    }
    
    public UserStory Task.getStory() {
        return this.story;
    }
    
    public void Task.setStory(UserStory story) {
        this.story = story;
    }
    
    public Project Task.getProject() {
        return this.project;
    }
    
    public void Task.setProject(Project project) {
        this.project = project;
    }
    
    public TaskStatus Task.getStatus() {
        return this.status;
    }
    
    public void Task.setStatus(TaskStatus status) {
        this.status = status;
    }
    
    public Double Task.getEstimate() {
        return this.estimate;
    }
    
    public void Task.setEstimate(Double estimate) {
        this.estimate = estimate;
    }
    
}
