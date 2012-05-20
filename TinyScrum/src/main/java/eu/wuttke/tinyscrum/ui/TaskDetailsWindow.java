package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.Task;

public class TaskDetailsWindow
extends Window {
	
	public TaskDetailsWindow(TinyScrumApplication application, Task task) {
		setModal(true);
        setWidth("80%");
        setHeight("80%");
        
        setCaption("Task: " + task.getName());
        getContent().setSizeFull();
        center();
		
        TaskDetailsView view = new TaskDetailsView(application, task);
		addComponent(view);
		view.refreshContent();
	}
	
	private static final long serialVersionUID = -5750180111347530457L;

}
