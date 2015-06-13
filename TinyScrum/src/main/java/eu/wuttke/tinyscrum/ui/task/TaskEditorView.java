package eu.wuttke.tinyscrum.ui.task;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.DoubleValidator;
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
import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.Task;
import eu.wuttke.tinyscrum.domain.TaskStatus;
import eu.wuttke.tinyscrum.logic.TaskManager;
import eu.wuttke.tinyscrum.logic.UserManager;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.misc.ObjectSavedListener;

@Configurable(autowire=Autowire.BY_NAME)
public class TaskEditorView extends VerticalLayout {

	public interface TaskSaved {
		public void taskSaved();
	}
	
	private Form form;
	private BeanItem<Task> item;
	private TinyScrumApplication application;
	private ObjectSavedListener listener;
	private List<String> users;
	private RichTextArea rtaDescription;
	
	public TaskEditorView(final TinyScrumApplication application, Task task, ObjectSavedListener listener) {
		this.application = application;
		this.listener = listener;
		
		item = new BeanItem<Task>(task);

		setSizeFull();
		setSpacing(true);
		
		form = new Form();
		form.setCaption("Task Details");
		form.setWidth("100%");
		form.setHeight("270px");
		form.setImmediate(true);
		
		form.setFormFieldFactory(new FormFieldFactory() {
			private static final long serialVersionUID = 1L;
			public Field createField(Item item, Object propertyId, Component uiContext) {
				if (propertyId.equals("name")) {
					TextField tf = new TextField("Name");
					tf.setRequired(true);
					tf.setRequiredError("Please enter a task title.");
					tf.setWidth("100%");
					return tf;
				} else if (propertyId.equals("developer1")) {
					Select sel = new Select("Developer 1", users);
					sel.setWidth("200px");
					return sel;
				} else if (propertyId.equals("developer2")) {
					Select sel = new Select("Developer 2", users);
					sel.setWidth("200px");
					return sel;
				} else if (propertyId.equals("tester")) {
					Select sel = new Select("Tester", users);
					sel.setWidth("200px");
					return sel;
				} else if (propertyId.equals("status")) {
					Select sel = new Select("Status", Arrays.asList(TaskStatus.values()));
					sel.setWidth("200px");
					sel.setNullSelectionAllowed(false);
					return sel;
				} else if (propertyId.equals("estimate")) {
					TextField tf = new TextField("Estimate (" + application.getCurrentProject().getTaskEstimateUnit() + ")");
					tf.addValidator(new DoubleValidator("Please enter a number."));
					return tf;
				} else if (propertyId.equals("actualEffort")) {
					TextField tf = new TextField("Effort (" + application.getCurrentProject().getTaskEstimateUnit() + ")");
					tf.addValidator(new DoubleValidator("Please enter a number."));
					return tf;
				} /*else if (propertyId.equals("description")) {
					RichTextArea rta = new RichTextArea("Description");
					rta.setWidth("100%");
					rta.setHeight("340px");
					return rta;
				} */ else
					return null;
			}
		});		
		addComponent(form);

		rtaDescription = new RichTextArea();
		rtaDescription.setWidth("100%");
		rtaDescription.setHeight("100%");
		rtaDescription.setValue(task.getDescription());
		addComponent(rtaDescription);
		
		Button btnSave = new Button("Save Task");
		btnSave.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				saveTask();
			}
		});
		
		Button btnCancel = new Button("Cancel");
		btnCancel.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				getWindow().getParent().removeWindow(getWindow());
			}
		});

		Button btnView = new Button("View Task");
		btnView.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				Window parent = getWindow().getParent();
				parent.removeWindow(getWindow());
				TaskDetailsWindow tdw = new TaskDetailsWindow(application, item.getBean());
				parent.addWindow(tdw);
			}
		});
		
		HorizontalLayout hl2 = new HorizontalLayout();
		hl2.addComponent(btnView);
		hl2.setComponentAlignment(btnView, Alignment.BOTTOM_LEFT);
		hl2.setWidth("100%");
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.setSpacing(true);
		hl.addComponent(hl2);
		hl.addComponent(btnSave);
		hl.addComponent(btnCancel);
		hl.setExpandRatio(hl2, 1f);
		hl.setWidth("100%");

		addComponent(hl);
		setComponentAlignment(hl, Alignment.BOTTOM_RIGHT);
		
		setExpandRatio(rtaDescription, 1f);
	}
	
	public void initForm() {
		users = userManager.getProjectUserNames(application.getCurrentProject());
		
		form.setItemDataSource(item, Arrays.asList(new String[]{
				"name", "developer1", "developer2", "tester", "status", "estimate",
				"actualEffort", "description"
		}));
		
		form.focus();
	}
	
	public void saveTask() {
		form.commit();
		item.getBean().setDescription((String)rtaDescription.getValue());
		Task task = taskManager.saveTask(item.getBean());
		taskManager.calculateStoryEffort(task.getStory());
		getWindow().getParent().removeWindow(getWindow());
		if (listener != null)
			listener.objectSaved(task);
	}
	
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	private TaskManager taskManager;
	private UserManager userManager;
	
	private static final long serialVersionUID = 1L;
	
}
