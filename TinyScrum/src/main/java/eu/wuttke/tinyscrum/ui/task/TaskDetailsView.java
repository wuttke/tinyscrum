package eu.wuttke.tinyscrum.ui.task;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Comment;
import eu.wuttke.tinyscrum.domain.CommentType;
import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.TaskStatus;
import eu.wuttke.tinyscrum.logic.TaskManager;
import eu.wuttke.tinyscrum.ui.CommentsView;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

@Configurable(autowire=Autowire.BY_NAME)
public class TaskDetailsView 
extends VerticalLayout 
implements RefreshableComponent {
	
	private TaskManager taskManager;
	private CommentsView comments;
	
	public TaskDetailsView(final TinyScrumApplication application, final Task task) {
		setSizeFull();
		setMargin(true);
		setSpacing(true);
		
		Label lblDescription = new Label(task.getDescription());
		lblDescription.setContentMode(Label.CONTENT_XHTML);

		Button editButton = new Button("Edit Task", new ClickListener(){
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				getWindow().getParent().removeWindow(getWindow());
				application.getMainWindow().addWindow(new TaskEditorWindow(application, task, new ObjectSavedListener() {
					public void objectSaved(Object object) {
						application.getMainView().refreshContent();
					}
				}));
			}
		});
		
		Button nextStateButton = new Button(getNextStateLabel(task.getStatus()), new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				doNextState(application, task);
				getWindow().getParent().removeWindow(getWindow());
				application.getMainView().refreshContent();
			}
		});
		nextStateButton.setVisible(task.getStatus() != TaskStatus.TASK_DONE);
		
		Label lblTitle = new Label("#" + task.getId() + ": " + task.getName() + " (" + task.getStatus() + ")");
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(lblTitle);
		hl.addComponent(nextStateButton);
		hl.addComponent(editButton);
		hl.setComponentAlignment(nextStateButton, Alignment.TOP_RIGHT);
		hl.setComponentAlignment(editButton, Alignment.TOP_RIGHT);
		hl.setSpacing(true);
		hl.setWidth("100%");
		hl.setExpandRatio(lblTitle, 1f);
		
		Panel descriptionPanel = new Panel();
		descriptionPanel.setCaption("Description");
		descriptionPanel.setSizeFull();
		descriptionPanel.addComponent(lblDescription);

		comments = new CommentsView(application, CommentType.TASK, task.getId(),
				new CommentsView.CommentSavedListener() {
					private static final long serialVersionUID = 1L;
					public void commentSaved(Comment comment) {
						taskManager.getMailManager().sendTaskMail(task, "New Comment");
					}
				});

		addComponent(hl);
		addComponent(descriptionPanel);
		addComponent(comments);	
		setExpandRatio(descriptionPanel, 1f);
		setExpandRatio(comments, 2f);
	}

	private String getNextStateLabel(TaskStatus status) {
		switch (status) {
		case TASK_DONE: return "Already done";
		case TASK_OPEN: return "Mark as 'Test'";
		case TASK_TEST: return "Mark as 'Done'";
		default: throw new IllegalArgumentException("unknown state: " + status);
		}
	}

	public void doNextState(TinyScrumApplication application, Task task) {
		if (task.getStatus() == TaskStatus.TASK_OPEN) {
			task.setStatus(TaskStatus.TASK_TEST);
			taskManager.saveTask(task);
		} else if (task.getStatus() == TaskStatus.TASK_TEST) {
			task.setStatus(TaskStatus.TASK_DONE);
			taskManager.saveTask(task);
		}
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	public void refreshContent() {
		comments.refreshContent();
	}

	private static final long serialVersionUID = 1L;

}
