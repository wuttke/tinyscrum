package eu.wuttke.tinyscrum.ui.admin;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.transaction.annotation.Transactional;

import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.Iteration;
import eu.wuttke.tinyscrum.domain.IterationStatus;
import eu.wuttke.tinyscrum.ui.TinyScrumApplication;

public class BatchIterationCreatorWindow
extends Window {

	private DateField startDate;
	private TextField dayCount;
	private TextField iterationCount;
	
	public BatchIterationCreatorWindow() {
		setModal(true);
        setResizable(false);
		
        setWidth("400px");
        setHeight("300px");
        
        setCaption("Create multiple iterations");
        getContent().setSizeFull();
        center();
        
        startDate = new DateField();
        startDate.setCaption("Start Date");
        startDate.setRequired(true);
        startDate.setResolution(DateField.RESOLUTION_DAY);

        dayCount = new TextField();
        dayCount.setRequired(true);
        dayCount.setCaption("Iteration Length (days)");
        dayCount.setImmediate(true);
        dayCount.addValidator(new IntegerValidator("Please enter a positive number."));
        
        iterationCount = new TextField();
        iterationCount.setRequired(true);
        iterationCount.setCaption("Iteration Count");
        iterationCount.setImmediate(true);
        iterationCount.addValidator(new IntegerValidator("Please enter a positive number."));
        
        Button btnOk = new Button("OK");
        btnOk.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				createIterations();
			}
		});
        
        Button btnCancel = new Button("Cancel");
        btnCancel.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				doCancel();
			}
		});
        
        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeFull();
        hl.setSpacing(true);
        hl.addComponent(btnOk);
        hl.addComponent(btnCancel);
        hl.setComponentAlignment(btnOk, Alignment.BOTTOM_RIGHT);
        hl.setComponentAlignment(btnCancel, Alignment.BOTTOM_RIGHT);
        hl.setExpandRatio(btnOk, 1f);
        
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        vl.setMargin(true);
        vl.setSpacing(true);
        vl.addComponent(startDate);
        vl.addComponent(dayCount);
        vl.addComponent(iterationCount);
        vl.addComponent(hl);
        vl.setExpandRatio(hl, 1f);

        addComponent(vl);
	}

	protected void doCancel() {
		getApplication().getMainWindow().removeWindow(this);
	}

	protected void createIterations() {
		reallyCreateIterations();
		getApplication().getMainWindow().removeWindow(this);
		((TinyScrumApplication)getApplication()).getMainView().refreshContent();
	}

	@Transactional
	private void reallyCreateIterations() {
		Date date = (Date)startDate.getValue();
		int length = Integer.parseInt((String)dayCount.getValue());
		int count = Integer.parseInt((String)iterationCount.getValue());
		long total = Iteration.countIterations();
		if (length > 0)
			for (int i = 0; i < count; i++) {
				Date start = addDays(date, i * length);
				Date end = addDays(date, (i + 1) * length - 1);
				DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
				Iteration it = new Iteration();
				it.setDurationDays(length);
				it.setProject(((TinyScrumApplication)getApplication()).getCurrentProject());
				it.setStartDate(start);
				it.setStatus(IterationStatus.ITERATION_NEW);
				it.setTeam(null);
				// TODO customizable name string with placeholders
				it.setName("Iteration #" + (total + i + 1) + ": " + df.format(start) + " - " + df.format(end));
				it.persist();
			}
	}

	private Date addDays(Date date, int i) {
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, i);
		return c.getTime();
	}

	private static final long serialVersionUID = 1L;
	
}
