package eu.wuttke.tinyscrum.ui.admin;

import eu.wuttke.tinyscrum.domain.ScrumUser;
import eu.wuttke.tinyscrum.logic.UserManager;

/**
 * Details handler that scrambles the password.
 * @author Matthias Wuttke
 */
public class ScrumUserAdminDetails
extends AdminBeanDetails {
	
	public ScrumUserAdminDetails(String title, DetailsListener listener, AdminBeanField[] fields) {
		super(title, listener, fields);
	}
	
	@Override
	protected void handleCommit() {
		ScrumUser user = (ScrumUser)bean;
		String prevPasswort = user.getPassword();
		
		form.commit();

		String newPassword = user.getPassword();
		if (!prevPasswort.equals(newPassword))
			user.setPassword(UserManager.makeSha1Hash(newPassword));
		
		listener.doCommitObject(item, item.getBean());
	}
	
	private static final long serialVersionUID = 1L;

}
