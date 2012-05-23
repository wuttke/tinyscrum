package eu.wuttke.tinyscrum.logic;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.ScrumUser;

/**
 * User management
 * @author Matthias Wuttke
 */
@Component
public class UserManager {

	/**
	 * Lists all users.
	 * @param project
	 * @return
	 */
	public List<ScrumUser> getProjectUsers(Project project) {
		// maybe honor team / iteration?
		return ScrumUser.findAllScrumUsers();
	}
	
	/**
	 * Lists all user names.
	 * @param project
	 * @return
	 */	
	public List<String> getProjectUserNames(Project project) {
		EntityManager em = ScrumUser.entityManager();
		TypedQuery<String> q = em.createQuery("SELECT userName FROM ScrumUser", String.class);
		return q.getResultList();		
	}

	/**
	 * Performs a user authentification.
	 * @param userName user name
	 * @param password password
	 * @return user
	 */
	public ScrumUser loginUser(String userName, String password) {
		EntityManager em = ScrumUser.entityManager();
		TypedQuery<ScrumUser> q = em.createQuery("FROM ScrumUser WHERE userName = ? AND password = ? AND active = TRUE", ScrumUser.class);
		q.setParameter(1, userName);
		q.setParameter(2, password);
		List<ScrumUser> l = q.getResultList();
		if (l == null || l.size() < 1)
			return null;
		else {
			// set last access timestamp?
			return l.get(0);
		}
	}

}
