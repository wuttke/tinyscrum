package eu.wuttke.tinyscrum.logic;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.ProjectFeature;
import eu.wuttke.tinyscrum.domain.ProjectRelease;
import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.UserStory;

/**
 * User story business logic
 * @author Matthias Wuttke
 */
@Component
public class UserStoryManager {
	
	public UserStoryManager() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<UserStory> loadBacklogUserStories(Project p) {
		EntityManager em = UserStory.entityManager();
		Query q = em.createQuery("FROM UserStory WHERE iteration IS NULL AND project = ? ORDER BY sequenceNumber", UserStory.class);
		q.setParameter(1, p);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserStory> loadIterationUserStories(Iteration i) {
		EntityManager em = UserStory.entityManager();
		Query q = em.createQuery("FROM UserStory WHERE iteration = ? AND project = ? ORDER BY sequenceNumber", UserStory.class);
		Project p = i.getProject();
		q.setParameter(1, i);
		q.setParameter(2, p);
		return q.getResultList();		
	}

	@SuppressWarnings("unchecked")
	public List<Iteration> loadIterations(Project p) {
		EntityManager em = UserStory.entityManager();
		Query q = em.createQuery("FROM Iteration WHERE project = ? ORDER BY startDate", Iteration.class);
		q.setParameter(1, p);
		return q.getResultList();		
	}

	@SuppressWarnings("unchecked")
	public List<Task> loadTasks(UserStory story) {
		EntityManager em = Task.entityManager();
		Query q = em.createQuery("FROM Task WHERE story = ? ORDER BY sequenceNumber", Task.class);
		q.setParameter(1, story);
		return q.getResultList();		
	}

	@SuppressWarnings("unchecked")
	public List<ProjectFeature> loadFeatures(Project project) {
		EntityManager em = ProjectFeature.entityManager();
		Query q = em.createQuery("FROM ProjectFeature WHERE project = ? ORDER BY name", ProjectFeature.class);
		q.setParameter(1, project);
		return q.getResultList();		
	}

	@SuppressWarnings("unchecked")
	public List<ProjectRelease> loadReleases(Project project) {
		EntityManager em = ProjectRelease.entityManager();
		Query q = em.createQuery("FROM ProjectRelease WHERE project = ? ORDER BY name", ProjectRelease.class);
		q.setParameter(1, project);
		return q.getResultList();		
	}

	@Transactional
	public void deleteUserStory(UserStory userStory) {
		EntityManager em = UserStory.entityManager();
		em.createQuery("DELETE FROM Task WHERE story = ?").setParameter(1, userStory).executeUpdate();
		
		userStory = em.merge(userStory);
		em.remove(userStory);
		
		Query q1 = em.createQuery("UPDATE UserStory SET sequenceNumber = sequenceNumber - 1 WHERE sequenceNumber > ?");
		q1.setParameter(1, userStory.getSequenceNumber());
		q1.executeUpdate();

		em.flush();
	}

	@Transactional
	public UserStory saveUserStory(UserStory bean) {
		EntityManager em = UserStory.entityManager();

		if (bean.getSequenceNumber() == 0) {
			Query q = em.createQuery("SELECT MAX(sequenceNumber) FROM UserStory");
			Integer seqNo = (Integer)q.getSingleResult();
			if (seqNo == null) seqNo = 0; // first user story ever!
			bean.setSequenceNumber(seqNo + 1);
		}
		
		UserStory story = em.merge(bean);
		em.flush();
		return story;
	}

}
