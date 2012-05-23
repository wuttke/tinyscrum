package eu.wuttke.tinyscrum.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

import eu.wuttke.tinyscrum.domain.TaskAndStory;
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
	}
	
	public void loadDashboardTasks(String user) {
		List<TaskAndStory> tasks = taskManager.loadUserTasks(user, application.getCurrentProject());
		setContainerDataSource(new BeanItemContainer<TaskAndStory>(TaskAndStory.class, tasks));
		setVisibleColumns(new String[]{"taskName", "storyTitle", "iterationName", "taskDeveloper", "taskTester", "taskStatus"});
		setColumnHeaders(new String[]{"Task", "Story", "Itereation Name", "Developer", "Tester", "Status"});
		addListener((ItemClickListener)this);
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
