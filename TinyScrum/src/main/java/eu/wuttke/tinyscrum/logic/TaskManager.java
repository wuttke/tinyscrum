package eu.wuttke.tinyscrum.logic;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import eu.wuttke.tinyscrum.domain.Task;
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

	
}
