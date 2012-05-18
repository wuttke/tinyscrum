package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.web.TinyScrumApplication;

public class DashboardView 
extends VerticalLayout {

	@SuppressWarnings("unused")
	private TinyScrumApplication application;
	
	public DashboardView(TinyScrumApplication application) {
		this.application = application;
	}

	private static final long serialVersionUID = 1L;

}
