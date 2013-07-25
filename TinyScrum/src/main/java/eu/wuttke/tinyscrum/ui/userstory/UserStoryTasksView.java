package eu.wuttke.tinyscrum.ui.userstory;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.DoubleValidator;
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
import eu.wuttke.tinyscrum.logic.TaskManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;
import eu.wuttke.tinyscrum.ui.task.TaskEditorWindow;
import eu.wuttke.tinyscrum.ui.task.TaskTable;

@Configurable(autowire=Autowire.BY_NAME)
public class UserStoryTasksView 
extends VerticalLayout
implements RefreshableComponent, ClickListener, ValueChangeListener {

	private TinyScrumApplication application;
	private UserStory story;
	private TaskTable taskTable;
	private TextField newTaskTitle;
	private TextField newTaskEstimate;
	private Button btnAddTask;
	private Button btnEditTask;
	private Button btnDeleteTasks;
	private Button btnMoveTasks;
	
	public UserStoryTasksView(TinyScrumApplication application, UserStory userStory) {
		this.application = application;
		this.story = userStory;
		
		initializeLayout();
	}

	private void initializeLayout() {
		setSizeFull();
		setMargin(true);
		setSpacing(true);

		taskTable = new TaskTable(application, story);
		taskTable.addListener((ValueChangeListener)this);
		taskTable.setImmediate(true);
		addComponent(taskTable);
		
		// Buttons
		btnAddTask = new Button("Add Task", this);
		btnEditTask = new Button("Edit Task", this);
		btnEditTask.setEnabled(false);
		btnMoveTasks = new Button("Move Tasks", this);
		btnMoveTasks.setEnabled(false);
		btnDeleteTasks = new Button("Delete Tasks", this);
		btnDeleteTasks.setEnabled(false);
		
		// Button Bar
		HorizontalLayout buttonBar = new HorizontalLayout();
		buttonBar.addComponent(btnAddTask);
		buttonBar.addComponent(btnEditTask);
		buttonBar.addComponent(btnMoveTasks);
		buttonBar.addComponent(btnDeleteTasks);
		buttonBar.setSpacing(true);
		addComponent(buttonBar);
		
		newTaskTitle = new TextField();
		newTaskTitle.setInputPrompt("Task Title");
		newTaskTitle.setWidth("100%");
		newTaskTitle.setImmediate(true);
		
		newTaskEstimate = new TextField();
		newTaskEstimate.setInputPrompt("Estimate");
		newTaskEstimate.setWidth("80px");
		newTaskEstimate.addValidator(new DoubleValidator("Please enter a number."));
		newTaskEstimate.setImmediate(true);
		
		@SuppressWarnings("serial")
		Button addTaskButton = new Button("Add Task", new ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (((String)newTaskTitle.getValue()).length() > 0)
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
		newTaskPanel.getContent().setWidth("100%");
		newTaskPanel.addComponent(newTaskLayout);
		newTaskPanel.setWidth("100%");
		addComponent(newTaskPanel);
		
		setExpandRatio(taskTable, 1f);
	}
	
	@Transactional
	protected void addTask() {
		Task t = createEmptyTask();
		t.setName((String)newTaskTitle.getValue());
		try {
			t.setEstimate(Double.parseDouble((String)newTaskEstimate.getValue()));
		} catch (NumberFormatException e) { }
		taskManager.saveTask(t);
		taskManager.calculateStoryEffort(story);
		
		taskTable.loadTasks();
		
		// Reset field for next task
		newTaskTitle.setValue("");
		newTaskEstimate.setValue("");
		newTaskTitle.selectAll();
	}
	
	@Override
	public void refreshContent() {
		taskTable.loadTasks();
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == btnAddTask) {
			newOrEditTask(createEmptyTask());
		} else if (event.getButton() == btnEditTask) {
			Task t = taskTable.getFirstSelectedTask();
			if (t != null)
				newOrEditTask(t);
		} else if (event.getButton() == btnMoveTasks) {
			moveSelectedTasks();
		} else if (event.getButton() == btnDeleteTasks) {
			deleteSelectedTasks();
		}
	}
	
	private void moveSelectedTasks() {
		UserStorySelectWindow w = new UserStorySelectWindow(application, taskTable.getSelectedTasks(), new ObjectSavedListener() {
			public void objectSaved(Object object) {
				logger.info("moved tasks");
				refreshContent();
			}
		});
		application.getMainWindow().addWindow(w);
	}

	private void deleteSelectedTasks() {
		final Set<Task> tasks = taskTable.getSelectedTasks();
		if (tasks == null || tasks.size() == 0)
			return;
		
		String message;
		if (tasks.size() == 1)
			message = String.format("Delete task '%s'?", tasks.iterator().next().getName());
		else
			message = String.format("Delete %d tasks?", tasks.size());
		
		ConfirmDialog.show(application.getMainWindow(), "Delete Tasks", 
				message,
		        "Yes", "No", new ConfirmDialog.Listener() {
					private static final long serialVersionUID = 1L;
					public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		            		for (Task task : tasks)
		            			taskManager.deleteTask(task);
		            		taskManager.calculateStoryEffort(story);
		            		refreshContent();
		            		application.getMainView().refreshContent();
		                }
		            }
		        });
	}

	private Task createEmptyTask() {
		Task t = new Task();
		t.setStory(story);
		t.setProject(story.getProject());
		t.setStatus(TaskStatus.TASK_OPEN);
		t.setName("");
		t.setEstimate(null);
		t.setDescription("");
		return t;
	}

	private void newOrEditTask(Task t) {
		TaskEditorWindow editor = new TaskEditorWindow(application, t, new ObjectSavedListener() {
			public void objectSaved(Object object) {
				refreshContent();
				application.getMainView().refreshContent(); // story effort/estimate
			}
		});
		application.getMainWindow().addWindow(editor);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		boolean enable = taskTable.getValue() != null;
		btnEditTask.setEnabled(enable);
		btnMoveTasks.setEnabled(enable);
		btnDeleteTasks.setEnabled(enable);
	}
	
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	private TaskManager taskManager;
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(UserStoryTasksView.class);
	
}
