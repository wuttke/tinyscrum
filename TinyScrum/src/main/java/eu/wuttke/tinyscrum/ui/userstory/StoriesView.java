package eu.wuttke.tinyscrum.ui.userstory;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

@Configurable(autowire=Autowire.BY_NAME)
public class StoriesView 
extends VerticalLayout 
implements RefreshableComponent {
	
	private FilterableStoryTableView view;
	
	public StoriesView(TinyScrumApplication application) {
		setMargin(true);
		setSpacing(true);
		setSizeFull();
		
		view = new FilterableStoryTableView();
		view.setApplication(application);
		addComponent(view);
	}
	
	@Override
	public void refreshContent() {
		view.refreshContent();
	}

	private static final long serialVersionUID = 1L;

}
