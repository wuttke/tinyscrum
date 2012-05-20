package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;


public class DashboardView 
extends VerticalLayout {

	@SuppressWarnings("unused")
	private TinyScrumApplication application;
	
	public DashboardView(TinyScrumApplication application) {
		this.application = application;
		
		setSizeFull();
		
		Table table = new Table();
		addComponent(table);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addComponent(new Button("User Stories"));
		buttonLayout.addComponent(new Button("Tasks"));
		addComponent(buttonLayout);
		
		setExpandRatio(table, 1f);
	}

	private static final long serialVersionUID = 1L;

}
