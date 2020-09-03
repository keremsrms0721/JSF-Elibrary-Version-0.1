package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import level.Level;
import utility.ConnectionUtility;

public class AdminDAOImpl implements AdminDAO {

	public boolean adminValidate(String username, String password) throws ClassNotFoundException, SQLException {
		String sql = "SELECT adminUsername,adminPassword FROM admin WHERE adminUsername = ? and adminPassword = ?";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement statement = connect.prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet set = statement.executeQuery();
		if (set.next()) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("level", Level.ADMIN);
			statement.close();
			connect.close();
			return true;
		} else {
			return false;
		}
	}
	
	

}
