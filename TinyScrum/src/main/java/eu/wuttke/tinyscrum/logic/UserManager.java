package eu.wuttke.tinyscrum.logic;

import java.security.MessageDigest;
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
		//return ScrumUser.findAllScrumUsers();
		TypedQuery<ScrumUser> q = ScrumUser.entityManager().createQuery("FROM ScrumUser WHERE active = TRUE", ScrumUser.class);
		return q.getResultList();
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
		String sha1Password = makeSha1Hash(password);
		EntityManager em = ScrumUser.entityManager();
		TypedQuery<ScrumUser> q = em.createQuery("FROM ScrumUser WHERE userName = ? AND password = ? AND active = TRUE", ScrumUser.class);
		q.setParameter(1, userName);
		q.setParameter(2, sha1Password);
		List<ScrumUser> l = q.getResultList();
		if (l == null || l.size() < 1)
			return null;
		else {
			// set last access timestamp?
			return l.get(0);
		}
	}

	/**
	 * Lookups a user only by username.
	 * @param userName user name
	 * @return user object
	 */
	public ScrumUser getUser(String userName) {
		EntityManager em = ScrumUser.entityManager();
		TypedQuery<ScrumUser> q = em.createQuery("FROM ScrumUser WHERE userName = ?", ScrumUser.class);
		q.setParameter(1, userName);
		List<ScrumUser> l = q.getResultList();
		if (l == null || l.size() < 1)
			return null;
		else
			return l.get(0);
	}

	/**
	 * Creates a SHA1 hash string for the given plaintext.
	 * @param plaintext Plaintext password
	 * @return Hashed password
	 */
	public static String makeSha1Hash(String plaintext) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA1");
			digest.update(plaintext.getBytes("UTF-8"));
			return bytesToHex(digest.digest());
		} catch (Exception e) {
			return plaintext;
		}
	}

	/**
	 * Converts a byte array to a hex string.
	 * @param b byte array
	 * @return hex string
	 */
	public static String bytesToHex(byte[] b) {
		char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < b.length; j++) {
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
		return buf.toString();
	}
	
}
