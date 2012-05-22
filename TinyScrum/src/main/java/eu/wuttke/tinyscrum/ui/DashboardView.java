package eu.wuttke.tinyscrum.ui;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

/**
 * Dashboard view with open user tasks and stories.
 * @author Matthias Wuttke
 */
@Configurable(autowire=Autowire.BY_NAME)
public class DashboardView 
extends VerticalLayout 
implements RefreshableComponent {

	private TinyScrumApplication application;
	private DashboardTaskStoryTable dashboardTaskStoryTable;
	private DashboardStoryTable dashboardStoryTable;
	
	public DashboardView(TinyScrumApplication application) {
		this.application = application;
		
		setSizeFull();
		setMargin(true);
		setSpacing(true);
		
		dashboardTaskStoryTable = new DashboardTaskStoryTable(this.application);
		dashboardStoryTable = new DashboardStoryTable(this.application);

		addComponent(dashboardTaskStoryTable);
		addComponent(dashboardStoryTable);
		setExpandRatio(dashboardTaskStoryTable, 2f);
		setExpandRatio(dashboardStoryTable, 1f);
	}
	
	@Override
	public void refreshContent() {
		String user = "user1"; // TODO
		
		dashboardTaskStoryTable.loadDashboardTasks(user);
		dashboardStoryTable.loadDashboardStories(user);
	}

	private static final long serialVersionUID = 1L;

}
