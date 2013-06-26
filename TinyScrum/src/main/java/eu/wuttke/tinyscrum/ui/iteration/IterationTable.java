package eu.wuttke.tinyscrum.ui.iteration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.UserStoryManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.userstory.BaseUserStoryTable;

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
		if (iteration != null) {
			storyContainer.addAll(userStoryManager.loadIterationUserStories(iteration));
			logger.info("reloaded iteration {}", iteration.getName());
		}
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
	private static Logger logger = LoggerFactory.getLogger(IterationTable.class);

}
