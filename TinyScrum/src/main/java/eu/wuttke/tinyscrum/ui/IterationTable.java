package eu.wuttke.tinyscrum.ui;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.UserStoryManager;

@Configurable(autowire=Autowire.BY_NAME)
public class IterationTable
extends BaseUserStoryTable {

	private Iteration iteration;
	
	public IterationTable(TinyScrumApplication application) {
		super(application);
	}

	public void loadIteration(Iteration iteration) {
		this.iteration = iteration;
		storyContainer.removeAllItems();
		if (iteration != null)
			storyContainer.addAll(userStoryManager.loadIterationUserStories(iteration));
		recalculateFooter();
	}
	
	@Override
	protected void updateStoryParent(UserStory story) {
		story.setIteration(iteration);
	}

	public void setUserStoryManager(UserStoryManager userStoryManager) {
		this.userStoryManager = userStoryManager;
	}
	
	private UserStoryManager userStoryManager;

	private static final long serialVersionUID = 1L;

}
