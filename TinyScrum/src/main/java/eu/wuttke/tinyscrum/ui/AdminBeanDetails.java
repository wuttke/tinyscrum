package eu.wuttke.tinyscrum.ui;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.ui.misc.BeanUtil;
import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

public class AdminBeanDetails 
extends VerticalLayout
implements RefreshableComponent, FormFieldFactory {
	
	interface DetailsListener {
		public void doCommitObject(Item item, Object bean);
		public void doCancelForm(Object bean);
		public void doDeleteObject(Object bean);
	}

	private Object bean;
	private BeanItem<Object> item;
	private AdminBeanField[] fields;
	private String title;
	private Form form;
	private Button btnDelete;
	private DetailsListener listener;
	
	public AdminBeanDetails(String title, DetailsListener listener, AdminBeanField[] fields) {
		this.title = title;
		this.fields = fields;
		this.listener = listener;
		initializeLayout();
	}	
	
	protected void initializeLayout() {
		setSizeFull();
		setWidth("100%");
		setMargin(true);
		setSpacing(true);
		
		Button btnOk = new Button("OK", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				handleCommit();
			}
		});
		
		Button btnCancel = new Button("Cancel", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				handleCancel();
			}
		});

		btnDelete = new Button("Delete", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				handleDelete();
			}
		});
		
		HorizontalLayout footerLayout = new HorizontalLayout();
		footerLayout.setWidth("100%");
		footerLayout.setSpacing(true);
		footerLayout.addComponent(btnDelete);
		footerLayout.addComponent(btnOk);
		footerLayout.addComponent(btnCancel);
		footerLayout.setExpandRatio(btnDelete, 1f);
		footerLayout.setComponentAlignment(btnDelete, Alignment.MIDDLE_LEFT);
		footerLayout.setComponentAlignment(btnOk, Alignment.MIDDLE_RIGHT);
		footerLayout.setComponentAlignment(btnCancel, Alignment.MIDDLE_RIGHT);
		
		form = new Form();
		form.setSizeFull();
		form.setWidth("100%");
		form.setCaption(title);
		form.setFormFieldFactory(this);
		form.setFooter(footerLayout);
		addComponent(form);
	}
	
	protected void handleCancel() {
		listener.doCancelForm(item.getBean());
	}

	protected void handleDelete() {
		form.commit();
		listener.doDeleteObject(item.getBean());
	}

	protected void handleCommit() {
		form.commit();
		listener.doCommitObject(item, item.getBean());
	}

	public void setBean(Object bean, Class<?> clazz) {
		String[] propertyIds = new String[fields.length];
		for (int i = 0; i < propertyIds.length; i++)
			propertyIds[i] = fields[i].getPropertyId();
		
		this.bean = bean;
		this.item = new BeanItem<Object>(bean, propertyIds);
		refreshContent();
	}
	
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Property property = item.getItemProperty(propertyId);
		AdminBeanField field = findField((String)propertyId);
		return field != null ? field.createField((BeanItem<?>)item, property) : null;
	}

	private AdminBeanField findField(String propertyId) {
		for (int i = 0; i < fields.length; i++)
			if (fields[i].getPropertyId().equals(propertyId))
				return fields[i];
		return null;
	}

	@Override
	public void refreshContent() {
		form.setItemDataSource(item);
		btnDelete.setEnabled(BeanUtil.getPropertyValue(bean, "id") != null);
		form.focus();
	}
	
	private static final long serialVersionUID = 1L;

}
