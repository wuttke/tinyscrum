package eu.wuttke.tinyscrum.ui.mobile;

import com.vaadin.addon.touchkit.ui.TabBarView;
import com.vaadin.terminal.ThemeResource;

public class TinyScrumMobileMainView extends TabBarView {

	private MobileDashboardNavigationManager dashboardNavigationManager;
	private MobileBacklogView backlogView;
	private MobileIterationsView iterationsView;
	
	public TinyScrumMobileMainView() {
		dashboardNavigationManager = new MobileDashboardNavigationManager();
		backlogView = new MobileBacklogView();
		iterationsView = new MobileIterationsView();

		addTab(dashboardNavigationManager, "Dashboard", new ThemeResource("dashboard.png"));
		addTab(backlogView, "Backlog", new ThemeResource("backlog.png"));
		addTab(iterationsView, "Iterations", new ThemeResource("iterations.png"));
		setSelectedTab(dashboardNavigationManager);
	}
	
	private static final long serialVersionUID = 1L;
	
}
