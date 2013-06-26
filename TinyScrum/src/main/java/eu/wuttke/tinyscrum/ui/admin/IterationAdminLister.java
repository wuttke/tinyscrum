package eu.wuttke.tinyscrum.ui.admin;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;

public class IterationAdminLister 
extends AdminBeanLister {
	
	private static final long serialVersionUID = 1L;

	public IterationAdminLister(AdminView parent) {
		super("Iterations", Iteration.class, 
				new String[]{"id", "project", "name", "startDate", "durationDays", "status"}, 
				new String[]{"Id", "Project", "Name", "Start Date", "Duration", "Status"}, 
				new float[] {1f,3f,3f,2f,1f,2f}, parent);
		
		Button btnBatchCreate = new Button("Create multiple iterations");
		btnBatchCreate.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				((TinyScrumApplication)getApplication()).getMainWindow().addWindow(new BatchIterationCreatorWindow());
			}
		});
		
		getFooter().addComponent(btnBatchCreate);
	}
	
}
