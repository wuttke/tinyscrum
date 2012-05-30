package eu.wuttke.tinyscrum.ui.mobile;

import java.text.DateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

import eu.wuttke.tinyscrum.domain.Comment;
import eu.wuttke.tinyscrum.domain.CommentType;
import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.TaskManager;

@Configurable(autowire=Autowire.BY_NAME)
public class MobileStoryView extends NavigationView {

	private UserStory story;
	
	public MobileStoryView(UserStory story) {
		this.story = story;
		setCaption(story.getTitle());
	}
	
	@Override
	@Transactional
	public void attach() {
		super.attach();

		story = UserStory.findUserStory(story.getId()); // re-attach to session
		
		VerticalComponentGroup group = new VerticalComponentGroup();
		group.setCaption("Story #" + story.getId() + " (" + story.getStatus() + ")");

		group.addComponent(new Label(story.getTitle()));
		group.addComponent(new Label(story.getDescription(), Label.CONTENT_XHTML));
		group.addComponent(new Label(story.getIterationName()));
		group.addComponent(new Label("Owner: " + story.getOwner()));
		group.addComponent(new Label("Estimate: " + story.getEstimate() + " " + ((TinyScrumMobileApplication)getApplication()).getCurrentProject().getStoryEstimateUnit()));

		EntityManager em = Comment.entityManager();
		TypedQuery<Comment> q1 = em.createQuery("FROM Comment WHERE commentType = ? AND parentId = ? ORDER BY createDateTime", Comment.class);
		q1.setParameter(1, CommentType.USER_STORY);
		q1.setParameter(2, story.getId());
		List<Comment> comments = q1.getResultList();
		
		VerticalComponentGroup commentsGroup = new VerticalComponentGroup("Comments");
		for (Comment comment : comments)
			commentsGroup.addComponent(new Label(comment.getComment() + 
					" (" + comment.getUserName() + " " + 
					DateFormat.getDateInstance(DateFormat.SHORT).format(comment.getCreateDateTime()) + ")"));

		List<Task> tasks = taskManager.loadTasksForStory(story);
		
		VerticalComponentGroup tasksGroup = new VerticalComponentGroup("Tasks");
		for (Task task : tasks) {
			NavigationButton taskButton = new NavigationButton(new MobileTaskView(task, story));
			tasksGroup.addComponent(taskButton);
		}
		
		CssLayout layout = new CssLayout();
		layout.setWidth("100%");
		layout.addComponent(group);
		layout.addComponent(commentsGroup);
		layout.addComponent(tasksGroup);
		setContent(layout);
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	private TaskManager taskManager;
	
	private static final long serialVersionUID = 1L;
	
}
