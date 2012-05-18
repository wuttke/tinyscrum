package eu.wuttke.tinyscrum.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.web.TinyScrumApplication;

public class IterationTable
extends Table {

	private TinyScrumApplication application;
	private BeanItemContainer<UserStory> storyContainer = new BeanItemContainer<UserStory>(UserStory.class);

	public IterationTable(TinyScrumApplication application) {
		this.application = application;

		setContainerDataSource(storyContainer);
		setSelectable(true);
		setSizeFull();
		
		setVisibleColumns(new String[]{"id", "title", "owner"});
		setColumnExpandRatio("id", 1);
		setColumnExpandRatio("title", 5);
		setColumnExpandRatio("owner", 3);
	}

	public void loadIteration(Iteration iteration) {
		storyContainer.removeAllItems();
		if (iteration != null)
			storyContainer.addAll(application.loadIterationUserStories(iteration));
	}
	
	private static final long serialVersionUID = 1L;

}
