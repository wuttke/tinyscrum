package eu.wuttke.tinyscrum.ui;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.web.TinyScrumApplication;

public class IterationTable
extends UserStoryTable {

	private Iteration iteration;
	
	public IterationTable(TinyScrumApplication application) {
		super(application);
	}

	public void loadIteration(Iteration iteration) {
		this.iteration = iteration;
		storyContainer.removeAllItems();
		if (iteration != null)
			storyContainer.addAll(application.loadIterationUserStories(iteration));
	}
	
	@Override
	protected void updateStoryParent(UserStory story) {
		story.setIteration(iteration);
	}
	
	private static final long serialVersionUID = 1L;

}
