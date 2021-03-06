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
	boolean filterIterationNotNull;
	
	private boolean filterFeature;
	private ProjectFeature featureEquals;
	
	private boolean filterRelease;
	private ProjectRelease releaseEquals;
	
	private boolean filterCustomer;
	private Customer customerEquals;
	
	private boolean filterCustomerProject;
	private CustomerProject customerProjectEquals;
	
}
