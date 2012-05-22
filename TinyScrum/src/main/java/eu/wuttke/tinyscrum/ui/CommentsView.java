package eu.wuttke.tinyscrum.ui;

import java.io.OutputStream;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
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
		//commentsLayout.setSizeFull();
		//commentsLayout.setMargin(true);
		commentsLayout.setSpacing(true);
		
		Panel commentsPanel = new Panel();
		commentsPanel.setSizeFull();
		commentsPanel.setCaption("Comments");
		commentsPanel.getContent().setSizeFull();
		commentsPanel.addComponent(commentsLayout);
		
		TextArea newCommentText = new TextArea();
		newCommentText.setSizeFull();
		
		Upload uploadButton = new Upload(null, new Receiver() {
			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				return null;
			}
		});
		uploadButton.setButtonCaption("Upload File");
		uploadButton.setImmediate(true);
		
		Button newCommentButton = new Button("New Comment");
		newCommentButton.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				newComment();
			}
		});

		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(newCommentButton);
		hl.addComponent(uploadButton);
		hl.setSpacing(true);

		VerticalLayout newCommentLayout = new VerticalLayout();
		newCommentLayout.setSizeFull();
		newCommentLayout.setSpacing(true);
		newCommentLayout.addComponent(newCommentText);
		newCommentLayout.addComponent(hl);
		newCommentLayout.setComponentAlignment(hl, Alignment.BOTTOM_RIGHT);
		newCommentLayout.setExpandRatio(newCommentText, 1f);
		newCommentLayout.setSizeFull();
		
		Panel newCommentPanel = new Panel();
		newCommentPanel.setCaption("New Comment");
		newCommentPanel.setHeight("170px");
		newCommentPanel.setWidth("100%");
		newCommentPanel.addComponent(newCommentLayout);
		newCommentPanel.getContent().setSizeFull();

		addComponent(commentsPanel);
		addComponent(newCommentPanel);
		setExpandRatio(commentsPanel, 1f);
	}
	
	protected void newComment() {
		
	}
	
	public void refreshContent() {
		commentsLayout.removeAllComponents();
		
		for (int i = 0; i < 10; i++) {
			// add children to commentsLayout
			Label myComment = new Label("<b>mwuttke 20.05.2012</b>: The quick brown fox jumps over the lazy dog.");
			myComment.setContentMode(Label.CONTENT_XHTML);
			commentsLayout.addComponent(myComment);
	
			// add children to commentsLayout
			Label myComment2 = new Label("<b>mwuttke 21.05.2012</b>: The quick brown fox jumps over the lazy dog.");
			myComment2.setContentMode(Label.CONTENT_XHTML);
			commentsLayout.addComponent(myComment2);
		}
	}

	private static final long serialVersionUID = 1L;
	
}
