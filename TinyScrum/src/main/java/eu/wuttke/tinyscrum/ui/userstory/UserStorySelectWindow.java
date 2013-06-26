package eu.wuttke.tinyscrum.ui.userstory;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.TaskManager;
import eu.wuttke.tinyscrum.logic.UserStoryManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;

@RooJavaBean
@Configurable(autowire=Autowire.BY_NAME)
public class UserStorySelectWindow 
extends Window {

	private TinyScrumApplication scrumApplication;
	private Set<Task> selectedTasks;
	private ObjectSavedListener objectSavedListener;
	private MoveTasksParamsView moveTasksParamsView;
	
	public UserStorySelectWindow(TinyScrumApplication application,
			Set<Task> selectedTasks, ObjectSavedListener objectSavedListener) {
		this.scrumApplication = application;
		this.selectedTasks = selectedTasks;
		this.objectSavedListener = objectSavedListener;
		
		setModal(true);
        setWidth("80%");
        setHeight("80%");
        
    	setCaption("Move Tasks");
        //getContent().setSizeFull();
        center();
		
        moveTasksParamsView = new MoveTasksParamsView();
        
        moveTasksParamsView.addOkListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				moveTasks();
				close();
			}
			private static final long serialVersionUID = 1L;
		});

        moveTasksParamsView.addCancelListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
			private static final long serialVersionUID = 1L;
		});
        
        getContent().addComponent(moveTasksParamsView);
	}

	protected void moveTasks() {
		UserStory story;
		if (moveTasksParamsView.isCreateNewUserStory()) {
			story = new UserStory();
			story.setTitle(moveTasksParamsView.getNewStoryTitle());
			story.setProject(scrumApplication.getCurrentProject());
			story = userStoryManager.saveUserStory(story);
			logger.info("created new story ID {}", story.getId());
		} else {
			story = moveTasksParamsView.getSelectedStory();
		}
		
		taskManager.moveTasksToStory(selectedTasks, story);
		
		if (objectSavedListener != null)
			objectSavedListener.objectSaved(null);
	}

	private UserStoryManager userStoryManager;
	private TaskManager taskManager;

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(UserStorySelectWindow.class);

}
