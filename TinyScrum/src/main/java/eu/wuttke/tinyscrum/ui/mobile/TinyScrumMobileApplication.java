package eu.wuttke.tinyscrum.ui.mobile;

import java.util.List;

import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.addon.touchkit.ui.TouchKitWindow;

import eu.wuttke.tinyscrum.domain.Project;

public class TinyScrumMobileApplication
extends TouchKitApplication {

	private TouchKitWindow mainWindow;
	//private TinyScrumMobileMainView mainView;
	private MobileDashboardNavigationManager mainView;
	private TinyScrumMobileLoginView loginView;
	
	private List<Project> projects;
	private Project project;
	private String userName;
	
	@Override
	public void init() {
		super.init();
		
		setTheme("tinyscrum");
		
		mainWindow = new TouchKitWindow();
		mainWindow.addApplicationIcon("VAADIN/themes/tinyscrum/img/TinyScrumAppIcon.png");
        //mainWindow.setStartupImage("VAADIN/themes/vornitologist/startup.png");
        mainWindow.setWebAppCapable(true);
        mainWindow.setPersistentSessionCookie(true);
        setMainWindow(mainWindow);
	}
	
	@Override
	public void onBrowserDetailsReady() {
		//mainView = new TinyScrumMobileMainView();
		mainView = new MobileDashboardNavigationManager();
		loginView = new TinyScrumMobileLoginView();
        mainWindow.setContent(loginView);
        
		projects = Project.findAllProjects();
		project = projects.get(0);
	}
	
	public MobileDashboardNavigationManager getMainView() {
		return mainView;
	}
	
	public Project getCurrentProject() {
		return project;
	}
	
	public void setCurrentProject(Project project) {
		this.project = project;
	}
	
	public List<Project> getProjects() {
		return projects;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	private static final long serialVersionUID = 1L;
	
}
