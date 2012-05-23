package eu.wuttke.tinyscrum.ui;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

import eu.wuttke.tinyscrum.domain.ScrumUser;
import eu.wuttke.tinyscrum.logic.UserManager;
import eu.wuttke.tinyscrum.ui.misc.LoginCompletedListener;

@Configurable(autowire=Autowire.BY_NAME)
public class LoginWindow 
extends Window {

	private TinyScrumApplication application;
	private LoginForm loginForm;
	private LoginCompletedListener listener;
	
	public LoginWindow(TinyScrumApplication application, LoginCompletedListener listener) {
		this.application = application;
		this.listener = listener;
		
		setCaption("TinyScrum Login");
		setClosable(false);
		setResizable(false);
		setWidth("300px");
		setModal(true);
		center();
		
		loginForm = new LoginForm();
		loginForm.addListener(new LoginListener() {
			private static final long serialVersionUID = 1L;
			public void onLogin(LoginEvent event) {
				doLogin(event.getLoginParameter("username"), event.getLoginParameter("password"));
			}
		});
		
		Panel loginPanel = new Panel("Login");
		loginPanel.setWidth("100%");
		loginPanel.addComponent(loginForm);
		
		addComponent(loginPanel);
	}
	
	public void doLogin(String userName, String password) {
		ScrumUser user = userManager.loginUser(userName, password);
		if (user != null) {
			application.setCurrentUser(user);
			application.getMainWindow().removeWindow(this);
			if (listener != null)
				listener.loginCompleted(user);
		} else {
			Notification n = new Notification("Invalid Login Credentials", "Your user name and/or password is incorrect.", Notification.TYPE_ERROR_MESSAGE);
			application.getMainWindow().showNotification(n);
		}
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	private UserManager userManager;

	private static final long serialVersionUID = 1L;

}
