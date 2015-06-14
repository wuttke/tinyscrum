package eu.wuttke.tinyscrum.ui.userstory;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

@Configurable(autowire=Autowire.BY_NAME)
public class BacklogView 
extends VerticalLayout 
implements ClickListener, ValueChangeListener, RefreshableComponent {
	
	private Button btnAddUserStory;
	private Button btnEditUserStory;
	private Button btnDeleteUserStory;
	private BacklogStoryTable backlogTable;
	
	private TinyScrumApplication application;
	
	public BacklogView(TinyScrumApplication application) {
		this.application = application;
		
		setMargin(true);
		setSpacing(true);
		setSizeFull();
		
		backlogTable = new BacklogStoryTable(application);
		backlogTable.setSizeFull();
		backlogTable.addListener(this);
		backlogTable.setImmediate(true);
		addComponent(backlogTable);
		
		// Buttons
		btnAddUserStory = new Button("Add User Story", this);
		btnEditUserStory = new Button("Edit User Story", this);
		btnEditUserStory.setEnabled(false);
		btnDeleteUserStory = new Button("Delete User Story", this);
		btnDeleteUserStory.setEnabled(false);
		
		// Button Bar
		HorizontalLayout buttonBar = new HorizontalLayout();
		buttonBar.addComponent(btnAddUserStory);
		buttonBar.addComponent(btnEditUserStory);
		buttonBar.addComponent(btnDeleteUserStory);
		buttonBar.setSpacing(true);
		addComponent(buttonBar);
		
		setExpandRatio(backlogTable, 1f);
	}
	
	@Override
	public void refreshContent() {
		backlogTable.loadBacklog();
	}
	
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == btnAddUserStory)
			userStoryActions.addUserStory(application);
		else if (event.getButton() == btnEditUserStory)
			userStoryActions.editUserStory(application, (UserStory)backlogTable.getValue());
		else if (event.getButton() == btnDeleteUserStory)
			userStoryActions.deleteUserStory(application, (UserStory)backlogTable.getValue());
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		boolean enable = backlogTable.getValue() != null;
		btnEditUserStory.setEnabled(enable);
		btnDeleteUserStory.setEnabled(enable);
	}

	public void setUserStoryActions(UserStoryActions userStoryActions) {
		this.userStoryActions = userStoryActions;
	}
	
	private UserStoryActions userStoryActions;
	
	private static final long serialVersionUID = 6977286043653094687L;

}
