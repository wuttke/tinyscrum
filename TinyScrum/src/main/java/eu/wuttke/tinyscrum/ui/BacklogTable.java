package eu.wuttke.tinyscrum.ui;

import java.util.List;


import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.web.TinyScrumApplication;

public class BacklogTable
extends UserStoryTable {
	
	public BacklogTable(TinyScrumApplication application) {
		super(application);
	}
	
	public void loadBacklog() {
		List<UserStory> l = application.loadBacklogUserStories();
		storyContainer.removeAllItems();
		storyContainer.addAll(l);
	}

	@Override
	protected void updateStoryParent(UserStory story) {
		story.setIteration(null);
	}
	
	private static final long serialVersionUID = -2443442356903790932L;

}
