package eu.wuttke.tinyscrum.ui.mobile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.OptionGroup;

import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.TaskAndStory;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.TaskManager;
import eu.wuttke.tinyscrum.logic.UserStoryManager;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

@Configurable(autowire=Autowire.BY_NAME)
public class MobileDashboardView 
extends NavigationView 
implements ClickListener, RefreshableComponent {
	
	private VerticalComponentGroup tasksGroup;
	private VerticalComponentGroup storiesGroup;
	
	public MobileDashboardView() {
		setCaption("Dashboard");
	}
	
	@Override
	public void attach() {
		super.attach();
	
		List<Project> projects = ((TinyScrumMobileApplication)getApplication()).getProjects();
		Project project = ((TinyScrumMobileApplication)getApplication()).getCurrentProject();

		tasksGroup = new VerticalComponentGroup();
		tasksGroup.setCaption("My Tasks");

		storiesGroup = new VerticalComponentGroup();
		storiesGroup.setCaption("My Stories");
		
        final OptionGroup projectSelect = new OptionGroup();
        projectSelect.setContainerDataSource(new BeanItemContainer<Project>(Project.class, projects));
        projectSelect.setValue(project);
        projectSelect.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			public void valueChange(ValueChangeEvent event) {
				Project project = (Project)projectSelect.getValue();
				((TinyScrumMobileApplication)getApplication()).setCurrentProject(project);
				refreshContent();
			}
		});
        
		VerticalComponentGroup projectGroup = new VerticalComponentGroup();
		projectGroup.setCaption("Project");
		projectGroup.addComponent(projectSelect);
		
		CssLayout content = new CssLayout();
		content.setWidth("100%");
		content.addComponent(tasksGroup);
		content.addComponent(storiesGroup);
		content.addComponent(projectGroup);
		setContent(content);
			
		refreshContent();
	}
	
	public void refreshContent() {
		Project project = ((TinyScrumMobileApplication)getApplication()).getCurrentProject();
		String userName = ((TinyScrumMobileApplication)getApplication()).getUserName();

		List<TaskAndStory> tasks = taskManager.loadUserTasks(userName, project);
		tasksGroup.removeAllComponents();
		for (TaskAndStory task : tasks) {
			NavigationButton taskButton = new NavigationButton();
			taskButton.setCaption(task.getTaskName());
			taskButton.setDescription(task.getStoryTitle());
			taskButton.addListener(this);
			taskButton.setData(task);
			tasksGroup.addComponent(taskButton);
		}
		
		List<UserStory> stories = userStoryManager.loadUserUserStories(userName, project);
		storiesGroup.removeAllComponents();
		for (UserStory story : stories) {
			NavigationButton storyButton = new NavigationButton();
			storyButton.setCaption(story.getTitle());
			storyButton.setData(story);
			storyButton.addListener(this);
			storiesGroup.addComponent(storyButton);
		}		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// Data -> TaskAndStory or UserStory
		// NavigationManager.NavigateTo
		Object data = event.getButton().getData();
		if (data instanceof TaskAndStory) {
			TaskAndStory ts = (TaskAndStory)data;
			getNavigationManager().navigateTo(new MobileTaskView(ts.getTask(), ts.getStory()));
		} else if (data instanceof UserStory) {
			UserStory us = (UserStory)data;
			getNavigationManager().navigateTo(new MobileStoryView(us));
		}
	}
	
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	public void setUserStoryManager(UserStoryManager userStoryManager) {
		this.userStoryManager = userStoryManager;
	}
	
	private TaskManager taskManager;
	private UserStoryManager userStoryManager;
	
	private static final long serialVersionUID = 1L;

}
