package eu.wuttke.tinyscrum.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

import eu.wuttke.tinyscrum.domain.TaskAndStory;
import eu.wuttke.tinyscrum.domain.TaskStatus;
import eu.wuttke.tinyscrum.logic.TaskManager;

@Configurable(autowire=Autowire.BY_NAME)
public class DashboardTaskStoryTable 
extends Table 
implements ItemClickListener {

	private TinyScrumApplication application;
	
	public DashboardTaskStoryTable(TinyScrumApplication application) {
		this.application = application;
		setSizeFull();
		setSelectable(true);
		setFooterVisible(true);
	}
	
	public void loadDashboardTasks(String user) {
		List<TaskAndStory> tasks = taskManager.loadUserTasks(user, application.getCurrentProject());
		setContainerDataSource(new BeanItemContainer<TaskAndStory>(TaskAndStory.class, tasks));
		setVisibleColumns(new String[]{"taskName", "storyTitle", "iterationName", "taskDeveloper", "taskTester", "taskEstimate", "taskStatus"});
		setColumnHeaders(new String[]{"Task", "Story", "Itereation Name", "Developer", "Tester", "Estimate", "Status"});
		addListener((ItemClickListener)this);
		recalculateFooter(tasks);
	}
	
	@Override
	protected String formatPropertyValue(Object rowId, Object colId,
			Property property) {
		if (colId.equals("taskEstimate"))
			return super.formatPropertyValue(rowId, colId, property) + " " + application.getCurrentProject().getTaskEstimateUnit();
		else
			return super.formatPropertyValue(rowId, colId, property);
	}
	
	private void recalculateFooter(List<TaskAndStory> tasks) {
		int open = 0, test = 0, close = 0;
		double estimate = 0;
		for (TaskAndStory task : tasks) {
			if (task.getTaskStatus() == TaskStatus.TASK_OPEN)
				open++;
			else if (task.getTaskStatus() == TaskStatus.TASK_TEST)
				test++;
			else if (task.getTaskStatus() == TaskStatus.TASK_DONE)
				close++;
			
			if (task.getTask().getEstimate() != null)
				estimate += task.getTask().getEstimate();
		}
		
		setColumnFooter("status", open + "/" + test + "/" + close);
		setColumnFooter("taskEstimate", Double.toString(estimate) + " " + application.getCurrentProject().getTaskEstimateUnit());
	}

	@Override
	public void itemClick(ItemClickEvent event) {
		if (event.isDoubleClick() && event.getItemId() != null) {
			String propertyId = (String)event.getPropertyId();
			if (propertyId != null && propertyId.equals("storyTitle"))
				openStory((TaskAndStory)event.getItemId());
			else
				openTask((TaskAndStory)event.getItemId());
		}
	}
	
	private void openTask(TaskAndStory st) {
		TaskDetailsWindow w = new TaskDetailsWindow(application, st.getTask());
		application.getMainWindow().addWindow(w);
	}

	private void openStory(TaskAndStory st) {
		UserStoryViewWindow w = new UserStoryViewWindow(application, st.getStory());
		application.getMainWindow().addWindow(w);
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	private TaskManager taskManager;

	private static final long serialVersionUID = 1L;
	
}
