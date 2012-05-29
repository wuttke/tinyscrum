package eu.wuttke.tinyscrum.ui.admin;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.ui.misc.RefreshableComponent;

@Configurable(autowire=Autowire.BY_NAME)
public class AdminBeanLister 
extends VerticalLayout
implements RefreshableComponent, ItemClickListener {
	
	public interface ListerListener {
		public void handleAddObject();
		public void handleEditObject(Object object);
		public void handleDeleteObject(Object object);
	}
	
	private String title;
	private Class<?> beanClass;
	private ListerListener listener;
	
	private Table table;
	private Button btnAdd;
	private Button btnEdit;
	private Button btnDelete;
	private HorizontalLayout footer;
	
	private String addButtonCaption = "Add";
	private String editButtonCaption = "Edit";
	private String deleteButtonCaption = "Delete";
	
	private String[] visibleColumns;
	private String[] columnHeaders;
	private float[] columnWeights;
	
    @PersistenceContext
    private EntityManager entityManager;

	public AdminBeanLister() {
		initializeLayout();
	}
	
	public AdminBeanLister(String title, Class<?> beanClass, String[] visibleColumns, String[] columnHeaders, float[] columnWeights, ListerListener listener) {
		this.title = title;
		this.beanClass = beanClass;
		this.visibleColumns = visibleColumns;
		this.columnHeaders = columnHeaders;
		this.columnWeights = columnWeights;
		this.listener = listener;
		
		if (columnHeaders != null) {
			assert visibleColumns != null;
			assert columnHeaders.length == visibleColumns.length;
		}
		
		if (columnWeights != null) {
			assert visibleColumns != null;
			assert visibleColumns.length == columnWeights.length;
		}
		
		initializeLayout();
	}
	
	protected void initializeLayout() {
		setMargin(true);
		setSpacing(true);
		setSizeFull();
		
		Form form = new Form();
		form.setCaption(title);
		
		table = new Table();
		table.setSelectable(true);
		table.setImmediate(true);
		table.setMultiSelect(false);
		table.setFooterVisible(true);
		table.setSizeFull();
		table.addListener((ItemClickListener)this);
		table.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			public void valueChange(ValueChangeEvent event) {
				boolean enabled = table.getValue() != null;
				btnEdit.setEnabled(enabled);
				btnDelete.setEnabled(enabled);
			}
		});
		
		btnAdd = new Button(addButtonCaption, new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				doAddEntity();
			}
		});
		
		btnEdit = new Button(editButtonCaption, new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				doEditEntity(table.getValue());
			}
		});
		btnEdit.setEnabled(false);
		
		btnDelete = new Button(deleteButtonCaption, new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				doDeleteEntity(table.getValue());
			}
		});
		btnDelete.setEnabled(false);
		
		footer = new HorizontalLayout();
		footer.addComponent(btnAdd);
		footer.addComponent(btnEdit);
		footer.addComponent(btnDelete);
		footer.setSpacing(true);
		
		addComponent(form);
		addComponent(table);
		addComponent(footer);
		setExpandRatio(table, 1f);
	}
	
	protected void doAddEntity() {
		if (listener != null)
			listener.handleAddObject();
	}

	protected void doEditEntity(Object obj) {
		if (listener != null)
			listener.handleEditObject(obj);
	}

	protected void doDeleteEntity(Object obj) {
		if (listener != null)
			listener.handleDeleteObject(obj);
	}

	@Override
	public void itemClick(ItemClickEvent event) {
		if (event.isDoubleClick())
			doEditEntity(event.getItemId());
	}

	@Override
	public void refreshContent() {
		List<?> objects = loadEntityList();
		@SuppressWarnings("unchecked")
		BeanItemContainer<Object> container = new BeanItemContainer<Object>((Class<? super Object>)beanClass);
		container.addAll(objects);
		table.setContainerDataSource(container);
		if (visibleColumns != null)
			table.setVisibleColumns(visibleColumns);
		if (columnHeaders != null)
			table.setColumnHeaders(columnHeaders);
		if (columnWeights != null && visibleColumns != null) {
			for (int i = 0; i < columnWeights.length; i++)
				table.setColumnExpandRatio(visibleColumns[i], columnWeights[i]);
		}
	}

	protected List<?> loadEntityList() {
		Query q = entityManager.createQuery("FROM " + beanClass.getSimpleName());
		return q.getResultList();
	}
	
	public String getTitle() {
		return title;
	}
	
	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public String getAddButtonCaption() {
		return addButtonCaption;
	}

	public void setAddButtonCaption(String addButtonCaption) {
		this.addButtonCaption = addButtonCaption;
	}

	public String getEditButtonCaption() {
		return editButtonCaption;
	}

	public void setEditButtonCaption(String editButtonCaption) {
		this.editButtonCaption = editButtonCaption;
	}

	public String getDeleteButtonCaption() {
		return deleteButtonCaption;
	}

	public void setDeleteButtonCaption(String deleteButtonCaption) {
		this.deleteButtonCaption = deleteButtonCaption;
	}

	public String[] getVisibleColumns() {
		return visibleColumns;
	}

	public void setVisibleColumns(String[] visibleColumns) {
		this.visibleColumns = visibleColumns;
	}

	public String[] getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(String[] columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	public float[] getColumnWeights() {
		return columnWeights;
	}

	public void setColumnWeights(float[] columnWeights) {
		this.columnWeights = columnWeights;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public HorizontalLayout getFooter() {
		return footer;
	}

	private static final long serialVersionUID = 1L;
	
}
