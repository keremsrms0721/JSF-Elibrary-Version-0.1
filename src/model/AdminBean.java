package model;

import java.sql.SQLException;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import dao.AdminDAO;
import dao.AdminDAOImpl;
import level.Level;

@ManagedBean
@SessionScoped
public class AdminBean {

	private String adminUsername;
	private String adminPassword;

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String adminLogin() throws ClassNotFoundException, SQLException {
		AdminDAO dao = new AdminDAOImpl();
		boolean result = dao.adminValidate(getAdminUsername(), getAdminPassword());
		if (result)
			return "response.xhtml?faces-redirect=true";
		return "index.xhtml";
	}

	public void isAdmin(ComponentSystemEvent event) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		if (session.getAttribute("level") == null || (!session.getAttribute("level").equals(Level.ADMIN))) {
			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication()
					.getNavigationHandler();
			nav.performNavigation("access-denied.xhtml?faces-redirect=true");
		}
	}

	public String adminLogOut() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "homePage.xhtml?faces-redirect=true";
	}
}