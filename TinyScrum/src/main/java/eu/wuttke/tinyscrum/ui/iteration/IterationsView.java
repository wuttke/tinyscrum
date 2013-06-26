package eu.wuttke.tinyscrum.ui.iteration;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalSplitPanel;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.logic.ProjectManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;
import eu.wuttke.tinyscrum.ui.userstory.BacklogView;

@Configurable(autowire=Autowire.BY_NAME)
public class IterationsView
extends HorizontalSplitPanel
implements RefreshableComponent {
	
	private TinyScrumApplication application;
	private BacklogView backlogView;
	private IterationView iterationView1, iterationView2;
	private Iteration rememberIteration1, rememberIteration2;
	
	public IterationsView(TinyScrumApplication application) {
		this.application = application;
		
		initializeLayout();
	}
	
	private void initializeLayout() {
		backlogView = new BacklogView(application);
		iterationView1 = new IterationView(application, new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				rememberIteration1 = iterationView1.getCurrentIteration();
			}
		});

		iterationView2 = new IterationView(application, new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				rememberIteration2 = iterationView2.getCurrentIteration();
			}
		});
		
		//VerticalLayout iterationSplitPanel = new VerticalLayout(); 
		VerticalSplitPanel iterationSplitPanel = new VerticalSplitPanel();
		iterationSplitPanel.addComponent(iterationView1);
		iterationSplitPanel.addComponent(iterationView2);

		setSizeFull();
		//setHeight("700px");
		//setWidth("100%");
		addComponent(backlogView);
		addComponent(iterationSplitPanel);
	}

	@Override
	public void refreshContent() {
		backlogView.refreshContent();
		
		List<Iteration> l = projectManager.loadIterations(application.getCurrentProject());
		if (l != null && l.size() > 0) {
			iterationView1.setIterations(l);
			iterationView2.setIterations(l);
			
			Iteration currentIteration = findCurrentIteration(l);
			Iteration nextIteration = findNextIteration(l, currentIteration);
			
			if (rememberIteration1 != null)
				currentIteration = findIterationById(l, rememberIteration1.getId());
			if (rememberIteration2 != null)
				nextIteration = findIterationById(l, rememberIteration2.getId());
			
			iterationView1.setCurrentIteration(currentIteration);
			rememberIteration1 = currentIteration;
			
			iterationView2.setCurrentIteration(nextIteration);
			rememberIteration2 = nextIteration;
		} else {
			iterationView1.setCurrentIteration(null);
			iterationView2.setCurrentIteration(null);
		}
	}

	private Iteration findIterationById(List<Iteration> l, long id) {
		for (Iteration i : l)
			if (i.getId().longValue() == id)
				return i;
		return null;
	}

	private Iteration findNextIteration(List<Iteration> l,
			Iteration currentIteration) {
		int idx = currentIteration != null ? l.indexOf(currentIteration) : -1;
		if (idx != -1 && idx + 1 < l.size())
			return l.get(idx + 1);
		else
			return null;
	}

	private Iteration findCurrentIteration(List<Iteration> l) {
		Date now = new Date();
		for (Iteration i : l) 
			if (i.containsDate(now))
				return i;
		return null;
	}
	
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	private ProjectManager projectManager;
	
	private static final long serialVersionUID = 1L;
	
}
