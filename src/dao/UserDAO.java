package dao;

import java.sql.SQLException;


public interface UserDAO {
	
	public boolean userValidate(String username,String password) throws ClassNotFoundException, SQLException;
	public boolean userAdd(String userName,String userPassword) throws ClassNotFoundException, SQLException;
	public String takeIt(int bookId,int userId) throws ClassNotFoundException, SQLException;
	public String dropBack(int bookId) throws SQLException,ClassNotFoundException;
	
}
