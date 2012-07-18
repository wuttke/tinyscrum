package eu.wuttke.tinyscrum.domain;

import java.io.Serializable;

/**
 * Task and parent user story.
 * For the dashboard.
 * @author Matthias Wuttke
 */
public class TaskAndStory
implements Serializable {

	/**
	 * Task
	 */
	private Task task;
	
	/**
	 * Parent user story
	 */
	private UserStory story;
	
	public TaskAndStory(Task task, UserStory userStory) {
		setTask(task);
		setStory(userStory);
	}

	public String getIterationName() {
		if (getStory().getIteration() == null)
			return "Backlog";
		else
			return getStory().getIteration().getName();
	}
	
	public String getStoryTitle() {
		return getStory().getTitle();
	}
	
	public String getTaskName() {
		return getTask().getName();
	}
	
	public String getTaskDeveloper() {
		return getTask().getDeveloper();
	}
	
	public String getTaskTester() {
		return getTask().getTester();
	}
	
	public TaskStatus getTaskStatus() {
		return getTask().getStatus();
	}
	
	public Double getTaskEstimate() {
		return getTask().getEstimate();
	}
	
	public Task getTask() {
		return task;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public UserStory getStory() {
		return story;
	}
	
	public void setStory(UserStory story) {
		this.story = story;
	}
	
	private static final long serialVersionUID = 1L;
	
}
