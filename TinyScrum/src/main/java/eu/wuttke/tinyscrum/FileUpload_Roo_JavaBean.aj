// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package eu.wuttke.tinyscrum;

import eu.wuttke.tinyscrum.FileUpload;

privileged aspect FileUpload_Roo_JavaBean {
    
    public String FileUpload.getFileName() {
        return this.fileName;
    }
    
    public void FileUpload.setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}
