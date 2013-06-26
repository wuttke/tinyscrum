package eu.wuttke.tinyscrum.ui.dashboard;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

import eu.wuttke.tinyscrum.domain.ScrumUser;
import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.UserManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.LoginCompletedListener;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;
import eu.wuttke.tinyscrum.ui.task.TaskDetailsWindow;
import eu.wuttke.tinyscrum.ui.userstory.UserStoryViewWindow;

/**
 * Dashboard view with open user tasks and stories.
 * @author Matthias Wuttke
 */
@Configurable(autowire=Autowire.BY_NAME)
public class DashboardView 
extends VerticalLayout 
implements RefreshableComponent {

	private TextField tfJump;
	private Label lblWelcome;
	private TinyScrumApplication application;
	private DashboardTaskStoryTable dashboardTaskStoryTable;
	private DashboardStoryTable dashboardStoryTable;
	private ComboBox cbDashboardUser;
	private UserManager userManager;
		
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
		cbDashboardUser.setImmediate(true);
		
		tfJump = new TextField();
		tfJump.setInputPrompt("Task/Story ID");
		tfJump.setImmediate(true);

        tfJump.addShortcutListener(new ShortcutListener("Jump", KeyCode.ENTER, null) {
			private static final long serialVersionUID = 1L;
			@Override
			public void handleAction(Object sender, Object target) {
				doJump((String)tfJump.getValue());
			}
		});
        
		Form separator1 = new Form();
		separator1.setCaption("Tasks");
		separator1.setWidth("100%");
		
		dashboardTaskStoryTable = new DashboardTaskStoryTable(this.application);
		
		Form separator2 = new Form();
		separator2.setCaption("User Stories");
		separator2.setWidth("100%");

		dashboardStoryTable = new DashboardStoryTable(this.application);

		lblWelcome = new Label("Welcome to TinyScrum!");
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(lblWelcome);
		hl.addComponent(tfJump);
		//hl.addComponent(btnJump);
		hl.addComponent(cbDashboardUser);
		hl.setSpacing(true);
		hl.setExpandRatio(lblWelcome, 1f);
		hl.setComponentAlignment(cbDashboardUser, Alignment.TOP_RIGHT);
		hl.setWidth("100%");
		
		addComponent(hl);
		addComponent(separator1);
		addComponent(dashboardTaskStoryTable);
		addComponent(separator2);
		addComponent(dashboardStoryTable);
		setExpandRatio(dashboardTaskStoryTable, 2f);
		setExpandRatio(dashboardStoryTable, 1f);
		
		application.addLoginCompletedListener(new LoginCompletedListener() {
			public void loginCompleted(ScrumUser newUser) {
				blockValueChanged = true;
				cbDashboardUser.setValue(newUser);
				lblWelcome.setValue("Welcome, " + newUser.getFullName() + "!");
				blockValueChanged = false;
			}
		});
	}
	
	protected void doJump(String val) {
		if (StringUtils.isEmpty(val))
			return;
		boolean isStory = val.startsWith("S") || val.startsWith("U") || val.startsWith("s") || val.startsWith("u");
		if (!Character.isDigit(val.charAt(0)))
			val = val.substring(1);
		val = val.trim();
		try {
			int id = Integer.parseInt(val);
			if (isStory)
				jumpStory(id);
			else
				jumpTask(id);
		} catch (NumberFormatException nfe) {
			Notification n = new Notification("Not a valid number", "Please enter T### or S### or ###.", Notification.TYPE_ERROR_MESSAGE);
			application.getMainWindow().showNotification(n);
		}
	}

	public void jumpTask(int id) {
		Task t = Task.findTask(new Long(id));
		if (t != null) {
			TaskDetailsWindow tdw = new TaskDetailsWindow(application, t);
			application.getMainWindow().addWindow(tdw);
		} else
			application.getMainWindow().showNotification("Invalid ID", "Task ID not found", Notification.TYPE_ERROR_MESSAGE);
	}

	public void jumpStory(int id) {
		UserStory t = UserStory.findUserStory(new Long(id));
		if (t != null) {
			UserStoryViewWindow tdw = new UserStoryViewWindow(application, t);
			application.getMainWindow().addWindow(tdw);
		} else
			application.getMainWindow().showNotification("Invalid ID", "Story ID not found", Notification.TYPE_ERROR_MESSAGE);
	}

	private boolean blockValueChanged = false;
	
	@Override
	public void refreshContent() {
		if (cbDashboardUser.getContainerDataSource().size() == 0)
			initUserComboBox();
		
		ScrumUser user = (ScrumUser)cbDashboardUser.getValue();
		String userName = user != null ? user.getUserName() : "nobody";
		
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

	private static final long serialVersionUID = 1L;

}
