package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.CommentType;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

public class CommentsView 
extends VerticalLayout 
implements RefreshableComponent {

	private VerticalLayout commentsLayout;
	
	@SuppressWarnings("serial")
	public CommentsView(CommentType type, Long parentId) {
		setSizeFull();
		setSpacing(true);
		
		commentsLayout = new VerticalLayout();
		commentsLayout.setSizeFull();
		commentsLayout.setMargin(true);
		commentsLayout.setSpacing(true);
		
		Panel commentsPanel = new Panel();
		commentsPanel.setSizeFull();
		commentsPanel.setCaption("Comments");
		commentsPanel.getContent().setSizeFull();
		commentsPanel.addComponent(commentsLayout);
		addComponent(commentsPanel);
		
		TextArea newCommentText = new TextArea();
		newCommentText.setSizeFull();
		
		Button newCommentButton = new Button("New Comment");
		newCommentButton.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				newComment();
			}
		});
		
		VerticalLayout newCommentLayout = new VerticalLayout();
		newCommentLayout.setSizeFull();
		newCommentLayout.setSpacing(true);
		newCommentLayout.addComponent(newCommentText);
		newCommentLayout.addComponent(newCommentButton);
		newCommentLayout.setExpandRatio(newCommentText, 1f);
		newCommentLayout.setComponentAlignment(newCommentButton, Alignment.BOTTOM_RIGHT);
		
		Panel newCommentPanel = new Panel();
		newCommentPanel.setCaption("New Comment");
		newCommentPanel.setHeight("170px");
		newCommentPanel.setWidth("100%");
		newCommentPanel.addComponent(newCommentLayout);
		newCommentPanel.getContent().setSizeFull();
		addComponent(newCommentPanel);
		
		setExpandRatio(commentsPanel, 1f);
	}
	
	protected void newComment() {
		
	}
	
	public void refreshContent() {
		commentsLayout.removeAllComponents();
		
		// add children to commentsLayout
		Label myComment = new Label("<b>mwuttke 20.05.2012</b>: The quick brown fox jumps over the lazy dog.");
		myComment.setContentMode(Label.CONTENT_XHTML);
		commentsLayout.addComponent(myComment);
	}

	private static final long serialVersionUID = 1L;
	
}
