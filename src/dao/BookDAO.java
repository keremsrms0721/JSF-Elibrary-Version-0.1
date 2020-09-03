package dao;


import java.sql.SQLException;
import java.util.List;

import model.BookBean;

public interface BookDAO {
	
	public List<BookBean> listAllBooks() throws ClassNotFoundException, SQLException;
	public boolean bookAdd(String bookName,String bookEditor) throws ClassNotFoundException, SQLException;
	public String bookDelete(int bookId) throws ClassNotFoundException, SQLException;
	public String findBookWithId(int bookId) throws ClassNotFoundException, SQLException;
	public String editBook(BookBean b) throws ClassNotFoundException, SQLException;
	public List<BookBean> listAllBooksForUsers() throws ClassNotFoundException, SQLException;
	public List<BookBean> listMyBooks() throws ClassNotFoundException, SQLException;
}
