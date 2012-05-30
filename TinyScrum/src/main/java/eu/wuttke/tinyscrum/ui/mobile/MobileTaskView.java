package eu.wuttke.tinyscrum.ui.mobile;

import java.text.DateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

import eu.wuttke.tinyscrum.domain.Comment;
import eu.wuttke.tinyscrum.domain.CommentType;
import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.TaskStatus;
import eu.wuttke.tinyscrum.domain.UserStory;

public class MobileTaskView 
extends NavigationView {

	private Task task;
	private UserStory story;
	
	public MobileTaskView(Task task, UserStory story) {
		this.task = task;
		this.story = story;
		setCaption(task.getName());
	}
	
	@Override
	public void attach() {
		super.attach();

		VerticalComponentGroup group = new VerticalComponentGroup();
		group.setCaption("Task #" + task.getId() + " (" + task.getStatus() + ")");
		
		group.addComponent(new Label(task.getName()));
		group.addComponent(new Label(task.getDescription(), Label.CONTENT_XHTML));
		group.addComponent(new Label(story.getTitle()));
		group.addComponent(new Label(story.getIterationName()));
		group.addComponent(new Label("Developer: " + task.getDeveloper()));
		group.addComponent(new Label("Tester: " + task.getTester()));
		group.addComponent(new Label("Estimate: " + task.getEstimate() + " " + ((TinyScrumMobileApplication)getApplication()).getCurrentProject().getTaskEstimateUnit()));

		EntityManager em = Comment.entityManager();
		TypedQuery<Comment> q1 = em.createQuery("FROM Comment WHERE commentType = ? AND parentId = ? ORDER BY createDateTime", Comment.class);
		q1.setParameter(1, CommentType.TASK);
		q1.setParameter(2, task.getId());
		List<Comment> comments = q1.getResultList();

		VerticalComponentGroup commentsGroup = new VerticalComponentGroup("Comments");
		for (Comment comment : comments)
			commentsGroup.addComponent(new Label(comment.getComment() + 
					" (" + comment.getUserName() + " " + 
					DateFormat.getDateInstance(DateFormat.SHORT).format(comment.getCreateDateTime()) + ")"));
		
		Button changeStatusButton = null;
		if (task.getStatus() == TaskStatus.TASK_OPEN)
			changeStatusButton = new Button("Set Status: Test");
		else if (task.getStatus() == TaskStatus.TASK_TEST)
			changeStatusButton = new Button("Set Status: Done");
		
		CssLayout layout = new CssLayout();
		layout.setWidth("100%");
		layout.addComponent(group);
		layout.addComponent(commentsGroup);

		if (changeStatusButton != null) {
			changeStatusButton.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
					changeTaskStatus();
				}
			});
			
			// verpacken in eine Gruppe?
			VerticalComponentGroup changeStatusGroup = new VerticalComponentGroup();
			changeStatusGroup.addComponent(changeStatusButton);
			layout.addComponent(changeStatusGroup);
		}
		
		setContent(layout);
	}

	protected void changeTaskStatus() {
		// TODO Auto-generated method stub
		
	}

	private static final long serialVersionUID = 1L;

}
