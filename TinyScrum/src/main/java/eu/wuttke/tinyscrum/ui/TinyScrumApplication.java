package eu.wuttke.tinyscrum.ui;

import java.util.LinkedList;
import java.util.List;

import com.vaadin.Application;
import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.ScrumUser;
import eu.wuttke.tinyscrum.ui.misc.FileDownloadHandler;
import eu.wuttke.tinyscrum.ui.misc.LoginCompletedListener;

public class TinyScrumApplication 
extends Application {
	
	public static final String VERSION = "1.0.1";
	private static final long serialVersionUID = -1486443784466891755L;

	private MainView mainView;
	private ScrumUser currentUser;
	private List<LoginCompletedListener> loginCompletedListeners = new LinkedList<LoginCompletedListener>();
	
	@Override
	public void init() {
		setTheme("tinyscrum");
		
		mainView = new MainView(this);
		Window mainWindow = new Window("TinyScrum");
		mainWindow.getContent().setSizeFull();
		mainWindow.addComponent(mainView);
		setMainWindow(mainWindow);
		
		FileDownloadHandler fdh = new FileDownloadHandler();
		mainWindow.addParameterHandler(fdh);
		mainWindow.addURIHandler(fdh);
		
		doLogin();
	}

	public void doLogin() {
		LoginWindow lw = new LoginWindow(this, new LoginCompletedListener() {
			public void loginCompleted(ScrumUser newUser) {
				setCurrentUser(newUser);
				for (LoginCompletedListener l : loginCompletedListeners)
					l.loginCompleted(newUser);
				
				// vorheriger Refresh blockiert
				getMainView().refreshContent();				
			}
		});
		getMainWindow().addWindow(lw);
	}

	public void doLogout() {
		setCurrentUser(null);
		doLogin();
	}
	
	public void addLoginCompletedListener(LoginCompletedListener l) {
		loginCompletedListeners.add(l);
	}

	public MainView getMainView() {
		return mainView;
	}
	
	public Project getCurrentProject() {
		return getMainView().getCurrentProject();
	}

	public ScrumUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(ScrumUser user) {
		currentUser = user;
	}

}
