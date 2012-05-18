package eu.wuttke.tinyscrum.ui;

import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.web.TinyScrumApplication;

public class BacklogTable
extends Table {
	
	private BeanItemContainer<UserStory> backlogContainer = new BeanItemContainer<UserStory>(UserStory.class);
	private TinyScrumApplication application;
	
	public BacklogTable(TinyScrumApplication application) {
		this.application = application;
		
		setContainerDataSource(backlogContainer);
		setSelectable(true);
		setSizeFull();
		
		setVisibleColumns(new String[]{"id", "title", "owner"});
		setColumnExpandRatio("id", 1);
		setColumnExpandRatio("title", 5);
		setColumnExpandRatio("owner", 3);
	}
	
	public void loadBacklog() {
		List<UserStory> l = application.loadBacklogUserStories();
		backlogContainer.removeAllItems();
		backlogContainer.addAll(l);
	}

	private static final long serialVersionUID = -2443442356903790932L;

}
