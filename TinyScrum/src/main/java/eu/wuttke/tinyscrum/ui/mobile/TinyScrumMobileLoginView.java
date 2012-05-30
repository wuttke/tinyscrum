package eu.wuttke.tinyscrum.ui.mobile;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import eu.wuttke.tinyscrum.domain.ScrumUser;
import eu.wuttke.tinyscrum.logic.UserManager;

@Configurable(autowire=Autowire.BY_NAME)
public class TinyScrumMobileLoginView 
extends NavigationView
implements ClickListener {

	private TextField tfLogin;
	private PasswordField tfPassword;
	
	public TinyScrumMobileLoginView() {
		setCaption("TinyScrum Login");
	}
	
	@Override
	public void attach() {
		super.attach();
		
		tfLogin = new TextField();
		tfLogin.setCaption("Login");
		
		tfPassword = new PasswordField();
		tfPassword.setCaption("Password");
		
		Button btnLogin = new Button("Login");
		btnLogin.addListener(this);
		
		VerticalComponentGroup group = new VerticalComponentGroup();
		group.setCaption("User Credentials");
		group.addComponent(tfLogin);
		group.addComponent(tfPassword);
		group.addComponent(btnLogin);
		
		CssLayout layout = new CssLayout();
		layout.setWidth("100%");
		layout.addComponent(group);
		setContent(layout);
		
		tfLogin.focus();
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		ScrumUser user = userManager.loginUser((String)tfLogin.getValue(), (String)tfPassword.getValue());
		if (user != null) {
			TinyScrumMobileApplication app = (TinyScrumMobileApplication)getApplication();
			app.setUser(user);
			app.setUserName(user.getUserName());
			app.getMainWindow().setContent(app.getMainView());
		} else {
			tfPassword.setValue(null);
			tfPassword.focus();
		}
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	private UserManager userManager;
	
	private static final long serialVersionUID = 1L;

	
}
