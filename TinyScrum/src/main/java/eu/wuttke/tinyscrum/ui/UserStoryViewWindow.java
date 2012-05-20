package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.UserStory;

public class UserStoryViewWindow
extends Window {

	public UserStoryViewWindow(TinyScrumApplication application, UserStory userStory) {
		setModal(true);
        setWidth("80%");
        setHeight("80%");
        setCaption("User Story: " + userStory.getTitle());
        getContent().setSizeFull();
        center();
		
		TabSheet tabSheet = new TabSheet();
		tabSheet.setSizeFull();
		tabSheet.addTab(new UserStoryDetailsView(application, userStory), "Overview");
		tabSheet.addTab(new UserStoryTasksView(application, userStory), "Tasks");
		
		addComponent(tabSheet);
	}
	
	private static final long serialVersionUID = -5750180111347530457L;

}
