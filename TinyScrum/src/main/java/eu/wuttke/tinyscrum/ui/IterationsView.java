package eu.wuttke.tinyscrum.ui;

import java.util.Date;
import java.util.List;

import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalSplitPanel;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.web.TinyScrumApplication;

public class IterationsView
extends HorizontalSplitPanel
implements RefreshableComponent {
	
	private TinyScrumApplication application;
	private BacklogView backlogView;
	private IterationView iterationView1, iterationView2;
	
	public IterationsView(TinyScrumApplication application) {
		this.application = application;
		
		initializeLayout();
	}
	
	private void initializeLayout() {
		backlogView = new BacklogView(application);
		iterationView1 = new IterationView(application);
		iterationView2 = new IterationView(application);
		
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
		
		List<Iteration> l = application.loadIterations();
		if (l != null) {
			iterationView1.setIterations(l);
			iterationView2.setIterations(l);
			
			Iteration currentIteration = findCurrentIteration(l);
			Iteration nextIteration = findNextIteration(l, currentIteration);
			
			iterationView1.setCurrentIteration(currentIteration);
			iterationView2.setCurrentIteration(nextIteration);
		} else {
			iterationView1.setCurrentIteration(null);
			iterationView2.setCurrentIteration(null);
		}
	}

	private Iteration findNextIteration(List<Iteration> l,
			Iteration currentIteration) {
		int idx = l.indexOf(currentIteration);
		if (idx + 1 < l.size())
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
	
	private static final long serialVersionUID = 1L;
	
}
