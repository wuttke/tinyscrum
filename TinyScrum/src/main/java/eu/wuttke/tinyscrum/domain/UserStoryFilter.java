package eu.wuttke.tinyscrum.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class UserStoryFilter {

	private Project project;
	
	private String titleContains;
	private UserStoryStatus statusEquals;

	private boolean filterOwner;
	private String ownerEquals;

	boolean filterIteration;
	private Iteration iterationEquals; 
	
	private boolean filterFeature;
	private ProjectFeature featureEquals;
	
	private boolean filterRelease;
	private ProjectRelease releaseEquals;

}
