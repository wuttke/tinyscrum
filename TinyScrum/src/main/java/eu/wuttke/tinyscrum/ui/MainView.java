package eu.wuttke.tinyscrum.ui;

import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

public class MainView 
extends VerticalLayout 
implements Property.ValueChangeListener, SelectedTabChangeListener, RefreshableComponent {
	
	private Select cbProjectChooser;
	private BeanItemContainer<Project> projectContainer;
	private TinyScrumApplication application;
	private TabSheet tabSheet;
	
	public MainView(TinyScrumApplication application) {
		this.application = application;
		
		initializeLayout();
		loadProjects();
	}
	
	private void initializeLayout() {
		setSizeFull();
		setSpacing(true);
		
		tabSheet = new TabSheet();
		tabSheet.setSizeFull();
		tabSheet.addTab(new DashboardView(application), "My Dashboard");
		tabSheet.addTab(new BacklogView(application), "Backlog Management");
		tabSheet.addTab(new IterationsView(application), "Iteration Planning");
		tabSheet.addListener(this);
		tabSheet.setSizeFull();
		
		cbProjectChooser = new Select();
		cbProjectChooser.setItemCaptionPropertyId("name");
		cbProjectChooser.setNullSelectionAllowed(false);
		cbProjectChooser.setImmediate(true);
		cbProjectChooser.addListener(this);
		
		HorizontalLayout headerLayout = new HorizontalLayout();
		Label lblTinyScrum = new Label();
		lblTinyScrum.setIcon(new ThemeResource("img/logo.png"));
		headerLayout.addComponent(lblTinyScrum);
		headerLayout.addComponent(cbProjectChooser);
		headerLayout.setComponentAlignment(lblTinyScrum, Alignment.MIDDLE_LEFT);
		headerLayout.setComponentAlignment(cbProjectChooser, Alignment.MIDDLE_RIGHT);
		headerLayout.setWidth("100%");
		
		Label lblFooter = new Label("TinyScrum Footer");
		
		addComponent(headerLayout);
		addComponent(tabSheet);
		addComponent(lblFooter);
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
