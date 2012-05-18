package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.web.TinyScrumApplication;

public class UserStoryWindow
extends Window {

	public UserStoryWindow(TinyScrumApplication application, UserStory userStory) {
		setModal(true);
        setWidth("800px");
        setHeight("600px");
        setCaption("User Story: " + userStory.getTitle());
		center();
		
		/*
		BeanItem<UserStory> item = new BeanItem<UserStory>(userStory);
		List<String> owners = Arrays.asList(new String[]{"user1", "user2", "user3"});
		Form form = new Form();
		//form.setItemDataSource(item);
		form.setValue(item);
		form.setSizeFull();
		form.addField("title", new TextField("Title"));
		form.addField("description", new RichTextArea("Description"));
		form.addField("owner", new Select("Owner", owners));
		form.addField("status", new Select("Status", Arrays.asList(UserStoryStatus.values())));
		form.addField("iteration", new Select("Iteration", application.loadIterations()));
		form.addField("feature", new Select("Feature", ProjectFeature.findAllProjectFeatures()));
		form.addField("release", new Select("Release", ProjectRelease.findAllProjectReleases()));
		addComponent(form);
		*/
		
		UserStoryView usv = new UserStoryView(application);
		addComponent(usv);
	}
	
	private static final long serialVersionUID = -5750180111347530457L;

}
