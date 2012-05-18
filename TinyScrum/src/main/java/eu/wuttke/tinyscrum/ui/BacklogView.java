package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.web.TinyScrumApplication;

public class BacklogView 
extends VerticalLayout 
implements ClickListener, RefreshableComponent {
	
	private Button btnAddUserStory;
	private Button btnDeleteUserStory;
	private BacklogTable backlogTable;
	
	private TinyScrumApplication application;
	
	public BacklogView(TinyScrumApplication application) {
		this.application = application;
		
		setMargin(true);
		setSpacing(true);
		setHeight("700px");
		
		backlogTable = new BacklogTable(application);
		backlogTable.setHeight("600px");
		addComponent(backlogTable);
		
		// Add User Story
		btnAddUserStory = new Button("Add User Story", this);
		btnDeleteUserStory = new Button("Delete User Story", this);
		btnDeleteUserStory.setEnabled(false);
		
		// Button Bar
		HorizontalLayout buttonBar = new HorizontalLayout();
		buttonBar.addComponent(btnAddUserStory);
		buttonBar.addComponent(btnDeleteUserStory);
		buttonBar.setSpacing(true);
		addComponent(buttonBar);		
	}
	
	@Override
	public void refreshContent() {
		backlogTable.loadBacklog();
	}
	
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == btnAddUserStory)
			addUserStory();
		else if (event.getButton() == btnDeleteUserStory)
			deleteUserStory(backlogTable.getValue());
	}

	public void deleteUserStory(Object userStoryId) {
	}

	public void addUserStory() {
		UserStory us = new UserStory();
		us.setId(0L);
		us.setTitle("New Story");
		UserStoryWindow w = new UserStoryWindow(us);
		application.getMainWindow().addWindow(w);
	}

	private static final long serialVersionUID = 6977286043653094687L;

}
