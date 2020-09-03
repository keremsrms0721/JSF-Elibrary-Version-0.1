package model;

import java.sql.SQLException;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dao.UserDAOImpl;
import level.Level;

@ManagedBean
@SessionScoped
public class UserBean {

	private String userName;
	private String userPassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String userLogin() throws ClassNotFoundException, SQLException {
		UserDAO dao = new UserDAOImpl();
		boolean result = dao.userValidate(getUserName(), getUserPassword());
		if (result)
			return "allBooks.xhtml?faces-redirect=true";
		return "user.xhtml";
	}

	public String addUser() throws ClassNotFoundException, SQLException {
		UserDAO dao = new UserDAOImpl();
		boolean result = dao.userAdd(userName, userPassword);
		return result ? "user.xhtml?faces-redirect=true" : "userRegister.xhtml?faces-redirect=true";
	}

	public void isUser(ComponentSystemEvent event) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		if (session.getAttribute("level") == null || (!session.getAttribute("level").equals(Level.USER))) {
			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication()
					.getNavigationHandler();
			nav.performNavigation("user.xhtml?faces-redirect=true");
		}
	}
	
	public String takeIts() throws ClassNotFoundException, SQLException
	{
	    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    int bookId = Integer.parseInt(ec.getRequestParameterMap().get("bookIds"));
	    int userId = Integer.parseInt(ec.getRequestParameterMap().get("userIds"));
	    UserDAO dao = new UserDAOImpl();
	    String s = dao.takeIt(bookId, userId);
	    return s;
	}
	
	public String dropBack() throws SQLException,ClassNotFoundException{
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		int bookId = Integer.parseInt(ec.getRequestParameterMap().get("bookIds"));
		UserDAO dao = new UserDAOImpl();
		String s = dao.dropBack(bookId);
		return s;
	}
	
	public String userLogOut() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "homePage.xhtml?faces-redirect=true";
	}
}