package eu.wuttke.tinyscrum.ui;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
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
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

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
	private Button btnDeleteTask;
	
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
		btnDeleteTask = new Button("Delete Task", this);
		btnDeleteTask.setEnabled(false);
		
		// Button Bar
		HorizontalLayout buttonBar = new HorizontalLayout();
		buttonBar.addComponent(btnAddTask);
		buttonBar.addComponent(btnEditTask);
		buttonBar.addComponent(btnDeleteTask);
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
		newTaskPanel.getContent().setSizeFull();
		newTaskPanel.addComponent(newTaskLayout);
		newTaskPanel.setWidth("100%");
		addComponent(newTaskPanel);
		
		setExpandRatio(taskTable, 1f);
	}
	
	protected void addTask() {
		Task t = createEmptyTask();
		t.setName((String)newTaskTitle.getValue());
		try {
			t.setEstimate(Double.parseDouble((String)newTaskEstimate.getValue()));
		} catch (NumberFormatException e) { }
		taskManager.saveTask(t);
		
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
			Task t = (Task)taskTable.getValue();
			newOrEditTask(t);
		} else if (event.getButton() == btnDeleteTask) {
			final Task t = (Task)taskTable.getValue();
			ConfirmDialog.show(application.getMainWindow(), "Delete Task", 
					"Delete task '" + t.getName() + "'?",
			        "Yes", "No", new ConfirmDialog.Listener() {
						private static final long serialVersionUID = 1L;
						public void onClose(ConfirmDialog dialog) {
			                if (dialog.isConfirmed()) {
			        			taskManager.deleteTask(t);
			        			refreshContent();
			                }
			            }
			        });

		}
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
			}
		});
		application.getMainWindow().addWindow(editor);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		boolean enable = taskTable.getValue() != null;
		btnEditTask.setEnabled(enable);
		btnDeleteTask.setEnabled(enable);
	}
	
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	private TaskManager taskManager;
	
	private static final long serialVersionUID = 1L;

}
