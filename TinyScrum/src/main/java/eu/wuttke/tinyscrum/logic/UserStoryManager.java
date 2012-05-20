package eu.wuttke.tinyscrum.logic;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.UserStory;

/**
 * User story business logic
 * @author Matthias Wuttke
 */
@Component
public class UserStoryManager {
	
	/**
	 * Load user stories in the backlog
	 * @param p project
	 * @return stories
	 */
	public List<UserStory> loadBacklogUserStories(Project p) {
		EntityManager em = UserStory.entityManager();
		TypedQuery<UserStory> q = em.createQuery("FROM UserStory WHERE iteration IS NULL AND project = ? ORDER BY sequenceNumber", UserStory.class);
		q.setParameter(1, p);
		return q.getResultList();
	}
	
	/**
	 * Load user stories in the given iteration.
	 * @param i iteration
	 * @return stories
	 */
	public List<UserStory> loadIterationUserStories(Iteration i) {
		EntityManager em = UserStory.entityManager();
		TypedQuery<UserStory> q = em.createQuery("FROM UserStory WHERE iteration = ? AND project = ? ORDER BY sequenceNumber", UserStory.class);
		Project p = i.getProject();
		q.setParameter(1, i);
		q.setParameter(2, p);
		return q.getResultList();		
	}

	/**
	 * Attaches and saves the given story.
	 * Gives a new sequence number.
	 * @param bean story
	 * @return entity
	 */
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

	/**
	 * Deletes the given user story.
	 * Renumbers the sequences.
	 * @param userStory user story
	 */
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

}
