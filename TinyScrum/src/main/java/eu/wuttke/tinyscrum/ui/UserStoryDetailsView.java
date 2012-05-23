package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.CommentType;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

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

		Panel descriptionPanel = new Panel();
		descriptionPanel.setCaption("Description");
		descriptionPanel.setSizeFull();
		descriptionPanel.addComponent(lblDescription);

		comments = new CommentsView(CommentType.USER_STORY, userStory.getId());
		
		addComponent(editButton);
		addComponent(descriptionPanel);
		addComponent(comments);		
		setComponentAlignment(editButton, Alignment.TOP_RIGHT);
		setExpandRatio(descriptionPanel, 1f);
		setExpandRatio(comments, 2f);
	}
	
	public void refreshContent() {
		comments.refreshContent();
	}

	private static final long serialVersionUID = 1L;

}
