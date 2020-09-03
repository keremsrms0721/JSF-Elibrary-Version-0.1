package dao;

import java.sql.SQLException;


public interface AdminDAO {
	
	public boolean adminValidate(String username,String password) throws ClassNotFoundException, SQLException;
}
