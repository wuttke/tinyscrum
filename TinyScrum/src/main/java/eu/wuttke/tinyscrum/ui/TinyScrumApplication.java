package eu.wuttke.tinyscrum.ui;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.vaadin.Application;
import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.UserStory;

public class TinyScrumApplication 
extends Application {
	
	private static final long serialVersionUID = -1486443784466891755L;

	private MainView mainView;
	
	@Override
	public void init() {
		mainView = new MainView(this);
		Window mainWindow = new Window("TinyScrum");
		mainWindow.getContent().setSizeFull();
		mainWindow.addComponent(mainView);
		setMainWindow(mainWindow);
	}
	
	public MainView getMainView() {
		return mainView;
	}
	
	public Project getCurrentProject() {
		return getMainView().getCurrentProject();
	}

}
