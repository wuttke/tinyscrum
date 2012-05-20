package eu.wuttke.tinyscrum.ui;

import org.hibernate.ejb.packaging.XmlHelper;

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
	
	public UserStoryDetailsView(TinyScrumApplication application, UserStory userStory) {
		this.application = application;
		this.userStory = userStory;
		
		setSizeFull();
		setMargin(true);
		setSpacing(true);
		
		Label lblDescription = new Label(userStory.getDescription());
		lblDescription.setContentMode(Label.CONTENT_XHTML);
		
		Panel descriptionPanel = new Panel();
		descriptionPanel.setCaption("Description");
		descriptionPanel.addComponent(lblDescription);
		addComponent(descriptionPanel);
		
		comments = new CommentsView(CommentType.USER_STORY, userStory.getId());
		addComponent(comments);
		
		setExpandRatio(comments, 1f);
	}
	
	public void refreshContent() {
		comments.refreshContent();
	}
	
}
