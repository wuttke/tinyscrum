package eu.wuttke.tinyscrum.logic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.TaskAndStory;
import eu.wuttke.tinyscrum.domain.TaskStatus;
import eu.wuttke.tinyscrum.domain.UserStory;

/**
 * Task-related tasks.
 * @author Matthias Wuttke
 */
@Component
public class TaskManager {

	/**
	 * Loads the tasks for the given user story.
	 * @param story story
	 * @return tasks
	 */
	public List<Task> loadTasksForStory(UserStory story) {
		EntityManager em = Task.entityManager();
		TypedQuery<Task> q = em.createQuery("FROM Task WHERE story = ? ORDER BY id", Task.class);
		q.setParameter(1, story);
		return q.getResultList();		
	}
	
	/**
	 * Attaches and saves the given task.
	 * @param bean task
	 * @return entity
	 */
	@Transactional
	public Task saveTask(Task bean) {
		EntityManager em = UserStory.entityManager();
		Task task = em.merge(bean);
		em.flush();
		return task;
	}

	/**
	 * Deletes the given user task.
	 * @param task task
	 */
	@Transactional
	public void deleteTask(Task task) {
		EntityManager em = UserStory.entityManager();
		task = em.merge(task);
		em.remove(task);
		em.flush();
	}

	/**
	 * Lädt die einem Nutzer zugeordneten Tasks zusammen mit der User Story.
	 * Lädt für Entwickler offene und für Tester zu testende Tasks.
	 * @param user Nutzer
	 * @return Tasks mit Story
	 */
	public List<TaskAndStory> loadUserTasks(String user, Project project) {
		EntityManager em = Task.entityManager();
		
		TypedQuery<Object[]> q = em.createQuery("SELECT t, u FROM Task t, UserStory u " +
				"WHERE t.story = u AND t.project = :project AND " +
				"(((t.developer1 = :name OR t.developer2 = :name) AND t.status = :openStatus) OR (t.tester = :name AND t.status = :testStatus)) " +
				"ORDER BY u.sequenceNumber, t.id", 
				Object[].class);
		q.setParameter("name", user);
		q.setParameter("openStatus", TaskStatus.TASK_OPEN);
		q.setParameter("testStatus", TaskStatus.TASK_TEST);
		q.setParameter("project", project);
		List<Object[]> l = q.getResultList();
		
		List<TaskAndStory> r = new ArrayList<TaskAndStory>(l.size());
		for (Object[] o : l)
			r.add(new TaskAndStory((Task)o[0], (UserStory)o[1]));
		return r;
	}
	
}
