package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
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
	private TextField newTaskTitle;
	private TextField newTaskEstimate;
	
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
		
		newTaskTitle = new TextField();
		newTaskTitle.setInputPrompt("Task Title");
		newTaskTitle.setWidth("100%");
		
		newTaskEstimate = new TextField();
		newTaskEstimate.setInputPrompt("Estimate");
		newTaskEstimate.setWidth("80px");

		@SuppressWarnings("serial")
		Button addTaskButton = new Button("Add Task", new ClickListener() {
			public void buttonClick(ClickEvent event) {
				addTask();
			}
		});
		
		HorizontalLayout newTaskLayout = new HorizontalLayout();
		newTaskLayout.setSpacing(true);
		newTaskLayout.addComponent(newTaskTitle);
		newTaskLayout.addComponent(newTaskEstimate);
		newTaskLayout.addComponent(addTaskButton);
		newTaskLayout.setWidth("100%");
		newTaskLayout.setExpandRatio(newTaskTitle, 1f);
		
		Panel newTaskPanel = new Panel();
		newTaskPanel.setCaption("New Task");
		newTaskPanel.getContent().setSizeFull();
		newTaskPanel.addComponent(newTaskLayout);
		newTaskPanel.setWidth("100%");
		addComponent(newTaskPanel);
		
		setExpandRatio(taskTable, 1f);
		
		// TODO später
		taskTable.loadTasks();
	}
	
	protected void addTask() {
		Task t = new Task();
		t.setStory(story);
		t.setProject(story.getProject());
		t.setStatus(TaskStatus.TASK_OPEN);
		t.setName((String)newTaskTitle.getValue());
		// TODO Estimate
		taskTable.addTask(t);
		
		// Reset field for next task
		newTaskTitle.setValue("");
		newTaskEstimate.setValue("");
		newTaskTitle.selectAll();
	}
	
	@Override
	public void refreshContent() {
		taskTable.loadTasks();
	}

	private static final long serialVersionUID = 1L;

}
