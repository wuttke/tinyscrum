package eu.wuttke.tinyscrum.ui;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.UserStory;

public class UserStoryWindow
extends Window {

	public UserStoryWindow(TinyScrumApplication application, UserStory userStory) {
		setModal(true);
        setWidth("80%");
        setHeight("80%");
        setCaption("User Story: " + userStory.getTitle());
        getContent().setSizeFull();
        
		center();
		
		TabSheet tabSheet = new TabSheet();
		tabSheet.setSizeFull();
		tabSheet.addTab(new UserStoryDetailsView(application), "Overview");
		tabSheet.addTab(new UserStoryTasksView(application, userStory), "Tasks");
		
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
		
		addComponent(tabSheet);
	}
	
	private static final long serialVersionUID = -5750180111347530457L;

}
