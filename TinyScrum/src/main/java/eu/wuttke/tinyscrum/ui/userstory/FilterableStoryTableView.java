package eu.wuttke.tinyscrum.ui.userstory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.ProjectFeature;
import eu.wuttke.tinyscrum.domain.ProjectRelease;
import eu.wuttke.tinyscrum.domain.UserStoryFilter;
import eu.wuttke.tinyscrum.domain.UserStoryStatus;
import eu.wuttke.tinyscrum.logic.ProjectManager;
import eu.wuttke.tinyscrum.logic.UserManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;

@Configurable(autowire=Autowire.BY_NAME)
public class FilterableStoryTableView 
extends CustomComponent 
implements ValueChangeListener {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private FilterableStoryTable filterableStoryTable;
	@AutoGenerated
	private HorizontalLayout horizontalLayout;
	@AutoGenerated
	private Select comboBoxFeature;
	@AutoGenerated
	private Select comboBoxRelease;
	@AutoGenerated
	private Select comboBoxIteration;
	@AutoGenerated
	private Select comboBoxStatus;
	@AutoGenerated
	private Select comboBoxUser;
	@AutoGenerated
	private TextField textFieldTitle;
	
	private ProjectFeature FEATURE_NOT_ASSIGNED = new ProjectFeature("NOT ASSIGNED");
	private Iteration ITERATION_BACKLOG = new Iteration("BACKLOG");
	private ProjectRelease RELEASE_NOT_ASSIGNED = new ProjectRelease("NOT ASSIGNED");
	private String USER_NOT_ASSIGNED = "NOT ASSIGNED";
	
	public FilterableStoryTableView(TinyScrumApplication application) {
		this.application = application;
		buildMainLayout();
		setCompositionRoot(mainLayout);
		filterableStoryTable.application = application;
		
		textFieldTitle.setImmediate(true);
		textFieldTitle.addListener(this);
	}

	public void refreshContent() {
		initCombo();
		refreshRows();
	}
	
	private void refreshRows() {
		UserStoryFilter filter = readFilter();
		filterableStoryTable.loadStories(filter);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		refreshRows();
	}

	private UserStoryFilter readFilter() {
		UserStoryFilter filter = new UserStoryFilter();
		filter.setProject(application.getCurrentProject());
		
		filter.setTitleContains((String)textFieldTitle.getValue());
		if (StringUtils.isEmpty(filter.getTitleContains()))
			filter.setTitleContains(null);
		
		ProjectFeature f = (ProjectFeature)comboBoxFeature.getValue();
		if (f != null) {
			filter.setFilterFeature(true);
			if (f != FEATURE_NOT_ASSIGNED)
				filter.setFeatureEquals(f);
		}
		
		ProjectRelease r = (ProjectRelease)comboBoxRelease.getValue();
		if (r != null) {
			filter.setFilterRelease(true);
			if (r != RELEASE_NOT_ASSIGNED)
				filter.setReleaseEquals(r);
		}

		Iteration i = (Iteration)comboBoxIteration.getValue();
		if (i != null) {
			filter.setFilterIteration(true);
			if (i != ITERATION_BACKLOG)
				filter.setIterationEquals(i);
		}

		String u = (String)comboBoxUser.getValue();
		if (u != null) {
			filter.setFilterOwner(true);
			if (u != USER_NOT_ASSIGNED)
				filter.setOwnerEquals(u);
		}
		
		UserStoryStatus s = (UserStoryStatus)comboBoxStatus.getValue();
		if (s != null)
			filter.setStatusEquals(s);

		return filter;
	}

	private void initCombo() {
		Project p = application.getCurrentProject();
		
		for (UserStoryStatus status : UserStoryStatus.values())
			comboBoxStatus.addItem(status);
		comboBoxStatus.setImmediate(true);
		comboBoxStatus.addListener(this);
		
		comboBoxUser.addItem(USER_NOT_ASSIGNED);
		for (String user : userManager.getProjectUserNames(p))
			comboBoxUser.addItem(user);
		comboBoxUser.setImmediate(true);
		comboBoxUser.addListener(this);
		
		comboBoxFeature.addItem(FEATURE_NOT_ASSIGNED);
		for (ProjectFeature feature : projectManager.loadFeatures(p))
			comboBoxFeature.addItem(feature);
		comboBoxFeature.setImmediate(true);
		comboBoxFeature.addListener(this);
		
		comboBoxRelease.addItem(RELEASE_NOT_ASSIGNED);
		for (ProjectRelease release : projectManager.loadReleases(p))
			comboBoxRelease.addItem(release);
		comboBoxRelease.setImmediate(true);
		comboBoxRelease.addListener(this);
		
		comboBoxIteration.addItem(ITERATION_BACKLOG);
		for (Iteration iteration : projectManager.loadIterations(p))
			comboBoxIteration.addItem(iteration);
		comboBoxIteration.setImmediate(true);
		comboBoxIteration.addListener(this);		
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	private ProjectManager projectManager;
	private UserManager userManager;
	private TinyScrumApplication application;

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// horizontalLayout
		horizontalLayout = buildHorizontalLayout();
		mainLayout.addComponent(horizontalLayout);
		
		// filterableStoryTable
		filterableStoryTable = new FilterableStoryTable();
		filterableStoryTable.setImmediate(false);
		filterableStoryTable.setWidth("100.0%");
		filterableStoryTable.setHeight("100.0%");
		mainLayout.addComponent(filterableStoryTable);
		mainLayout.setExpandRatio(filterableStoryTable, 1.0f);
		
		return mainLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildHorizontalLayout() {
		// common part: create layout
		horizontalLayout = new HorizontalLayout();
		horizontalLayout.setImmediate(false);
		horizontalLayout.setWidth("-1px");
		horizontalLayout.setHeight("-1px");
		horizontalLayout.setMargin(false);
		horizontalLayout.setSpacing(true);
		
		// textFieldTitle
		textFieldTitle = new TextField();
		textFieldTitle.setCaption("Title contains");
		textFieldTitle.setImmediate(false);
		textFieldTitle.setWidth("-1px");
		textFieldTitle.setHeight("-1px");
		horizontalLayout.addComponent(textFieldTitle);
		
		// comboBoxUser
		comboBoxUser = new Select();
		comboBoxUser.setCaption("Owner equals");
		comboBoxUser.setImmediate(false);
		comboBoxUser.setWidth("-1px");
		comboBoxUser.setHeight("-1px");
		horizontalLayout.addComponent(comboBoxUser);
		
		// comboBoxIteration
		comboBoxIteration = new Select();
		comboBoxIteration.setCaption("Iteration");
		comboBoxIteration.setImmediate(false);
		comboBoxIteration.setWidth("-1px");
		comboBoxIteration.setHeight("-1px");
		horizontalLayout.addComponent(comboBoxIteration);
		
		// comboBoxFeature
		comboBoxFeature = new Select();
		comboBoxFeature.setCaption("Feature");
		comboBoxFeature.setImmediate(false);
		comboBoxFeature.setWidth("-1px");
		comboBoxFeature.setHeight("-1px");
		horizontalLayout.addComponent(comboBoxFeature);
		
		// comboBoxRelease
		comboBoxRelease = new Select();
		comboBoxRelease.setCaption("Release");
		comboBoxRelease.setImmediate(false);
		comboBoxRelease.setWidth("-1px");
		comboBoxRelease.setHeight("-1px");
		horizontalLayout.addComponent(comboBoxRelease);
		
		// comboBoxStatus
		comboBoxStatus = new Select();
		comboBoxStatus.setCaption("Status equals");
		comboBoxStatus.setImmediate(false);
		comboBoxStatus.setWidth("-1px");
		comboBoxStatus.setHeight("-1px");
		horizontalLayout.addComponent(comboBoxStatus);
		
		return horizontalLayout;
	}
	
	private static final long serialVersionUID = 1L;

}