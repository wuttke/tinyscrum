package eu.wuttke.tinyscrum.ui;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.Project;
import eu.wuttke.tinyscrum.domain.ProjectFeature;
import eu.wuttke.tinyscrum.domain.ProjectRelease;
import eu.wuttke.tinyscrum.domain.ScrumUser;
import eu.wuttke.tinyscrum.domain.Team;
import eu.wuttke.tinyscrum.ui.AdminBeanDetails.DetailsListener;
import eu.wuttke.tinyscrum.ui.AdminBeanField.AdminBeanFieldType;
import eu.wuttke.tinyscrum.ui.AdminBeanLister.ListerListener;
import eu.wuttke.tinyscrum.ui.misc.BeanUtil;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

@Configurable(autowire=Autowire.BY_NAME)
public class AdminView 
extends VerticalLayout 
implements RefreshableComponent, ClickListener, ListerListener, DetailsListener {

	@SuppressWarnings("unused")
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
		new AdminBeanLister("Iterations", Iteration.class, new String[]{"id", "project", "name", "startDate", "durationDays", "status"}, new String[]{"Id", "Project", "Name", "Start Date", "Duration", "Status"}, new float[] {1f,3f,3f,2f,1f,2f}, this),
		new AdminBeanLister("Users", ScrumUser.class, new String[]{"active", "userName", "fullName", "email"}, new String[]{"Active", "User Name", "Full Name", "E-Mail"}, new float[] {1f,3f,4f,3f}, this),
		new AdminBeanLister("Teams", Team.class, new String[]{"id", "name"}, null, new float[] {1f,4f}, this)		
	};
	
	private AdminBeanDetails[] adminBeanDetails = new AdminBeanDetails[] {
		new AdminBeanDetails("Project", this, new AdminBeanField[]{
				new AdminBeanField("name", "Name", AdminBeanFieldType.PLAIN_TEXT, true, "100%"),
				new AdminBeanField("description", "Description", AdminBeanFieldType.RICH_TEXT, false, "100%"),
				new AdminBeanField("storyEstimateUnit", "Story Estimate Unit", null, true, "80px"),
				new AdminBeanField("taskEstimateUnit", "Task Estimate Unit", null, true, "80px")
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
		
		new AdminBeanDetails("Iteration", this, new AdminBeanField[]{
				new AdminBeanField("name", "Name", AdminBeanFieldType.PLAIN_TEXT, true, "100%"),
				new AdminBeanField("project", "Project", null, true, "200px"),
				new AdminBeanField("status", "Status", null, true, "200px"),
				new AdminBeanField("startDate", "Start Date", null, true, "200px"),
				new AdminBeanField("durationDays", "Duration (Days)", AdminBeanFieldType.PLAIN_TEXT, true, "200px"),
				new AdminBeanField("team", "Team", null, false, "200px"),
				new AdminBeanField("description", "Description", AdminBeanFieldType.RICH_TEXT, false, "100%")
		}),
		
		new AdminBeanDetails("User", this, new AdminBeanField[]{
				new AdminBeanField("active", "Active", AdminBeanFieldType.PLAIN_TEXT, true, "200px"),				
				new AdminBeanField("userName", "User name", AdminBeanFieldType.PLAIN_TEXT, true, "200px"),
				new AdminBeanField("password", "Password", AdminBeanFieldType.PLAIN_TEXT, true, "200px"),
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
	public void handleDeleteObject(Object object) {
		object = entityManager.merge(object);
		entityManager.remove(object);
		entityManager.flush();
		refreshContent();
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
