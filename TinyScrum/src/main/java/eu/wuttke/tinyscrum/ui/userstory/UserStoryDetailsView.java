package eu.wuttke.tinyscrum.ui.userstory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Comment;
import eu.wuttke.tinyscrum.domain.CommentType;
import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.TaskStatus;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.domain.UserStoryStatus;
import eu.wuttke.tinyscrum.logic.TaskManager;
import eu.wuttke.tinyscrum.logic.UserStoryManager;
import eu.wuttke.tinyscrum.ui.CommentsView;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

@Configurable(autowire=Autowire.BY_NAME)
public class UserStoryDetailsView 
extends VerticalLayout 
implements RefreshableComponent {

	@SuppressWarnings("unused")
	private TinyScrumApplication application;
	@SuppressWarnings("unused")
	private UserStory userStory;
	private CommentsView comments;
	
	public UserStoryDetailsView(final TinyScrumApplication application, final UserStory userStory) {
		this.application = application;
		this.userStory = userStory;
		
		setSizeFull();
		setMargin(true);
		setSpacing(true);
		
		Label lblDescription = new Label(userStory.getDescription());
		lblDescription.setContentMode(Label.CONTENT_XHTML);

		Button editButton = new Button("Edit User Story", new ClickListener(){
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				getWindow().getParent().removeWindow(getWindow());
				UserStory story = UserStory.findUserStory(userStory.getId());
				application.getMainWindow().addWindow(new UserStoryEditorWindow(application, story, new ObjectSavedListener() {
					public void objectSaved(Object object) {
						application.getMainView().refreshContent();
					}
				}));
			}
		});
		
		Button nextStateButton = new Button(getNextStateLabel(userStory.getStatus()), new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				doNextState(application, userStory);
				getWindow().getParent().removeWindow(getWindow());
				application.getMainView().refreshContent();
			}
		});
		nextStateButton.setVisible(userStory.getStatus() != UserStoryStatus.STORY_DONE);
		
		Button splitStoryButton = new Button("Split Story", new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				splitStory(application, userStory);
			}
		});

		Label lblTitle = new Label("#" + userStory.getId() + ": " + userStory.getTitle() + " (" + userStory.getStatus() + ")");
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(lblTitle);
		hl.addComponent(nextStateButton);
		hl.addComponent(splitStoryButton);
		hl.addComponent(editButton);
		hl.setComponentAlignment(nextStateButton, Alignment.TOP_RIGHT);
		hl.setComponentAlignment(splitStoryButton, Alignment.TOP_RIGHT);
		hl.setComponentAlignment(editButton, Alignment.TOP_RIGHT);
		hl.setSpacing(true);
		hl.setWidth("100%");
		hl.setExpandRatio(lblTitle, 1f);

		Panel descriptionPanel = new Panel();
		descriptionPanel.setCaption("Description");
		descriptionPanel.setSizeFull();
		descriptionPanel.addComponent(lblDescription);

		comments = new CommentsView(application, CommentType.USER_STORY, userStory.getId(),
			new CommentsView.CommentSavedListener() {
				private static final long serialVersionUID = 1L;
				public void commentSaved(Comment comment) {
					userStoryManager.getMailManager().sendStoryMail(userStory, "New Comment");
				}
			});

		addComponent(hl);
		addComponent(descriptionPanel);
		addComponent(comments);		
		setExpandRatio(descriptionPanel, 1f);
		setExpandRatio(comments, 2f);
	}
	
	protected void splitStory(final TinyScrumApplication application,
			final UserStory userStory) {
		ConfirmDialog.show(application.getMainWindow(), "Split User Story", 
				"Split user story '" + userStory.getTitle() + "'? Unfinished tasks will be moved to a new user story in the backlog.",
		        "Yes", "No", new ConfirmDialog.Listener() {
					private static final long serialVersionUID = 1L;
					public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
		        			if (reallySplitStory(application, userStory)) {
			    				getWindow().getParent().removeWindow(getWindow());
			    				application.getMainView().refreshContent();
		        			}
		                }
		            }
		        });
	}

	protected boolean reallySplitStory(TinyScrumApplication application,
			UserStory userStory) {
		int open = 0, notOpen = 0;
		
		List<Task> tasks = taskManager.loadTasksForStory(userStory);
		for (Task task : tasks)
			if (task.getStatus() == TaskStatus.TASK_OPEN)
				open++;
			else
				notOpen++;
		
		if (open > 0 && notOpen > 0) {
			UserStory us2 = new UserStory();
			us2.setDescription(userStory.getDescription());
			us2.setEstimate(userStory.getEstimate());
			us2.setOwner(userStory.getOwner());
			us2.setProject(userStory.getProject());
			us2.setProjectFeature(userStory.getProjectFeature());
			us2.setProjectRelease(userStory.getProjectRelease());
			us2.setTitle(userStory.getTitle() + " (2)");
			UserStory newStory = userStoryManager.saveUserStory(us2);
			
			for (Task task : tasks) {
				if (task.getStatus() == TaskStatus.TASK_OPEN) {
					task.setStory(newStory);
					taskManager.saveTask(task);
				}
			}
			
			taskManager.calculateStoryEffort(userStory);
			taskManager.calculateStoryEffort(newStory);
			
			return true;
		} else if (open == 0) {
			application.getMainWindow().showNotification("The user story does not contain open tasks: Splitting is not possible.", 
					Notification.TYPE_TRAY_NOTIFICATION);
		} else if (notOpen == 0) {
			application.getMainWindow().showNotification("The user story contains only open tasks: Splitting is not possible.", 
					Notification.TYPE_TRAY_NOTIFICATION);
		}
		return false;
	}

	private String getNextStateLabel(UserStoryStatus status) {
		switch (status) {
		case STORY_DONE: return "Already done";
		case STORY_OPEN: return "Mark as 'Test'";
		case STORY_TEST: return "Mark as 'Done'";
		default: throw new IllegalArgumentException("unknown state: " + status);
		}
	}

	public void doNextState(TinyScrumApplication application, UserStory story) {
		if (story.getStatus() == UserStoryStatus.STORY_OPEN) {
			story.setStatus(UserStoryStatus.STORY_TEST);
			userStoryManager.saveUserStory(story);
		} else if (story.getStatus() == UserStoryStatus.STORY_TEST) {
			story.setStatus(UserStoryStatus.STORY_DONE);
			userStoryManager.saveUserStory(story);
		}
	}
	
	public void refreshContent() {
		comments.refreshContent();
	}
	
	public void setUserStoryManager(UserStoryManager userStoryManager) {
		this.userStoryManager = userStoryManager;
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	private UserStoryManager userStoryManager;
	private TaskManager taskManager;
	
	private static final long serialVersionUID = 1L;

}
