package eu.wuttke.tinyscrum.ui.userstory;

import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;

public class UserStoryEditorWindow 
extends Window {
	
	public UserStoryEditorWindow(TinyScrumApplication application, UserStory userStory, ObjectSavedListener listener) {
		setModal(true);
        setWidth("80%");
        setHeight("80%");
        
        if (userStory.getTitle() == null || userStory.getTitle().length() == 0)
        	setCaption("New User Story");
        else
        	setCaption("User Story: " + userStory.getTitle());
        getContent().setSizeFull();
        center();
		
        UserStoryEditorView editor = new UserStoryEditorView(application, userStory, listener);
        editor.initForm();
		addComponent(editor);
	}
	
	private static final long serialVersionUID = -5750180111347530457L;

}
