package eu.wuttke.tinyscrum.ui.misc;

import eu.wuttke.tinyscrum.domain.ScrumUser;

/**
 * Login successful.
 * @author Matthias Wuttke
 */
public interface LoginCompletedListener {

	public void loginCompleted(ScrumUser newUser);
	
}
