// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum.logic;

import eu.wuttke.tinyscrum.logic.MailManager;
import eu.wuttke.tinyscrum.logic.TaskManager;

privileged aspect TaskManager_Roo_JavaBean {
    
    public MailManager TaskManager.getMailManager() {
        return this.mailManager;
    }
    
    public void TaskManager.setMailManager(MailManager mailManager) {
        this.mailManager = mailManager;
    }
    
}
