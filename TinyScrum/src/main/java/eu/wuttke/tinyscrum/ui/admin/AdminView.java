package eu.wuttke.tinyscrum.ui.admin;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Customer;
import eu.wuttke.tinyscrum.domain.CustomerProject;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.ProjectFeature;
import eu.wuttke.tinyscrum.domain.ProjectRelease;
import eu.wuttke.tinyscrum.domain.Quote;
import eu.wuttke.tinyscrum.domain.ScrumUser;
import eu.wuttke.tinyscrum.domain.Team;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;
import eu.wuttke.tinyscrum.ui.admin.AdminBeanDetails.DetailsListener;
import eu.wuttke.tinyscrum.ui.admin.AdminBeanField.AdminBeanFieldType;
import eu.wuttke.tinyscrum.ui.admin.AdminBeanLister.ListerListener;
import eu.wuttke.tinyscrum.ui.misc.BeanUtil;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

@Configurable(autowire=Autowire.BY_NAME)
public class AdminView 
extends VerticalLayout 
implements RefreshableComponent, ClickListener, ListerListener, DetailsListener {

	private TinyScrumApplication application;
	private VerticalLayout adminPanel;
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Listers for admin beans
	 */
	private AdminBeanLister[] adminBeanListers = new AdminBeanLister[] {
		new AdminBeanLister("Projects", Project.class, new String[]{"id", "name"}, null, new float[] {1f,4f}, this),
		new AdminBeanLister("Features", ProjectFeature.class, new String[]{"id", "project", "name"}, null, new float[] {1f,4f,4f}, this),
		new AdminBeanLister("Releases", ProjectRelease.class, new String[]{"id", "project", "name"}, null, new float[] {1f,4f,4f}, this),
		new AdminBeanLister("Quotes", Quote.class, new String[]{"id", "quoteNumber", "title", "orderDate"}, null, new float[] {1f,1f,4f,1f}, this),
		new AdminBeanLister("Customers", Customer.class, new String[]{"id", "project", "name"}, null, new float[] {1f,4f,4f}, this),
		new AdminBeanLister("Customer Projects", CustomerProject.class, new String[]{"id", "project", "customer", "name"}, null, new float[] {1f,4f,4f,4f}, this),
		new IterationAdminLister(this),
		new AdminBeanLister("Users", ScrumUser.class, new String[]{"active", "userName", "fullName", "email"}, new String[]{"Active", "User Name", "Full Name", "E-Mail"}, new float[] {1f,3f,4f,3f}, this),
		new AdminBeanLister("Teams", Team.class, new String[]{"id", "name"}, null, new float[] {1f,4f}, this)		
	};
	
	private AdminBeanDetails[] adminBeanDetails = new AdminBeanDetails[] {
		new AdminBeanDetails("Project", this, new AdminBeanField[]{
				new AdminBeanField("name", "Name", AdminBeanFieldType.PLAIN_TEXT, true, "100%"),
				new AdminBeanField("description", "Description", AdminBeanFieldType.RICH_TEXT, false, "100%"),
				new AdminBeanField("storyEstimateUnit", "Story Estimate Unit", null, true, "80px"),
				new AdminBeanField("taskEstimateUnit", "Task Estimate Unit", null, true, "80px"),
				new AdminBeanField("calculateStoryEstimates", "Calculate Story Estimates", null, true, "80px")
		}),
		
		new AdminBeanDetails("Feature", this, new AdminBeanField[]{
				new AdminBeanField("name", "Name", AdminBeanFieldType.PLAIN_TEXT, true, "100%"),
				new AdminBeanField("project", "Project", null, true, "200px"),
				new AdminBeanField("description", "Description", AdminBeanFieldType.RICH_TEXT, false, "100%")
		}),
		
		new AdminBeanDetails("Release", this, new AdminBeanField[]{
				new AdminBeanField("name", "Name", AdminBeanFieldType.PLAIN_TEXT, true, "100%"),
				new AdminBeanField("project", "Project", null, true, "200px"),
				new AdminBeanField("plannedDate", "Planned Date", null, false, "200px"),
				new AdminBeanField("description", "Description", AdminBeanFieldType.RICH_TEXT, false, "100%")
		}),
		
		new AdminBeanDetails("Quote", this, new AdminBeanField[]{
				new AdminBeanField("issueDate", "Issue Date", null, false, "200px"),
				new AdminBeanField("orderDate", "Order Date", null, false, "200px"),
				new AdminBeanField("deliveryDate", "Delivery Date", null, false, "200px"),
				new AdminBeanField("quoteNumber", "Quote Number", AdminBeanFieldType.PLAIN_TEXT, true, "400px"),
				new AdminBeanField("title", "Title", AdminBeanFieldType.PLAIN_TEXT, true, "90%"),
				new AdminBeanField("orderNumber", "Order Number", AdminBeanFieldType.PLAIN_TEXT, false, "200px"),
				new AdminBeanField("externalOrderNumber", "External Order Number", AdminBeanFieldType.PLAIN_TEXT, false, "200px"),
				new AdminBeanField("status", "Status", null, true, "200px"),
				new AdminBeanField("comment", "Comment", AdminBeanFieldType.RICH_TEXT, false, "90%"),
				new AdminBeanField("project", "Project", null, true, "200px"),
				new AdminBeanField("customer", "Customer", null, true, "200px")
		}),
		
		new AdminBeanDetails("Customer", this, new AdminBeanField[]{
				new AdminBeanField("name", "Name", AdminBeanFieldType.PLAIN_TEXT, true, "100%"),
				new AdminBeanField("project", "Project", null, true, "200px"),
		}),
		
		new AdminBeanDetails("Customer Project", this, new AdminBeanField[]{
				new AdminBeanField("name", "Name", AdminBeanFieldType.PLAIN_TEXT, true, "100%"),
				new AdminBeanField("customer", "Customer", null, true, "200px"),
				new AdminBeanField("project", "Project", null, true, "200px"),
		}),
		
		new AdminBeanDetails("Iteration", this, new AdminBeanField[]{
				new AdminBeanField("name", "Name", AdminBeanFieldType.PLAIN_TEXT, true, "100%"),
				new AdminBeanField("project", "Project", null, true, "200px"),
				new AdminBeanField("status", "Status", null, true, "200px"),
				new AdminBeanField("startDate", "Start Date", null, true, "200px"),
				new AdminBeanField("durationDays", "Duration (Days)", AdminBeanFieldType.PLAIN_TEXT, true, "200px"),
				new AdminBeanField("team", "Team", null, false, "200px"),
				new AdminBeanField("description", "Description", AdminBeanFieldType.RICH_TEXT, false, "100%")
		}),
		
		new ScrumUserAdminDetails("User", this, new AdminBeanField[]{
				new AdminBeanField("active", "Active", AdminBeanFieldType.PLAIN_TEXT, true, "200px"),				
				new AdminBeanField("userName", "User name", AdminBeanFieldType.PLAIN_TEXT, true, "200px"),
				new AdminBeanField("password", "Password", AdminBeanFieldType.PASSWORD, true, "200px"),
				new AdminBeanField("fullName", "Full name", AdminBeanFieldType.PLAIN_TEXT, false, "200px"),
				new AdminBeanField("email", "E-Mail address", AdminBeanFieldType.PLAIN_TEXT, false, "200px")
		}),
		
		new AdminBeanDetails("Team", this, new AdminBeanField[]{
				new AdminBeanField("name", "Name", AdminBeanFieldType.PLAIN_TEXT, true, "100%"),
				new AdminBeanField("description", "Description", AdminBeanFieldType.RICH_TEXT, false, "100%")				
		}),
	};
	
	public AdminView(TinyScrumApplication application) {
		this.application = application;
		
		setSizeFull();
		setSpacing(true);
		setMargin(true);
		
		HorizontalLayout navigationButtonLayout = new HorizontalLayout();
		navigationButtonLayout.setSpacing(true);
		navigationButtonLayout.setMargin(true);

		for (AdminBeanLister lister : adminBeanListers) {
			Button adminButton = new Button(lister.getTitle());
			adminButton.setData(lister);
			adminButton.addListener((ClickListener)this);
			navigationButtonLayout.addComponent(adminButton);
		}
		
		adminPanel = new VerticalLayout();
		adminPanel.setSizeFull();
		
		addComponent(navigationButtonLayout);
		addComponent(adminPanel);
		setExpandRatio(adminPanel, 1f);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		AdminBeanLister lister = (AdminBeanLister)event.getButton().getData();
		adminPanel.removeAllComponents();
		adminPanel.addComponent(lister);
		refreshContent();
	}
	
	@Override
	public void refreshContent() {
		if (adminPanel.getComponentCount() > 0) {
			Component c = adminPanel.getComponent(0);
			if (c != null && c instanceof RefreshableComponent)
				((RefreshableComponent)c).refreshContent();
		}
	}
	
	@Override
	public void handleAddObject() {
		showDetails(null);
	}
	
	@Override
	public void handleEditObject(Object object) {
		showDetails(object);
	}
	
	@Override
	@Transactional
	public void handleDeleteObject(final Object object) {
		ConfirmDialog.show(application.getMainWindow(), "Delete Object", 
				"Delete '" + object.toString() + "'?",
		        "Yes", "No", new ConfirmDialog.Listener() {
					private static final long serialVersionUID = 1L;
					public void onClose(ConfirmDialog dialog) {
		                if (dialog.isConfirmed()) {
							Object obj2 = entityManager.merge(object);
							entityManager.remove(obj2);
							entityManager.flush();
							refreshContent();
		                }
					}
		});
	}
	
	@Override
	public void doCancelForm(Object bean) {
		adminPanel.removeAllComponents();
		showListerByClass(bean.getClass());
	}
	
	@Override
	@Transactional
	public void doCommitObject(Item item, Object bean) {
		if (BeanUtil.getPropertyValue(bean, "id") != null)
			bean = entityManager.merge(bean);
		else
			entityManager.persist(bean);
		entityManager.flush();
		showListerByClass(bean.getClass());
	}
	
	@Override
	public void doDeleteObject(Object bean) {
		handleDeleteObject(bean);
		showListerByClass(bean.getClass());
	}
	
	protected void showListerByClass(Class<? extends Object> clazz) {
		AdminBeanLister lister = findListerByClass(clazz);
		adminPanel.removeAllComponents();
		adminPanel.addComponent(lister);
		refreshContent();
	}

	private int findListerIndex(AdminBeanLister lister) {
		for (int i = 0; i < adminBeanListers.length; i++)
			if (adminBeanListers[i] == lister)
				return i;
		return -1;
	}

	private AdminBeanLister findListerByClass(Class<? extends Object> clazz) {
		for (int i = 0; i < adminBeanListers.length; i++)
			if (adminBeanListers[i].getBeanClass() == clazz)
				return adminBeanListers[i];
		return null;
	}

	protected void showDetails(Object object) {
		if (getComponentCount() > 0) {
			AdminBeanLister lister = (AdminBeanLister)adminPanel.getComponent(0);
			int idx = findListerIndex(lister);
			AdminBeanDetails details = adminBeanDetails[idx];
		
			if (object == null) {
				try {
					object = lister.getBeanClass().newInstance();
					initEmptyBean(object);
				} catch (Exception e) {
				}
			}
			
			details.setBean(object, lister.getBeanClass());
			adminPanel.removeAllComponents();
			adminPanel.addComponent(details);
			refreshContent();
		}
	}

	private void initEmptyBean(Object object) {
		// set String properties to "empty" instead of null (if not a reference)
		// (this can be done more efficiently)
		for (String propertyId : BeanUtil.getBeanPropertiesByType(object.getClass(), String.class)) {
			if (BeanUtil.getPropertyValue(object, propertyId) == null)
				BeanUtil.setPropertyValue(object, propertyId, "");
		}
	}

	private static final long serialVersionUID = 1L;

}
