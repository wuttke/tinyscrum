package eu.wuttke.tinyscrum.web;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.vaadin.Application;
import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.ui.MainView;

public class TinyScrumApplication 
extends Application {
	
	private static final long serialVersionUID = -1486443784466891755L;

	private MainView mainView;
	
	@Override
	public void init() {
		mainView = new MainView(this);
		Window mainWindow = new Window("TinyScrum");
		mainWindow.getContent().setSizeFull();
		mainWindow.addComponent(mainView);
		setMainWindow(mainWindow);
	}
	
	public MainView getMainView() {
		return mainView;
	}

	@SuppressWarnings("unchecked")
	public List<UserStory> loadBacklogUserStories() {
		EntityManager em = UserStory.entityManager();
		Query q = em.createQuery("FROM UserStory WHERE iteration IS NULL AND project = ? ORDER BY sequenceNumber", UserStory.class);
		Project p = getMainView().getCurrentProject();
		q.setParameter(1, p);
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserStory> loadIterationUserStories(Iteration i) {
		EntityManager em = UserStory.entityManager();
		Query q = em.createQuery("FROM UserStory WHERE iteration = ? AND project = ? ORDER BY sequenceNumber", UserStory.class);
		Project p = getMainView().getCurrentProject();
		q.setParameter(1, i);
		q.setParameter(2, p);
		return q.getResultList();		
	}

	@SuppressWarnings("unchecked")
	public List<Iteration> loadIterations() {
		EntityManager em = UserStory.entityManager();
		Query q = em.createQuery("FROM Iteration WHERE project = ? ORDER BY startDate", Iteration.class);
		Project p = getMainView().getCurrentProject();
		q.setParameter(1, p);
		return q.getResultList();		
	}

}
