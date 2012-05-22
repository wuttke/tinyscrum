package eu.wuttke.tinyscrum.ui;

import com.vaadin.Application;
import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.Project;

public class TinyScrumApplication 
extends Application {
	
	private static final long serialVersionUID = -1486443784466891755L;

	private MainView mainView;

	@Override
	public void init() {
		setTheme("tinyscrum");
		
		mainView = new MainView(this);
		Window mainWindow = new Window("TinyScrum");
		mainWindow.getContent().setSizeFull();
		mainWindow.addComponent(mainView);
		setMainWindow(mainWindow);
		
		// vorheriger Refresh blockiert
		getMainView().refreshContent();
	}
	
	public MainView getMainView() {
		return mainView;
	}
	
	public Project getCurrentProject() {
		return getMainView().getCurrentProject();
	}

}
