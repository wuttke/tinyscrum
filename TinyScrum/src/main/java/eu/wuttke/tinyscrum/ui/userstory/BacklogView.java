package eu.wuttke.tinyscrum.ui.userstory;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Customer;
import eu.wuttke.tinyscrum.domain.CustomerProject;
import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.ProjectFeature;
import eu.wuttke.tinyscrum.domain.ProjectRelease;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.domain.UserStoryStatus;
import eu.wuttke.tinyscrum.logic.ProjectManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

@Configurable(autowire=Autowire.BY_NAME)
public class BacklogView 
extends VerticalLayout 
implements ClickListener, ValueChangeListener, RefreshableComponent {
	
	private Button btnAddUserStory;
	private Button btnEditUserStory;
	private Button btnDeleteUserStory;
	
	private BacklogStoryTable backlogTable;
	
	private ComboBox comboBoxProject;
	private ComboBox comboBoxCustomer;
	
	private TinyScrumApplication application;
	
	private Customer CUSTOMER_NOT_ASSIGNED = new Customer("NOT ASSIGNED");
	private CustomerProject PROJECT_NOT_ASSIGNED = new CustomerProject();
	
	
	public BacklogView(TinyScrumApplication application) {
		this.application = application;
		
		setMargin(true);
		setSpacing(true);
		setSizeFull();
		
		// Comboboxes
		comboBoxCustomer = new ComboBox();
		comboBoxProject = new ComboBox();
		
		// Filter Header
		HorizontalLayout filterBar = new HorizontalLayout();
		filterBar.setSpacing(true);
		filterBar.addComponent(comboBoxCustomer);
		filterBar.addComponent(comboBoxProject);
		addComponent(filterBar);
		
		// Backlog Table
		backlogTable = new BacklogStoryTable(application);
		backlogTable.setSizeFull();
		backlogTable.addListener(this);
		backlogTable.setImmediate(true);
		addComponent(backlogTable);
		
		// Buttons
		btnAddUserStory = new Button("Add User Story", this);
		btnEditUserStory = new Button("Edit User Story", this);
		btnEditUserStory.setEnabled(false);
		btnDeleteUserStory = new Button("Delete User Story", this);
		btnDeleteUserStory.setEnabled(false);
		
		// Button Bar
		HorizontalLayout buttonBar = new HorizontalLayout();
		buttonBar.addComponent(btnAddUserStory);
		buttonBar.addComponent(btnEditUserStory);
		buttonBar.addComponent(btnDeleteUserStory);
		buttonBar.setSpacing(true);
		addComponent(buttonBar);
		
		setExpandRatio(backlogTable, 1f);
	}
	
	private void initComboBoxes() {
		if (comboBoxCustomer.getItemIds().size() == 0) {
			ValueChangeListener refreshContentListener = new ValueChangeListener() {
				@Override
				public void valueChange(ValueChangeEvent event) {
					refreshContent();
				}
				private static final long serialVersionUID = 1L;
			};
			
			Project p = application.getCurrentProject();
			
			comboBoxCustomer.addItem(CUSTOMER_NOT_ASSIGNED);
			for (Customer customer : projectManager.loadCustomers(p))
				comboBoxCustomer.addItem(customer);
			comboBoxCustomer.setImmediate(true);
			comboBoxCustomer.addListener(refreshContentListener);
			
			PROJECT_NOT_ASSIGNED.setName("NOT ASSIGNED");
			
			comboBoxProject.addItem(PROJECT_NOT_ASSIGNED);
			for (CustomerProject customerProject : projectManager.loadCustomerProjects(p, null))
				comboBoxProject.addItem(customerProject);
			comboBoxProject.setImmediate(true);
			comboBoxProject.addListener(refreshContentListener);
		}
	}

	@Override
	public void refreshContent() {
		initComboBoxes();
		
		CustomerProject customerProjectFilter = (CustomerProject)comboBoxProject.getValue();
		Customer customerFilter = (Customer)comboBoxCustomer.getValue();
		
		backlogTable.loadBacklog(customerFilter, customerProjectFilter);
	}
	
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == btnAddUserStory)
			userStoryActions.addUserStory(application);
		else if (event.getButton() == btnEditUserStory)
			userStoryActions.editUserStory(application, (UserStory)backlogTable.getValue());
		else if (event.getButton() == btnDeleteUserStory)
			userStoryActions.deleteUserStory(application, (UserStory)backlogTable.getValue());
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		boolean enable = backlogTable.getValue() != null;
		btnEditUserStory.setEnabled(enable);
		btnDeleteUserStory.setEnabled(enable);
	}

	public void setUserStoryActions(UserStoryActions userStoryActions) {
		this.userStoryActions = userStoryActions;
	}
	
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}
	
	private UserStoryActions userStoryActions;
	private ProjectManager projectManager;
	
	private static final long serialVersionUID = 6977286043653094687L;

}
