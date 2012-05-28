package eu.wuttke.tinyscrum.ui;

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

import eu.wuttke.tinyscrum.domain.CommentType;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.domain.UserStoryStatus;
import eu.wuttke.tinyscrum.logic.UserStoryManager;
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
				application.getMainWindow().addWindow(new UserStoryEditorWindow(application, userStory, new ObjectSavedListener() {
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
				if (splitStory(application, userStory)) {
					getWindow().getParent().removeWindow(getWindow());
					application.getMainView().refreshContent();
				}
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

		comments = new CommentsView(application, CommentType.USER_STORY, userStory.getId());
		
		addComponent(hl);
		addComponent(descriptionPanel);
		addComponent(comments);		
		setExpandRatio(descriptionPanel, 1f);
		setExpandRatio(comments, 2f);
	}
	
	protected boolean splitStory(TinyScrumApplication application,
			UserStory userStory) {
		// TODO Auto-generated method stub
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

	private UserStoryManager userStoryManager;
	
	private static final long serialVersionUID = 1L;

}
