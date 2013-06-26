package eu.wuttke.tinyscrum.ui.iteration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Select;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;

public class IterationView 
extends VerticalLayout 
implements Property.ValueChangeListener {

	private Select cbIterationChooser;
	private BeanItemContainer<Iteration> iterationContainer = new BeanItemContainer<Iteration>(Iteration.class);
	private TinyScrumApplication application;
	private IterationTable iterationTable;
	private ValueChangeListener iterationChangedListener;
	private Iteration currentIteration;
	
	public IterationView(TinyScrumApplication application, ValueChangeListener iterationChangedListener) {
		this.application = application;
		this.iterationChangedListener = iterationChangedListener;
		initializeLayout();
	}

	private void initializeLayout() {
		setMargin(true);
		setSpacing(true);
		//setHeight("100%");
		setSizeFull();
		
		cbIterationChooser = new Select();
		cbIterationChooser.setWidth("100%");
		cbIterationChooser.addListener(this);
		cbIterationChooser.setItemCaptionPropertyId("name"); // ...
		cbIterationChooser.setNullSelectionAllowed(false);
		cbIterationChooser.setImmediate(true);

		iterationTable = new IterationTable(application);
		iterationTable.setSizeFull();
		//iterationTable.setWidth("100%");
		//iterationTable.setHeight("276px");
		
		addComponent(cbIterationChooser);
		addComponent(iterationTable);
		setExpandRatio(iterationTable, 1f);
		setComponentAlignment(iterationTable, Alignment.TOP_LEFT);
	}
	
	public void setIterations(List<Iteration> iterations) {
		iterationContainer.removeAllItems();
		if (iterations != null)
			iterationContainer.addAll(iterations);
		cbIterationChooser.setContainerDataSource(iterationContainer);		
	}
	
	public void setCurrentIteration(Iteration iteration) {
		currentIteration = iteration;
		boolean willHaveChange = nullSafeGetId(getIterationFromComboBox()) != nullSafeGetId(iteration);
		cbIterationChooser.setValue(iteration);
		
		// refreshIterationTable wird ansonsten durch den ValueChanged-Event ausgelöst
		if (!willHaveChange)
			refreshIterationTable();
			
		if (iteration != null)
			logger.info("set current iteration: {}", iteration.getName());
	}
	
	private long nullSafeGetId(Iteration iteration) {
		if (iteration == null || iteration.getId() == null)
			return 0;
		else
			return iteration.getId().longValue();
	}

	public Iteration getCurrentIteration() {
		return currentIteration;
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		currentIteration = getIterationFromComboBox();
		refreshIterationTable();
	}
	
	private Iteration getIterationFromComboBox() {
		Object iterationId = cbIterationChooser.getValue();
		if (iterationId instanceof Iteration)
			return (Iteration)iterationId;
		
		if (iterationId != null) {
			BeanItem<Iteration> i = iterationContainer.getItem(iterationId);
			if (i != null)
				return i.getBean();
		}
			
		return null;
	}
	
	private void refreshIterationTable() {
		iterationTable.loadIteration(currentIteration);
		if (iterationChangedListener != null)
			iterationChangedListener.valueChange(null);
	}
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(IterationView.class);

}
