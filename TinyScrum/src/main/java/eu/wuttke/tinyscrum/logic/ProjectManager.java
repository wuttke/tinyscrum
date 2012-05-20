package eu.wuttke.tinyscrum.logic;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.ProjectFeature;
import eu.wuttke.tinyscrum.domain.ProjectRelease;
import eu.wuttke.tinyscrum.domain.UserStory;

/**
 * Project-wide operations.
 * @author Matthias Wuttke
 */
@Component
public class ProjectManager {

	/**
	 * Loads iterations for the given project.
	 * @param p project
	 * @return iterations
	 */
	public List<Iteration> loadIterations(Project p) {
		EntityManager em = UserStory.entityManager();
		TypedQuery<Iteration> q = em.createQuery("FROM Iteration WHERE project = ? ORDER BY startDate", Iteration.class);
		q.setParameter(1, p);
		return q.getResultList();		
	}

	/**
	 * Loads features for the given project.
	 * @param p project
	 * @return features
	 */
	public List<ProjectFeature> loadFeatures(Project project) {
		EntityManager em = ProjectFeature.entityManager();
		TypedQuery<ProjectFeature> q = em.createQuery("FROM ProjectFeature WHERE project = ? ORDER BY name", ProjectFeature.class);
		q.setParameter(1, project);
		return q.getResultList();		
	}

	/**
	 * Loads releases for the given project.
	 * @param p project
	 * @return releases
	 */
	public List<ProjectRelease> loadReleases(Project project) {
		EntityManager em = ProjectRelease.entityManager();
		TypedQuery<ProjectRelease> q = em.createQuery("FROM ProjectRelease WHERE project = ? ORDER BY name", ProjectRelease.class);
		q.setParameter(1, project);
		return q.getResultList();		
	}

}
