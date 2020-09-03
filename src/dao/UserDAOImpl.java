package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import level.Level;
import utility.ConnectionUtility;

public class UserDAOImpl implements UserDAO {

	public boolean userValidate(String username, String password) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM user WHERE userName = ? and userPassword = ?";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement statement = connect.prepareStatement(sql);
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet set = statement.executeQuery();
		if (set.next()) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("level", Level.USER);
			session.setAttribute("userId",set.getInt("userId"));
			statement.close();
			connect.close();
			return true;
		} else {
			statement.close();
			connect.close();
			return false;
		}
	}
	
	public boolean userAdd(String userName,String userPassword) throws ClassNotFoundException, SQLException
	{
		String bookSql = "INSERT INTO user (userName,userPassword) VALUES (?,?)";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement ps = connect.prepareStatement(bookSql);
		ps.setString(1, userName);
		ps.setString(2, userPassword);
		int i = ps.executeUpdate();
		ps.close();
		connect.close();
		return i>0 ? true : false;
	}

	@Override
	public String takeIt(int bookId,int userId) throws ClassNotFoundException, SQLException {
		String bookSql = "UPDATE book SET userId = ? WHERE bookId = ?";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement statement = connect.prepareStatement(bookSql);
		statement.setInt(1,userId);
		statement.setInt(2, bookId);
		statement.executeUpdate();
		statement.close();
		connect.close();
		return "userBooks.xhtml?faces-redirect=true";
	}

	@Override
	public String dropBack(int bookId) throws SQLException, ClassNotFoundException {
		String bookSql = "UPDATE book SET userId = ? WHERE bookId = ?";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement ps = connect.prepareStatement(bookSql);
		ps.setString(1, null);
		ps.setInt(2, bookId);
		ps.executeUpdate();
		return "userBooks.xhtml?faces-redirect=true";
	}
	

}
