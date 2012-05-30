package eu.wuttke.tinyscrum.ui.mobile;

import com.vaadin.addon.touchkit.ui.NavigationManager;

public class MobileDashboardNavigationManager 
extends NavigationManager {

	private static final long serialVersionUID = 1L;

	public MobileDashboardNavigationManager() {
		navigateTo(new MobileDashboardView());
	}
	
}
