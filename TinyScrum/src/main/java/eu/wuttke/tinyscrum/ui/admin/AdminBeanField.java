package eu.wuttke.tinyscrum.ui.admin;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

@RooJavaBean
@RooToString
@Configurable(autowire=Autowire.BY_NAME)
public class AdminBeanField {

	private String propertyId;
	private String caption;
	private AdminBeanFieldType fieldType = AdminBeanFieldType.PLAIN_TEXT;
	private String width;
	private Boolean required;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	enum AdminBeanFieldType {
		PLAIN_TEXT,
		MULTILINE_TEXT,
		RICH_TEXT
	}
	
	public AdminBeanField(String propertyId, String caption,
			AdminBeanFieldType fieldType, Boolean required, String width) {
		this.propertyId = propertyId;
		this.caption = caption;
		this.fieldType = fieldType;
		this.required = required;
		this.width = width;
	}

	@SuppressWarnings("unchecked")
	public Field createField(BeanItem<?> item, Property property) {
		try {
			Class<?> parentClass = item.getBean().getClass();
			java.lang.reflect.Field javaField = parentClass.getDeclaredField(getPropertyId());
			
			String caption = getCaption() != null ? getCaption() : getPropertyId();
			Class<?> propertyType = property.getType();
			boolean notNull = javaField.getAnnotation(NotNull.class) != null;
			Field field = null;
			if (propertyType.isAssignableFrom(Date.class)) {
				DateField df = new DateField(caption);
				df.setResolution(DateField.RESOLUTION_DAY);
				field = df;
			} else if (propertyType.isAssignableFrom(Boolean.class)) {
				CheckBox cb = new CheckBox(caption);
				field = cb;
			} else if (javaField.getAnnotation(ManyToOne.class) != null) {
				Select sel = new Select(caption);
				sel.setNullSelectionAllowed(!notNull);
				sel.setContainerDataSource(new BeanItemContainer<Object>((Class<? super Object>)propertyType, getEntityList(propertyType)));
				field = sel;
			} else if (javaField.getAnnotation(Enumerated.class) != null) {
				Select sel = new Select(caption);
				sel.setNullSelectionAllowed(!notNull);
				Enum<?>[] constants = ((Class<? extends Enum<?>>)propertyType).getEnumConstants();
				sel.setContainerDataSource(new BeanItemContainer<Enum<?>>(Enum.class, Arrays.asList(constants)));
				field = sel;			
			} else if (getFieldType() == AdminBeanFieldType.RICH_TEXT) {
				RichTextArea rta = new RichTextArea(caption);
				field = rta;
			} else if (getFieldType() == AdminBeanFieldType.MULTILINE_TEXT) {
				field = new TextArea(caption);
			} else if (getFieldType() == AdminBeanFieldType.PLAIN_TEXT) {
				field = new TextField(caption);
			} else {
				//?
				return null;
			}
	
			if (width != null)
				field.setWidth(width);
			
			if (required != null)
				field.setRequired(required);
			
			return field;
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("invalid property name", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getEntityList(Class<?> c) {
		Query q = entityManager.createQuery("FROM " + c.getSimpleName());
		return q.getResultList();
	}
	
}
