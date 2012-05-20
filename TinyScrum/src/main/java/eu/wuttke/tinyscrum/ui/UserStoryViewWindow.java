package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;

import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

public class UserStoryViewWindow
extends Window 
implements RefreshableComponent, SelectedTabChangeListener {

	private UserStoryDetailsView detailsView;
	private UserStoryTasksView tasksView;
	private TabSheet tabSheet;
	
	public UserStoryViewWindow(TinyScrumApplication application, UserStory userStory) {
		setModal(true);
        setWidth("80%");
        setHeight("80%");
        setCaption("User Story: " + userStory.getTitle());
        getContent().setSizeFull();
        center();
		
        detailsView = new UserStoryDetailsView(application, userStory);
        tasksView = new UserStoryTasksView(application, userStory);
        
		tabSheet = new TabSheet();
		tabSheet.setSizeFull();
		tabSheet.addListener((SelectedTabChangeListener)this);
		tabSheet.addTab(detailsView, "Overview");
		tabSheet.addTab(tasksView, "Tasks");
		
		addComponent(tabSheet);
		
		//refreshContent();
	}
	
	@Override
	public void refreshContent() {
		Component tab = tabSheet.getSelectedTab();
		if (tab instanceof RefreshableComponent) {
			RefreshableComponent rc = (RefreshableComponent)tab;
			rc.refreshContent();
		}
	}

	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		refreshContent();
	}
	
	private static final long serialVersionUID = -5750180111347530457L;

}
