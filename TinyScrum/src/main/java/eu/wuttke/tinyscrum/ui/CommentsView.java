package eu.wuttke.tinyscrum.ui;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;

import com.vaadin.event.ComponentEventListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

import eu.wuttke.tinyscrum.domain.Comment;
import eu.wuttke.tinyscrum.domain.CommentType;
import eu.wuttke.tinyscrum.domain.FileUpload;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

public class CommentsView 
extends VerticalLayout 
implements RefreshableComponent {

	private VerticalLayout commentsLayout;
	private TextArea newCommentText;
	
	private CommentType commentType;
	private Long parentId;
	
	private TinyScrumApplication application;
	
	private ByteArrayOutputStream lastUploadStream;
	private String lastUploadFileName;
	private String lastUploadMimetype;
	
	private CommentSavedListener commentSavedListener;
	
	public CommentsView(TinyScrumApplication application, CommentType type, Long parentId, CommentSavedListener commentSavedListener) {
		this.application = application;
		this.commentType = type;
		this.parentId = parentId;
		this.commentSavedListener = commentSavedListener;
		
		setSizeFull();
		setSpacing(true);
		
		commentsLayout = new VerticalLayout();
		commentsLayout.setSpacing(true);
		
		Panel commentsPanel = new Panel();
		commentsPanel.setSizeFull();
		commentsPanel.setCaption("Comments");
		//commentsPanel.getContent().setSizeFull();
		commentsPanel.addComponent(commentsLayout);
		
		newCommentText = new TextArea();
		newCommentText.setSizeFull();
		
		Upload uploadButton = new Upload(null, new Receiver() {
			private static final long serialVersionUID = 1L;
			public OutputStream receiveUpload(String filename, String mimeType) {
				lastUploadStream = new ByteArrayOutputStream();
				lastUploadFileName = filename;
				lastUploadMimetype = mimeType;
				return lastUploadStream;
			}
		});
		uploadButton.setButtonCaption("Upload File");
		uploadButton.setImmediate(true);
		uploadButton.addListener(new Upload.SucceededListener() {
			private static final long serialVersionUID = 1L;
			public void uploadSucceeded(SucceededEvent event) {
				processUpload();
			}
		});
		
		Button newCommentButton = new Button("New Comment");
		newCommentButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				newComment();
			}
		});

		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(uploadButton);
		hl.addComponent(newCommentButton);
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
	
	protected void processUpload() {
		FileUpload u = new FileUpload();
		u.setBinaryData(lastUploadStream.toByteArray());
		u.setCommentType(commentType);
		u.setFileName(lastUploadFileName);
		u.setFileSize(u.getBinaryData().length);
		u.setMimeType(lastUploadMimetype);
		u.setParentId(parentId);
		u.setCreateDateTime(new Date());
		u.setUserName(application.getCurrentUser().getUserName());
		lastUploadStream = null;
		
		int maxSize = 1048576 / 2; // MySQL max_allowed_packet
		if (u.getFileSize() >= maxSize) {
			application.getMainWindow().showNotification("Your upload has not been accepted because it is too large (" + u.getMimeType() + ", " +
					u.getFileSize() + " bytes). The maximum supported file size is " + maxSize + " bytes.", Notification.TYPE_HUMANIZED_MESSAGE);
			return;
		}
		
		u.persist();
		
		application.getMainWindow().showNotification("Your upload has been received successfully (" + u.getMimeType() + ", " +
				u.getFileSize() + " bytes).", Notification.TYPE_HUMANIZED_MESSAGE);
		
		refreshContent();
	}

	protected void newComment() {
		Comment comment = new Comment();
		comment.setComment((String)newCommentText.getValue());
		comment.setCommentType(commentType);
		comment.setCreateDateTime(new Date());
		comment.setParentId(parentId);
		comment.setUserName(application.getCurrentUser().getUserName());
		comment.persist();
		
		newCommentText.setValue("");
		refreshContent();
		
		if (commentSavedListener != null)
			commentSavedListener.commentSaved(comment);
	}
	
	public void refreshContent() {
		EntityManager em = Comment.entityManager();
		TypedQuery<Comment> q1 = em.createQuery("FROM Comment WHERE commentType = ? AND parentId = ? ORDER BY createDateTime", Comment.class);
		q1.setParameter(1, commentType);
		q1.setParameter(2, parentId);
		List<Comment> comments = q1.getResultList();
		
		TypedQuery<Object[]> q2 = em.createQuery("SELECT id, fileSize, fileName, mimeType, createDateTime, userName FROM FileUpload WHERE commentType = ? AND parentId = ? ORDER BY createDateTime", Object[].class);
		q2.setParameter(1, commentType);
		q2.setParameter(2, parentId);
		List<Object[]> files = q2.getResultList();
		
		ArrayList<CommentDateObject> list = new ArrayList<CommentDateObject>(); 
		for (Comment comment : comments) {
			CommentDateObject cdo = new CommentDateObject();
			cdo.comment = comment;
			cdo.date = comment.getCreateDateTime();
			list.add(cdo);
		}
		
		for (Object[] file : files) {
			CommentDateObject cdo = new CommentDateObject();
			cdo.upload = file;
			cdo.date = (Date)file[4];
			list.add(cdo);
		}
		Collections.sort(list);
		
		commentsLayout.removeAllComponents();
		DateFormat df = new SimpleDateFormat();
		for (CommentDateObject cdo : list) {
			if (cdo.comment != null) {
				// add comment
				Label myComment = new Label(cdo.comment.getComment() + " (<i>" + 
						cdo.comment.getUserName() + ", " + df.format(cdo.comment.getCreateDateTime()) + "</i>)");
				myComment.setContentMode(Label.CONTENT_XHTML);
				commentsLayout.addComponent(myComment);
			} else {
				WebApplicationContext wc = (WebApplicationContext)application.getContext();
				ServletContext ctx = wc.getHttpSession().getServletContext();
				String ctxPath = ctx.getContextPath();
				
				// add upload
				long id = (Long)cdo.upload[0];
				long size = (Long)cdo.upload[1];
				String fn = (String)cdo.upload[2];
				String mt = (String)cdo.upload[3];
				Date cdt = (Date)cdo.upload[4];
				String un = (String)cdo.upload[5];
				String capt = fn + " (" + mt + ", " + size + " bytes, " + un + ", " + df.format(cdt) + ")";
				Resource res = new ExternalResource(ctxPath + "/getFile?binaryId=" + id, mt); // Kontextname?
				Link l = new Link(capt, res);
				l.setTargetName("_blank");
				commentsLayout.addComponent(l);
			}
		}
	}
	
	class CommentDateObject implements Comparable<CommentDateObject> {
		public Date date;
		public Comment comment;
		public Object[] upload;
		
		@Override
		public int compareTo(CommentDateObject o) {
			return date.compareTo(o.date);
		}
	}

	public interface CommentSavedListener extends ComponentEventListener {
		public void commentSaved(Comment comment);
	}

	private static final long serialVersionUID = 1L;
	
}
