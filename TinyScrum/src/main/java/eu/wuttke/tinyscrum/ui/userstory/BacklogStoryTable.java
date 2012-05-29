package eu.wuttke.tinyscrum.ui.userstory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;


import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.UserStoryManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;

@Configurable(autowire=Autowire.BY_NAME)
public class BacklogStoryTable
extends BaseUserStoryTable {
	
	public BacklogStoryTable(TinyScrumApplication application) {
		super(application);
	}
	
	public void loadBacklog() {
		List<UserStory> l = userStoryManager.loadBacklogUserStories(application.getCurrentProject());
		storyContainer.removeAllItems();
		storyContainer.addAll(l);
		recalculateFooter();
	}

	@Override
	protected void updateStoryParent(UserStory story) {
		story.setIteration(null);
	}
	
	public void setUserStoryManager(UserStoryManager userStoryManager) {
		this.userStoryManager = userStoryManager;
	}
	
	private UserStoryManager userStoryManager;

	private static final long serialVersionUID = -2443442356903790932L;

}
