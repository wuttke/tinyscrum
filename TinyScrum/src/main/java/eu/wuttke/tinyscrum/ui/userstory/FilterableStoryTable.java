package eu.wuttke.tinyscrum.ui.userstory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.domain.UserStoryFilter;
import eu.wuttke.tinyscrum.logic.UserStoryManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;

@Configurable(autowire=Autowire.BY_NAME)
public class FilterableStoryTable 
extends BaseUserStoryTable {

	public FilterableStoryTable() {
		super(null);
		init();
	}

	public FilterableStoryTable(TinyScrumApplication application) {
		super(application);
		init();
	}
	
	private void init() {
		setVisibleColumns(new String[]{"id", "customerProject", "title", "owner", 
				"iterationName", "projectFeature", 
				"projectRelease", "estimate", 
				"currentDueDate", "latestDueDate", "status"});
		setColumnHeader("iterationName", "Iteration Name");
		setColumnHeader("projectRelease", "Release Name");
		setColumnHeader("latestDueDate", "Latest Due Date");
		setColumnHeader("currentDueDate", "Current Due Date");
		setColumnHeader("customerProject", "Customer Project");
		setColumnExpandRatio("iterationName", 3);
		setColumnExpandRatio("projectRelease", 2);
		setDragMode(TableDragMode.NONE);
		setSortDisabled(false);
	}
	
	public void loadStories(UserStoryFilter filter) {
		List<UserStory> l = userStoryManager.loadUserStories(filter);
		storyContainer.removeAllItems();
		storyContainer.addAll(l);
		recalculateFooter();
	}
	
	public void setUserStoryManager(UserStoryManager userStoryManager) {
		this.userStoryManager = userStoryManager;
	}

	private UserStoryManager userStoryManager;
	
	private static final long serialVersionUID = 1L;

}
