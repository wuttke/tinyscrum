// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum.ui.userstory;

import eu.wuttke.tinyscrum.logic.UserStoryManager;
import eu.wuttke.tinyscrum.ui.userstory.UserStoryActions;

privileged aspect UserStoryActions_Roo_JavaBean {
    
    public UserStoryManager UserStoryActions.getUserStoryManager() {
        return this.userStoryManager;
    }
    
    public void UserStoryActions.setUserStoryManager(UserStoryManager userStoryManager) {
        this.userStoryManager = userStoryManager;
    }
    
}