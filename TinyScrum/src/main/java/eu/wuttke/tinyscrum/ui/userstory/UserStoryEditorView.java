package eu.wuttke.tinyscrum.ui.userstory;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Priority;
import eu.wuttke.tinyscrum.domain.UserStory;
import eu.wuttke.tinyscrum.domain.UserStoryStatus;
import eu.wuttke.tinyscrum.logic.ProjectManager;
import eu.wuttke.tinyscrum.logic.UserManager;
import eu.wuttke.tinyscrum.logic.UserStoryManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;

@Configurable(autowire=Autowire.BY_NAME)
public class UserStoryEditorView extends VerticalLayout {

	private Form form;
	private BeanItem<UserStory> item;
	private Select iterationSelect, featureSelect, releaseSelect;
	private TinyScrumApplication application;
	private ObjectSavedListener listener;
	
	public UserStoryEditorView(final TinyScrumApplication application, UserStory userStory, ObjectSavedListener listener) {
		this.application = application;
		this.listener = listener;
		
		item = new BeanItem<UserStory>(userStory);

		setSizeFull();
		setSpacing(true);
		
		form = new Form();
		form.setCaption("User Story Details");
		form.setSizeFull();
		form.setImmediate(true);
		form.setFormFieldFactory(new FormFieldFactory() {
			private static final long serialVersionUID = 1L;

			public Field createField(Item item, Object propertyId, Component uiContext) {
				if (propertyId.equals("title")) {
					TextField tf = new TextField("Title");
					tf.setRequired(true);
					tf.setRequiredError("Please enter a story title.");
					tf.setWidth("100%");
					return tf;
				} else if (propertyId.equals("owner")) {
					final List<String> owners = userManager.getProjectUserNames(application.getCurrentProject());
					Select sel = new Select("Owner", owners);
					sel.setWidth("200px");
					return sel;
				} else if (propertyId.equals("status")) {
					Select sel = new Select("Status", Arrays.asList(UserStoryStatus.values()));
					sel.setWidth("200px");
					sel.setNullSelectionAllowed(false);
					return sel;
				} else if (propertyId.equals("estimate")) {
					TextField tf = new TextField("Estimate (" + application.getCurrentProject().getStoryEstimateUnit() + ")");
					tf.addValidator(new DoubleValidator("Please enter a number."));
					return tf;
				} else if (propertyId.equals("actualEffort")) {
					TextField tf = new TextField("Effort (" + application.getCurrentProject().getStoryEstimateUnit() + ")");
					tf.addValidator(new DoubleValidator("Please enter a number."));
					return tf;
				} else if (propertyId.equals("priority")) {
					Select sel = new Select("Priority", Arrays.asList(Priority.values()));
					sel.setWidth("200px");
					return sel;
				} else if (propertyId.equals("currentDueDate")) {
					DateField df = new DateField("Current Due Date");
					df.setResolution(DateField.RESOLUTION_DAY);
					df.setShowISOWeekNumbers(true);
					return df;
				} else if (propertyId.equals("latestDueDate")) {
					DateField df = new DateField("Latest Due Date");
					df.setResolution(DateField.RESOLUTION_DAY);
					df.setShowISOWeekNumbers(true);
					return df;
				} else if (propertyId.equals("iteration")) {
					iterationSelect = new Select("Iteration");
					iterationSelect.setWidth("200px");
					iterationSelect.setContainerDataSource(new IndexedContainer(projectManager.loadIterations(application.getCurrentProject())));
					return iterationSelect;
				} else if (propertyId.equals("projectFeature")) {
					featureSelect = new Select("Feature");
					featureSelect.setWidth("200px");
					featureSelect.setContainerDataSource(new IndexedContainer(projectManager.loadFeatures(application.getCurrentProject())));
					return featureSelect;
				} else if (propertyId.equals("projectRelease")) {
					releaseSelect = new Select("Release");
					releaseSelect.setWidth("200px");
					releaseSelect.setContainerDataSource(new IndexedContainer(projectManager.loadReleases(application.getCurrentProject())));
					return releaseSelect;
				} else if (propertyId.equals("customerProject")) {
					Select customerProjectSelect = new Select("Project");
					customerProjectSelect.setWidth("200px");
					// TODO customer lookup?
					customerProjectSelect.setContainerDataSource(new IndexedContainer(projectManager.loadCustomerProjects(application.getCurrentProject(), null)));
					return customerProjectSelect;
				} else if (propertyId.equals("quote")) {
					Select quoteProjectSelect = new Select("Quote");
					quoteProjectSelect.setWidth("200px");
					quoteProjectSelect.setContainerDataSource(new IndexedContainer(projectManager.loadQuotes(application.getCurrentProject())));
					return quoteProjectSelect;
				} else if (propertyId.equals("description")) {
					RichTextArea rta = new RichTextArea("Description");
					rta.setWidth("100%");
					rta.setHeight("370px");
					return rta;
				} else
					return null;
			}
		});		
		addComponent(form);
		
		Button btnSave = new Button("Save Story");
		btnSave.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				saveStory();
			}
		});
		
		Button btnCancel = new Button("Cancel");
		btnCancel.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				getWindow().getParent().removeWindow(getWindow());
			}
		});

		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.addComponent(btnSave);
		hl.addComponent(btnCancel);

		addComponent(hl);
		setComponentAlignment(hl, Alignment.BOTTOM_RIGHT);
		
		setExpandRatio(form, 1f);
	}
	
	public void initForm() {
		form.setItemDataSource(item, Arrays.asList(new String[]{
				"title", "owner", "status", "estimate", "actualEffort",
				"priority", "currentDueDate", "latestDueDate",
				"iteration", "projectFeature", "projectRelease",
				"customerProject", "quote",
				"description"
		}));
		
		form.focus();
	}
	
	public void saveStory() {
		boolean newStory = item.getBean().getId() == null;
		form.commit();
		UserStory story = item.getBean();
		
		story.setCustomer(null);
		if (story.getCustomerProject() != null)
			story.setCustomer(story.getCustomerProject().getCustomer());
		
		story = userStoryManager.saveUserStory(item.getBean());
		getWindow().getParent().removeWindow(getWindow());

		if (listener != null)
			listener.objectSaved(story);
		
		if (newStory)
			application.getMainWindow().addWindow(new UserStoryViewWindow(application, story));
	}
	
	public void setUserStoryManager(UserStoryManager userStoryManager) {
		this.userStoryManager = userStoryManager;
	}
	
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	private UserStoryManager userStoryManager;
	private ProjectManager projectManager;
	private UserManager userManager;
	
	private static final long serialVersionUID = 1L;
	
}
