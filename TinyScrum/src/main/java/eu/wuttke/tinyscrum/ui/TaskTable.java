package eu.wuttke.tinyscrum.ui;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.UserStoryManager;

@Configurable(autowire=Autowire.BY_NAME)
public class TaskTable 
extends Table {

	private UserStory story;
	private BeanItemContainer<Task> taskContainer;
	
	public TaskTable(UserStory story) {
		this.story = story;

		taskContainer = new BeanItemContainer<Task>(Task.class);
		
		setSizeFull();
		setSelectable(true);
		setContainerDataSource(taskContainer);
		setVisibleColumns(new String[]{ "name", "developer", "tester", "estimate", "status" });
	}
	
	public void loadTasks() {
		List<Task> tasks = userStoryManager.loadTasks(story);
		taskContainer.removeAllItems();
		taskContainer.addAll(tasks);
	}
	
	private static final long serialVersionUID = 1L;

	@Transactional
	public void addTask(Task t) {
		assert t.getStory() == story;
		taskContainer.addBean(t);
		
		EntityManager em = Task.entityManager();
		em.merge(t);
	}
	
	public void setUserStoryManager(UserStoryManager userStoryManager) {
		this.userStoryManager = userStoryManager;
	}

	private UserStoryManager userStoryManager;
	
}
