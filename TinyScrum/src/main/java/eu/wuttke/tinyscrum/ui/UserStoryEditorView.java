package eu.wuttke.tinyscrum.ui;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.domain.UserStoryStatus;
import eu.wuttke.tinyscrum.logic.UserStoryManager;

@Configurable(autowire=Autowire.BY_NAME)
public class UserStoryEditorView extends VerticalLayout {

	private Form form;
	private BeanItem<UserStory> item;
	private Select iterationSelect, featureSelect, releaseSelect;
	private TinyScrumApplication application;
	
	public UserStoryEditorView(final TinyScrumApplication application, UserStory userStory) {
		this.application = application;
		
		item = new BeanItem<UserStory>(userStory);
		final List<String> owners = Arrays.asList(new String[]{"user1", "user2", "user3"});

		setSizeFull();
		setSpacing(true);
		
		form = new Form();
		form.setCaption("User Story Details");
		form.setSizeFull();
		form.setFormFieldFactory(new FormFieldFactory() {
			public Field createField(Item item, Object propertyId, Component uiContext) {
				if (propertyId.equals("title")) {
					TextField tf = new TextField("Title");
					tf.setRequired(true);
					tf.setWidth("100%");
					return tf;
				} else if (propertyId.equals("owner"))
					return new Select("Owner", owners);
				else if (propertyId.equals("status")) {
					Select sel = new Select("Status", Arrays.asList(UserStoryStatus.values()));
					sel.setNullSelectionAllowed(false);
					return sel;
				} else if (propertyId.equals("estimate")) {
					TextField tf = new TextField("Estimate");
					return tf;
				} else if (propertyId.equals("iteration")) {
					iterationSelect = new Select("Iteration");
					iterationSelect.setContainerDataSource(new IndexedContainer(userStoryManager.loadIterations(application.getCurrentProject())));
					return iterationSelect;
				} else if (propertyId.equals("projectFeature")) {
					featureSelect = new Select("Feature");
					featureSelect.setContainerDataSource(new IndexedContainer(userStoryManager.loadFeatures(application.getCurrentProject())));
					return featureSelect;
				} else if (propertyId.equals("projectRelease")) {
					releaseSelect = new Select("Release");
					releaseSelect.setContainerDataSource(new IndexedContainer(userStoryManager.loadReleases(application.getCurrentProject())));
					return releaseSelect;
				} else if (propertyId.equals("description")) {
					RichTextArea rta = new RichTextArea("Description");
					rta.setSizeFull();
					return rta;
				} else
					return null;
			}
		});		
		addComponent(form);
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.setWidth("100%");
		Button btnSave = new Button("Save Story");
		btnSave.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				saveStory();
			}
		});
		hl.addComponent(btnSave);
		
		Button btnCancel = new Button("Cancel");
		btnCancel.addListener(new ClickListener() {
			public void buttonClick(ClickEvent event) {
				getWindow().getParent().removeWindow(getWindow());
			}
		});
		hl.addComponent(btnCancel);
		hl.setComponentAlignment(btnSave, Alignment.BOTTOM_RIGHT);
		form.getFooter().addComponent(hl);
		
		setExpandRatio(form, 1f);
	}
	
	public void initForm() {
		form.setItemDataSource(item, Arrays.asList(new String[]{
				"title", "owner", "status", "estimate",
				"iteration", "projectFeature", "projectRelease",
				"description"
		}));
		
		form.focus();
	}
	
	public void saveStory() {
		boolean newStory = item.getBean().getId() == null;
		form.commit();
		UserStory story = userStoryManager.saveUserStory(item.getBean());
		getWindow().getParent().removeWindow(getWindow());
		application.getMainView().refreshContent();
		if (newStory)
			application.getMainWindow().addWindow(new UserStoryViewWindow(application, story));
	}
	
	public void setUserStoryManager(UserStoryManager userStoryManager) {
		this.userStoryManager = userStoryManager;
	}

	private UserStoryManager userStoryManager;

	private static final long serialVersionUID = 1L;
	
}
