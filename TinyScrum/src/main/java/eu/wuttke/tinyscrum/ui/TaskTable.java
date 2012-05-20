package eu.wuttke.tinyscrum.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.TaskManager;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;

@Configurable(autowire=Autowire.BY_NAME)
public class TaskTable 
extends Table 
implements ItemClickListener {

	private UserStory story;
	private BeanItemContainer<Task> taskContainer;
	private TinyScrumApplication application;
	
	public TaskTable(TinyScrumApplication application, UserStory story) {
		this.application = application;
		this.story = story;

		taskContainer = new BeanItemContainer<Task>(Task.class);
		
		setSizeFull();
		setSelectable(true);
		setContainerDataSource(taskContainer);
		setVisibleColumns(new String[]{ "name", "developer", "tester", "estimate", "status" });
		
		addListener((ItemClickListener)this);
	}
	
	public void loadTasks() {
		List<Task> tasks = taskManager.loadTasksForStory(story);
		taskContainer.removeAllItems();
		taskContainer.addAll(tasks);
	}
	
	private static final long serialVersionUID = 1L;

	@Override
	public void itemClick(ItemClickEvent event) {
		if (event.isDoubleClick()) {
			Task task = (Task)event.getItemId();
			if (task != null) {
				TaskEditorWindow w = new TaskEditorWindow(application, task, new ObjectSavedListener() {
					public void objectSaved(Object object) {
						loadTasks();
					}
				});
				application.getMainWindow().addWindow(w);
			}
		}
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	private TaskManager taskManager;

}
