package eu.wuttke.tinyscrum.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.ScrumUser;
import eu.wuttke.tinyscrum.logic.UserManager;
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
	private ComboBox cbDashboardUser;
	
	public DashboardView(TinyScrumApplication application) {
		this.application = application;
		
		setSizeFull();
		setMargin(true);
		setSpacing(true);
		
		cbDashboardUser = new ComboBox();
		cbDashboardUser.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			public void valueChange(ValueChangeEvent event) {
				if (!blockValueChanged)
					refreshContent();
			}
		});
		cbDashboardUser.setNullSelectionAllowed(false);
		cbDashboardUser.setTextInputAllowed(false);
		
		Form separator1 = new Form();
		separator1.setCaption("Tasks");
		separator1.setWidth("100%");
		
		dashboardTaskStoryTable = new DashboardTaskStoryTable(this.application);
		
		Form separator2 = new Form();
		separator2.setCaption("User Stories");
		separator2.setWidth("100%");

		dashboardStoryTable = new DashboardStoryTable(this.application);

		addComponent(cbDashboardUser);
		addComponent(separator1);
		addComponent(dashboardTaskStoryTable);
		addComponent(separator2);
		addComponent(dashboardStoryTable);
		setExpandRatio(dashboardTaskStoryTable, 2f);
		setExpandRatio(dashboardStoryTable, 1f);
		setComponentAlignment(cbDashboardUser, Alignment.TOP_RIGHT);
	}
	
	private boolean blockValueChanged = false;
	
	@Override
	public void refreshContent() {
		if (cbDashboardUser.getContainerDataSource() == null)
			initUserComboBox();
		
		ScrumUser user = application.getCurrentUser();
		String userName = "user1"; // TODO
		if (user != null)
			userName = user.getUserName();
		
		dashboardTaskStoryTable.loadDashboardTasks(userName);
		dashboardStoryTable.loadDashboardStories(userName);
	}

	private void initUserComboBox() {
		blockValueChanged = true;
		List<ScrumUser> users = userManager.getProjectUsers(application.getCurrentProject());
		BeanItemContainer<ScrumUser> userContainer = new BeanItemContainer<ScrumUser>(ScrumUser.class);
		userContainer.addAll(users);
		cbDashboardUser.setContainerDataSource(userContainer);
		cbDashboardUser.setValue(application.getCurrentUser());
		blockValueChanged = false;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	private UserManager userManager;
	
	private static final long serialVersionUID = 1L;

}
