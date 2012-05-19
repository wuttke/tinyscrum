package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.TaskStatus;
import eu.wuttke.tinyscrum.domain.UserStory;

public class UserStoryTasksView 
extends VerticalLayout
implements RefreshableComponent {

	private TinyScrumApplication application;
	private UserStory story;
	private TaskTable taskTable;
	
	public UserStoryTasksView(TinyScrumApplication application, UserStory userStory) {
		this.application = application;
		this.story = userStory;
		
		initializeLayout();
	}

	private void initializeLayout() {
		setSizeFull();
		setMargin(true);
		setSpacing(true);

		taskTable = new TaskTable(story);
		addComponent(taskTable);
		
		Button addTask = new Button("Add Task", new ClickListener() {
			public void buttonClick(ClickEvent event) {
				addTask();
			}
		});
		
		Button deleteTask = new Button("Delete Task", new ClickListener() {
			public void buttonClick(ClickEvent event) {
				deleteTask();
			}
		});

		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.addComponent(addTask);
		layout.addComponent(deleteTask);
		addComponent(layout);
		
		setExpandRatio(taskTable, 1f);
		setComponentAlignment(layout, Alignment.BOTTOM_RIGHT);
		
		// TODO später
		taskTable.loadTasks();
	}
	
	protected void addTask() {
		Task t = new Task();
		t.setStory(story);
		t.setProject(story.getProject());
		t.setStatus(TaskStatus.TASK_OPEN);
		t.setName("");
		taskTable.addTask(t);
	}
	
	protected void deleteTask() {
	}
	
	@Override
	public void refreshContent() {
		taskTable.loadTasks();
	}

	private static final long serialVersionUID = 1L;

}
