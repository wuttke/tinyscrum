package eu.wuttke.tinyscrum.ui;

import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Select;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.ui.admin.AdminView;
import eu.wuttke.tinyscrum.ui.dashboard.DashboardView;
import eu.wuttke.tinyscrum.ui.iteration.IterationsView;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;
import eu.wuttke.tinyscrum.ui.userstory.BacklogView;

public class MainView 
extends VerticalLayout 
implements Property.ValueChangeListener, SelectedTabChangeListener, RefreshableComponent {
	
	private Select cbProjectChooser;
	private BeanItemContainer<Project> projectContainer;
	private TinyScrumApplication application;
	private TabSheet tabSheet;
	private boolean blockProjectValueChanged = false;
	

	public MainView(TinyScrumApplication application) {
		this.application = application;

		blockProjectValueChanged = true;
		initializeLayout();
		loadProjects();
		blockProjectValueChanged = false;
	}
	
	private void initializeLayout() {
		setSizeFull();
		setSpacing(true);
		
		tabSheet = new TabSheet();
		tabSheet.setSizeFull();
		tabSheet.addTab(new DashboardView(application), "Dashboard");
		tabSheet.addTab(new BacklogView(application), "Backlog Management");
		tabSheet.addTab(new IterationsView(application), "Iteration Planning");
		tabSheet.addTab(new AdminView(application), "Administration");
		tabSheet.addListener(this);
		tabSheet.setSizeFull();
		
		cbProjectChooser = new Select();
		cbProjectChooser.setItemCaptionPropertyId("name");
		cbProjectChooser.setNullSelectionAllowed(false);
		cbProjectChooser.setImmediate(true);
		cbProjectChooser.addListener(this);
		
		Label lblTinyScrum = new Label();
		lblTinyScrum.setIcon(new ThemeResource("img/logo.png"));
		
		Button btnLogout = new Button("Logout");
		btnLogout.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				application.doLogout();
			}
		});
		
		HorizontalLayout headerLayout = new HorizontalLayout();
		headerLayout.addComponent(lblTinyScrum);
		headerLayout.addComponent(btnLogout);
		headerLayout.addComponent(cbProjectChooser);
		headerLayout.setComponentAlignment(lblTinyScrum, Alignment.MIDDLE_LEFT);
		headerLayout.setComponentAlignment(btnLogout, Alignment.MIDDLE_RIGHT);
		headerLayout.setComponentAlignment(cbProjectChooser, Alignment.MIDDLE_RIGHT);
		headerLayout.setExpandRatio(lblTinyScrum, 1f);
		headerLayout.setSpacing(true);
		headerLayout.setWidth("100%");
		
		Link footerLink = new Link("TinyScrum " + TinyScrumApplication.VERSION + ", http://code.google.com/p/tinyscrum", new ExternalResource("http://code.google.com/p/tinyscrum"));
		footerLink.setTargetName("_blank");
		
		addComponent(headerLayout);
		addComponent(tabSheet);
		addComponent(footerLink);
		setExpandRatio(tabSheet, 1);
	}

	private void loadProjects() {
		projectContainer = new BeanItemContainer<Project>(Project.class);
		List<Project> projects = Project.findAllProjects();
		projectContainer.addAll(projects);
		LogFactory.getLog(TinyScrumApplication.class).info("found projects: " + projects.size());
		cbProjectChooser.setContainerDataSource(projectContainer);
		cbProjectChooser.setValue(projectContainer.firstItemId()); // oder irgendwoher merken?
	}
	
	public Project getCurrentProject() {
		Object projectId = cbProjectChooser.getValue();
		if (projectId == null)
			return null;
		return projectContainer.getItem(projectId).getBean();
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		// project changed
		if (!blockProjectValueChanged)
			refreshContent();
	}
	
	@Override
	public void refreshContent() {
		Component tab = tabSheet.getSelectedTab();
		if (tab instanceof RefreshableComponent) {
			RefreshableComponent rc = (RefreshableComponent)tab;
			rc.refreshContent();
		}
	}

	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		refreshContent();
	}
	
	private static final long serialVersionUID = 1L;
		
}
