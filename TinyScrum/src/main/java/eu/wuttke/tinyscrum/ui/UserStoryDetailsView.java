package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.CommentType;
import eu.wuttke.tinyscrum.domain.UserStory;

public class UserStoryDetailsView 
extends VerticalLayout 
implements RefreshableComponent {

	private TinyScrumApplication application;
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
			public void buttonClick(ClickEvent event) {
				getWindow().getParent().removeWindow(getWindow());
				application.getMainWindow().addWindow(new UserStoryEditorWindow(application, userStory));
			}
		});

		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(lblDescription);
		vl.setWidth("100%");
		vl.addComponent(editButton);
		vl.setComponentAlignment(editButton, Alignment.BOTTOM_RIGHT);
		
		Panel descriptionPanel = new Panel();
		descriptionPanel.setCaption("Description");
		descriptionPanel.addComponent(vl);

		comments = new CommentsView(CommentType.USER_STORY, userStory.getId());

		addComponent(descriptionPanel);
		addComponent(comments);		
		setExpandRatio(comments, 1f);
	}
	
	public void refreshContent() {
		comments.refreshContent();
	}
	
}
