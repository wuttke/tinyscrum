package eu.wuttke.tinyscrum.ui;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.UserStory;

public class UserStoryWindow
extends Window {

	public UserStoryWindow(UserStory userStory) {
		setModal(true);
        setWidth("50%");
        setCaption("User Story");
		center();
		
		BeanContainer<Integer, UserStory> container = new BeanContainer<Integer, UserStory>(UserStory.class);
		container.addItem(userStory.getId().intValue(), userStory);
		
		Form form = new Form();
		form.setSizeFull();
		form.addField("title", new TextField("Title", container.getContainerProperty(userStory.getId(), "title")));
		addComponent(form);
		
		
	}
	
	private static final long serialVersionUID = -5750180111347530457L;

}
