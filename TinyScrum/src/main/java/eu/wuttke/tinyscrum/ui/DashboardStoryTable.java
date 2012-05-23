package eu.wuttke.tinyscrum.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.UserStoryManager;

@Configurable(autowire=Autowire.BY_NAME)
public class DashboardStoryTable
extends BaseUserStoryTable {

	public DashboardStoryTable(TinyScrumApplication application) {
		super(application);
		setDragMode(TableDragMode.NONE);
		setSizeFull();
		
		setVisibleColumns(new String[]{"id", "title", "owner", "iterationName", "estimate", "status"});
		setColumnHeader("iterationName", "Iteration Name");
		setColumnExpandRatio("iterationName", 3);
	}
	
	public void loadDashboardStories(String user) {
		List<UserStory> stories = userStoryManager.loadUserUserStories(user, application.getCurrentProject());
		storyContainer.removeAllItems();
		storyContainer.addAll(stories);
		recalculateFooter();
	}

	public void setUserStoryManager(UserStoryManager userStoryManager) {
		this.userStoryManager = userStoryManager;
	}
	
	private UserStoryManager userStoryManager;
	
	private static final long serialVersionUID = 1L;

}
