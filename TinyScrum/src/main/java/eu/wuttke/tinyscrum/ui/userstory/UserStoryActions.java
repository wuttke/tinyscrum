package eu.wuttke.tinyscrum.ui.userstory;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Component;
import org.vaadin.dialogs.ConfirmDialog;

import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.logic.UserStoryManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;

@RooJavaBean
@Component
@Configurable(autowire=Autowire.BY_NAME)
public class UserStoryActions {
	
	public void addUserStory(final TinyScrumApplication application) {
		UserStory us = new UserStory();
		us.setProject(application.getCurrentProject());
		
		UserStoryEditorWindow w = new UserStoryEditorWindow(application, us, new ObjectSavedListener() {
			public void objectSaved(Object object) {
				application.getMainView().refreshContent();
			}
		});
		application.getMainWindow().addWindow(w);
	}	

	public void editUserStory(final TinyScrumApplication application, final UserStory userStory) {
		if (userStory != null) {
			UserStoryEditorWindow w = new UserStoryEditorWindow(application, userStory, new ObjectSavedListener() {
				public void objectSaved(Object object) {
					application.getMainView().refreshContent();
				}
			});
			application.getMainWindow().addWindow(w);
		}
	}

	public void deleteUserStory(final TinyScrumApplication application, final UserStory userStory) {
		if (userStory != null) {
			// war: getWindow() ohne application
			ConfirmDialog.show(application.getMainWindow(), "Delete User Story", 
					"Delete user story '" + userStory.getTitle() + "' and all contained tasks?",
			        "Yes", "No", new ConfirmDialog.Listener() {
						private static final long serialVersionUID = 1L;
						public void onClose(ConfirmDialog dialog) {
			                if (dialog.isConfirmed()) {
			        			userStoryManager.deleteUserStory(userStory);
								application.getMainView().refreshContent();
			                }
			            }
			        });
		}
	}

	private UserStoryManager userStoryManager;

}
