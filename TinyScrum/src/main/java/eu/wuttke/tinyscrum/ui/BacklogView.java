package eu.wuttke.tinyscrum.ui;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.domain.UserStoryStatus;
import eu.wuttke.tinyscrum.logic.UserStoryManager;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

@Configurable(autowire=Autowire.BY_NAME)
public class BacklogView 
extends VerticalLayout 
implements ClickListener, ValueChangeListener, RefreshableComponent {
	
	private Button btnAddUserStory;
	private Button btnEditUserStory;
	private Button btnDeleteUserStory;
	private BacklogTable backlogTable;
	
	private TinyScrumApplication application;
	
	public BacklogView(TinyScrumApplication application) {
		this.application = application;
		
		setMargin(true);
		setSpacing(true);
		setSizeFull();
		
		backlogTable = new BacklogTable(application);
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
			addUserStory();
		else if (event.getButton() == btnEditUserStory)
			editUserStory((UserStory)backlogTable.getValue());
		else if (event.getButton() == btnDeleteUserStory)
			deleteUserStory((UserStory)backlogTable.getValue());
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		boolean enable = backlogTable.getValue() != null;
		btnEditUserStory.setEnabled(enable);
		btnDeleteUserStory.setEnabled(enable);
	}

	public void editUserStory(UserStory userStory) {
		if (userStory != null) {
			UserStoryEditorWindow w = new UserStoryEditorWindow(application, userStory, new ObjectSavedListener() {
				public void objectSaved(Object object) {
					application.getMainView().refreshContent();
				}
			});
			application.getMainWindow().addWindow(w);
		}
	}

	public void deleteUserStory(final UserStory userStory) {
		if (userStory != null) {
			ConfirmDialog.show(getWindow(), "Delete User Story", 
					"Delete user story '" + userStory.getTitle() + "' and all contained tasks?",
			        "Yes", "No", new ConfirmDialog.Listener() {
						private static final long serialVersionUID = 1L;
						public void onClose(ConfirmDialog dialog) {
			                if (dialog.isConfirmed()) {
			        			userStoryManager.deleteUserStory(userStory);
			        			refreshContent();
			                }
			            }
			        });
		}
	}

	public void addUserStory() {
		UserStory us = new UserStory();
		us.setId(0L);
		us.setTitle("");
		us.setDescription("");
		us.setProject(application.getCurrentProject());
		us.setStatus(UserStoryStatus.STORY_OPEN);
		
		UserStoryEditorWindow w = new UserStoryEditorWindow(application, us, new ObjectSavedListener() {
			public void objectSaved(Object object) {
				application.getMainView().refreshContent();
			}
		});
		application.getMainWindow().addWindow(w);
	}
	
	public void setUserStoryManager(UserStoryManager userStoryManager) {
		this.userStoryManager = userStoryManager;
	}

	private UserStoryManager userStoryManager;
	
	private static final long serialVersionUID = 6977286043653094687L;

}
