package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.UserStory;

public class UserStoryEditorWindow 
extends Window {
	
	public UserStoryEditorWindow(TinyScrumApplication application, UserStory userStory) {
		setModal(true);
        setWidth("80%");
        setHeight("80%");
        
        if (userStory.getTitle() == null || userStory.getTitle().length() == 0)
        	setCaption("New User Story");
        else
        	setCaption("User Story: " + userStory.getTitle());
        getContent().setSizeFull();
        center();
		
        UserStoryEditorView editor = new UserStoryEditorView(application, userStory);
        editor.initForm();
		addComponent(editor);
	}
	
	private static final long serialVersionUID = -5750180111347530457L;

}