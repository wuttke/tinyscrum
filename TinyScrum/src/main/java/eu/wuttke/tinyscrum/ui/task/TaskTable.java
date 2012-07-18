package eu.wuttke.tinyscrum.ui.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.TaskStatus;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.TaskManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
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
		setVisibleColumns(new String[]{ "id", "name", "developer", "tester", "estimate", "status" });
		setColumnExpandRatio("id", 1);
		setColumnExpandRatio("name", 4);
		setColumnExpandRatio("developer", 2);
		setColumnExpandRatio("tester", 2);
		setColumnExpandRatio("estimate", 1);
		setColumnExpandRatio("status", 2);
		setColumnAlignment("id", ALIGN_RIGHT);
		setColumnAlignment("estimate", ALIGN_RIGHT);
		setFooterVisible(true);

		addListener((ItemClickListener)this);
	}
	
	public void loadTasks() {
		List<Task> tasks = taskManager.loadTasksForStory(story);
		taskContainer.removeAllItems();
		taskContainer.addAll(tasks);
		recalculateFooter(tasks);
	}
	
	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property) {
		if (colId.equals("estimate"))
			return super.formatPropertyValue(rowId, colId, property) + " " + application.getCurrentProject().getTaskEstimateUnit();
		else
			return super.formatPropertyValue(rowId, colId, property);
	}
	
	private void recalculateFooter(List<Task> tasks) {
		int open = 0, test = 0, close = 0;
		double estimate = 0;
		for (Task task : tasks) {
			if (task.getStatus() == TaskStatus.TASK_OPEN)
				open++;
			else if (task.getStatus() == TaskStatus.TASK_TEST)
				test++;
			else if (task.getStatus() == TaskStatus.TASK_DONE)
				close++;
			
			if (task.getEstimate() != null)
				estimate += task.getEstimate();
		}
		
		setColumnFooter("status", open + "/" + test + "/" + close);
		setColumnFooter("estimate", Double.toString(estimate) + " " + application.getCurrentProject().getTaskEstimateUnit());
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void itemClick(ItemClickEvent event) {
		if (event.isDoubleClick()) {
			//event.getPropertyId()
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
