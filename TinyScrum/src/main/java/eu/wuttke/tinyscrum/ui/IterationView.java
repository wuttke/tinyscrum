package eu.wuttke.tinyscrum.ui;

import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Select;
import com.vaadin.ui.VerticalLayout;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.web.TinyScrumApplication;

public class IterationView 
extends VerticalLayout 
implements Property.ValueChangeListener {

	private Select cbIterationChooser;
	private BeanItemContainer<Iteration> iterationContainer = new BeanItemContainer<Iteration>(Iteration.class);
	private TinyScrumApplication application;
	private IterationTable iterationTable;
	
	public IterationView(TinyScrumApplication application) {
		this.application = application;
		initializeLayout();
	}

	private void initializeLayout() {
		setMargin(true);
		setSpacing(true);
		setHeight("100%");
		
		cbIterationChooser = new Select();
		cbIterationChooser.setWidth("100%");
		cbIterationChooser.addListener(this);
		cbIterationChooser.setItemCaptionPropertyId("name"); // ...
		cbIterationChooser.setNullSelectionAllowed(false);
		cbIterationChooser.setImmediate(true);

		iterationTable = new IterationTable(application);
		iterationTable.setWidth("100%");
		iterationTable.setHeight("280px");
		
		addComponent(cbIterationChooser);
		addComponent(iterationTable);
		setComponentAlignment(iterationTable, Alignment.TOP_LEFT);
	}
	
	public void setIterations(List<Iteration> iterations) {
		iterationContainer.removeAllItems();
		if (iterations != null)
			iterationContainer.addAll(iterations);
		cbIterationChooser.setContainerDataSource(iterationContainer);		
	}
	
	public void setCurrentIteration(Iteration iteration) {
		cbIterationChooser.setValue(iteration);
		iterationTable.loadIteration(iteration);
	}
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		Object iterationId = cbIterationChooser.getValue();
		if (iterationId != null) {
			Iteration iteration = iterationContainer.getItem(iterationId).getBean();
			iterationTable.loadIteration(iteration);
		}
	}
	
	private static final long serialVersionUID = 1L;

}
