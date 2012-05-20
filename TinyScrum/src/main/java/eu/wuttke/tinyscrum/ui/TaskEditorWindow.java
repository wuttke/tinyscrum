package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;

public class TaskEditorWindow 
extends Window {
		
	public TaskEditorWindow(TinyScrumApplication application, Task task, ObjectSavedListener listener) {
		setModal(true);
        setWidth("80%");
        setHeight("80%");
        
        if (task.getName() == null || task.getName().length() == 0)
        	setCaption("New Task");
        else
        	setCaption("Task: " + task.getName());
        getContent().setSizeFull();
        center();
		
        TaskEditorView editor = new TaskEditorView(application, task, listener);
        editor.initForm();
		addComponent(editor);
	}
	
	private static final long serialVersionUID = -5750180111347530457L;

}
