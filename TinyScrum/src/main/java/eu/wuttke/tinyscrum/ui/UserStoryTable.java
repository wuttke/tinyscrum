package eu.wuttke.tinyscrum.ui;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.ui.Table;

import eu.wuttke.tinyscrum.domain.UserStory;

public class UserStoryTable 
extends Table
implements DropHandler, ItemClickListener {

	protected BeanItemContainer<UserStory> storyContainer = new BeanItemContainer<UserStory>(UserStory.class);
	protected TinyScrumApplication application;

	public UserStoryTable(TinyScrumApplication application) {
		this.application = application;
		
		setContainerDataSource(storyContainer);
		setSelectable(true);
		setSizeFull();
		
		setVisibleColumns(new String[]{"id", "title", "owner"});
		setColumnExpandRatio("id", 1);
		setColumnExpandRatio("title", 5);
		setColumnExpandRatio("owner", 3);
		setSortDisabled(true);
		
		setDragMode(TableDragMode.ROW);
		setDropHandler(this);
		addListener((ItemClickListener)this);
	}
	
	@Override
	public void drop(DragAndDropEvent event) {
        DataBoundTransferable t = (DataBoundTransferable)event.getTransferable();
        if (!UserStoryTable.class.isAssignableFrom(t.getSourceComponent().getClass()))
            return;

        UserStoryTable source = (UserStoryTable)t.getSourceComponent();
        Object sourceItemId = t.getItemId();
        UserStory sourceItem = (UserStory)sourceItemId;

        AbstractSelectTargetDetails dropData = (AbstractSelectTargetDetails)event.getTargetDetails();
        Object targetItemId = dropData.getItemIdOver();

        source.removeItem(sourceItemId);

        int newSequenceNumber;
        if (targetItemId != null) {
            switch (dropData.getDropLocation()) {
            case BOTTOM:
                storyContainer.addItemAfter(targetItemId, sourceItem);
                newSequenceNumber = ((UserStory)targetItemId).getSequenceNumber() + 1;
                break;
                
            case MIDDLE:
            case TOP:
                Object prevItemId = storyContainer.prevItemId(targetItemId);
                storyContainer.addItemAfter(prevItemId, sourceItem);
                newSequenceNumber = ((UserStory)targetItemId).getSequenceNumber();
                break;
                
            default:
            	throw new RuntimeException("unknown drop location");
            }
        } else {
        	// at the end
            UserStory lastStory = storyContainer.lastItemId();
            if (lastStory != null)
            	newSequenceNumber = lastStory.getSequenceNumber() + 1;
            else
            	newSequenceNumber = 1;
            storyContainer.addItem(sourceItem);
        }
        
        storyDropped(sourceItem, newSequenceNumber);
	}
	
	@Transactional
	protected void storyDropped(UserStory story, int newSequenceNumber) {
		EntityManager em = UserStory.entityManager();
		
		// Umnummerieren
		Query q1 = em.createQuery("UPDATE UserStory SET sequenceNumber = sequenceNumber - 1 WHERE sequenceNumber > ?");
		q1.setParameter(1, story.getSequenceNumber());
		q1.executeUpdate();
		
		if (newSequenceNumber > story.getSequenceNumber())
			newSequenceNumber--;
		
		Query q2 = em.createQuery("UPDATE UserStory SET sequenceNumber = sequenceNumber + 1 WHERE sequenceNumber >= ?");
		q2.setParameter(1, newSequenceNumber);
		q2.executeUpdate();
		
		story.setSequenceNumber(newSequenceNumber);
		updateStoryParent(story);
		em.merge(story);
		
		// alle möglichen Sequenzen neu laden
		application.getMainView().refreshContent();
	}

	protected void updateStoryParent(UserStory story) {
	}
	
	@Override
	public void itemClick(ItemClickEvent event) {
		if (event.isDoubleClick()) {
			UserStory story = (UserStory)event.getItemId();
			if (story != null) {
				UserStoryWindow w = new UserStoryWindow(application, story);
				application.getMainWindow().addWindow(w);
			}
		}
	}

	@Override
	public AcceptCriterion getAcceptCriterion() {
		return AcceptItem.ALL;
	}

	private static final long serialVersionUID = 1L;

}