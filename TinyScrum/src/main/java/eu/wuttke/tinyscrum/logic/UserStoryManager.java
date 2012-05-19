package eu.wuttke.tinyscrum.logic;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
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

}
