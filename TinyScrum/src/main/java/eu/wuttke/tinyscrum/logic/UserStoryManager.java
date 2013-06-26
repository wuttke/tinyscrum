package eu.wuttke.tinyscrum.logic;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.domain.UserStoryFilter;
import eu.wuttke.tinyscrum.domain.UserStoryStatus;

/**
 * User story business logic
 * @author Matthias Wuttke
 */
@Component
@Configurable(autowire=Autowire.BY_NAME)
@RooJavaBean
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
		boolean newStory = bean.getId() == null;

		EntityManager em = UserStory.entityManager();

		if (bean.getSequenceNumber() == 0) {
			Query q = em.createQuery("SELECT MAX(sequenceNumber) FROM UserStory");
			Integer seqNo = (Integer)q.getSingleResult();
			if (seqNo == null) seqNo = 0; // first user story ever!
			bean.setSequenceNumber(seqNo + 1);
		}
		
		UserStory story = em.merge(bean);
		em.flush();
		
		mailManager.sendStoryMail(bean, newStory ? "Created" : "Changed");

		return story;
	}

	/**
	 * Deletes the given user story.
	 * Renumbers the sequences.
	 * @param userStory user story
	 */
	@Transactional
	public void deleteUserStory(UserStory userStory) {
		mailManager.sendStoryMail(userStory, "Deleted");
		
		EntityManager em = UserStory.entityManager();
		em.createQuery("DELETE FROM Task WHERE story = ?").setParameter(1, userStory).executeUpdate();
		
		userStory = em.merge(userStory);
		em.remove(userStory);
		
		Query q1 = em.createQuery("UPDATE UserStory SET sequenceNumber = sequenceNumber - 1 WHERE sequenceNumber > ?");
		q1.setParameter(1, userStory.getSequenceNumber());
		q1.executeUpdate();
	
		em.flush();
	}
	
	/**
	 * Retrieves user stories assigned to the given user whose status is not 'done'.
	 * @param user
	 * @return
	 */
	public List<UserStory> loadUserUserStories(String user, Project project) {
		EntityManager em = UserStory.entityManager();
		TypedQuery<UserStory> q = em.createQuery("FROM UserStory WHERE owner = ? AND project = ? AND status != ? ORDER BY sequenceNumber", UserStory.class);
		q.setParameter(1, user);
		q.setParameter(2, project);
		q.setParameter(3, UserStoryStatus.STORY_DONE);
		return q.getResultList();
	}

	private MailManager mailManager;

	public List<UserStory> loadUserStories(UserStoryFilter filter) {
		EntityManager em = UserStory.entityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<UserStory> q = cb.createQuery(UserStory.class);
		Root<UserStory> r = q.from(UserStory.class);
		
		List<Predicate> restrictions = new LinkedList<Predicate>();
		restrictions.add(cb.equal(r.get("project"), filter.getProject()));
		
		if (filter.getStatusEquals() != null)
			restrictions.add(cb.equal(r.get("status"), filter.getStatusEquals()));
		
		if (filter.isFilterIteration()) {
			if (filter.getIterationEquals() == null)
				restrictions.add(cb.isNull(r.get("iteration")));
			else
				restrictions.add(cb.equal(r.get("iteration"), filter.getIterationEquals()));
		}
		
		if (filter.isFilterFeature()) {
			if (filter.getFeatureEquals() == null)
				restrictions.add(cb.isNull(r.get("projectFeature")));
			else
				restrictions.add(cb.equal(r.get("projectFeature"), filter.getFeatureEquals()));
		}
		
		if (filter.isFilterRelease()) {
			if (filter.getReleaseEquals() == null)
				restrictions.add(cb.isNull(r.get("projectRelease")));
			else
				restrictions.add(cb.equal(r.get("projectRelease"), filter.getReleaseEquals()));
		}
		
		if (filter.getTitleContains() != null)
			restrictions.add(cb.like(r.<String>get("title"), "%" + filter.getTitleContains() + "%"));
		
		if (filter.isFilterOwner()) {
			if (filter.getOwnerEquals() == null)
				restrictions.add(cb.isNull(r.get("owner")));
			else
				restrictions.add(cb.equal(r.get("owner"), filter.getOwnerEquals()));
		}
		
		q.select(r).where(cb.and(restrictions.toArray(new Predicate[restrictions.size()])));
		return em.createQuery(q).getResultList();
	}
	
}
