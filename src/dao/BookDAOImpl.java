package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.BookBean;
import utility.ConnectionUtility;

public class BookDAOImpl implements BookDAO{

	@Override
	public List<BookBean> listAllBooks() throws ClassNotFoundException, SQLException {
		List<BookBean> books = new ArrayList<>();
		String bookSql = "SELECT * FROM book";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement ps = connect.prepareStatement(bookSql);
		BookBean b = null;
		ResultSet set = ps.executeQuery();
		while(set.next())
		{
			String bookName = set.getString("bookName");
			String bookEditor = set.getString("bookEditor");
			int bookId = set.getInt("bookId");
			b = new BookBean();
			b.setBookName(bookName);
			b.setBookEditor(bookEditor);
			b.setBookId(bookId);
			books.add(b);
		}
		set.close();
		ps.close();
		connect.close();
		return books;
	}
	
	@Override
	public List<BookBean> listMyBooks() throws ClassNotFoundException, SQLException {
		List<BookBean> books = new ArrayList<>();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		int userId = (int) session.getAttribute("userId");
		String bookSql = "SELECT * FROM book WHERE userId = ?";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement ps = connect.prepareStatement(bookSql);
		ps.setInt(1, userId);
		BookBean b = null;
		ResultSet set = ps.executeQuery();
		while(set.next())
		{
			String bookName = set.getString("bookName");
			String bookEditor = set.getString("bookEditor");
			int bookId = set.getInt("bookId");
			b = new BookBean();
			b.setBookName(bookName);
			b.setBookEditor(bookEditor);
			b.setBookId(bookId);
			books.add(b);
		}
		set.close();
		ps.close();
		connect.close();
		return books;
	}
	
	@Override
	public List<BookBean> listAllBooksForUsers() throws ClassNotFoundException, SQLException {
		List<BookBean> books = new ArrayList<>();
		String bookSql = "SELECT * FROM book WHERE userId is null";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement ps = connect.prepareStatement(bookSql);
		BookBean b = null;
		ResultSet set = ps.executeQuery();
		while(set.next())
		{
			String bookName = set.getString("bookName");
			String bookEditor = set.getString("bookEditor");
			int bookId = set.getInt("bookId");
			b = new BookBean();
			b.setBookName(bookName);
			b.setBookEditor(bookEditor);
			b.setBookId(bookId);
			books.add(b);
		}
		set.close();
		ps.close();
		connect.close();
		return books;
	}
	
	
	public boolean bookAdd(String bookName,String bookEditor) throws ClassNotFoundException, SQLException
	{
		String bookSql = "INSERT INTO book (bookName,bookEditor) VALUES (?,?)";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement ps = connect.prepareStatement(bookSql);
		ps.setString(1, bookName);
		ps.setString(2, bookEditor);
		int i = ps.executeUpdate();
		ps.close();
		connect.close();
		return i>0 ? true : false;
	}
	
	public String bookDelete(int bookId) throws ClassNotFoundException, SQLException
	{
		String bookSql = "DELETE FROM book WHERE bookId = ?";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement ps = connect.prepareStatement(bookSql);
		ps.setInt(1, bookId);
		ps.executeUpdate();
		ps.close();
		connect.close();
		return "response.xhtml?faces-redirect=true";
	}
	
	public String findBookWithId(int bookId) throws ClassNotFoundException, SQLException
	{
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String bookSql = "Select * from book WHERE bookId = ?";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement ps = connect.prepareStatement(bookSql);
		ps.setInt(1, bookId);
		ResultSet set = ps.executeQuery();
		if(set.next())
		{
			BookBean b = new BookBean();
			b.setBookId(set.getInt("bookId"));
			b.setBookName(set.getString("bookName"));
			b.setBookEditor(set.getString("bookEditor"));
			session.setAttribute("foundBook", b);
		}
		ps.close();
		connect.close();
		return "update.xhtml?faces-redirect=true";
	}
	
	public String editBook(BookBean bean) throws ClassNotFoundException, SQLException
	{
		String bookSql = "UPDATE book SET bookName = ? , bookEditor = ? WHERE bookId = ?";
		Connection connect = ConnectionUtility.getConnection();
		PreparedStatement ps = connect.prepareStatement(bookSql);
		ps.setString(1, bean.getBookName());
		ps.setString(2, bean.getBookEditor());
		ps.setInt(3, bean.getBookId());
		ps.executeUpdate();
		ps.close();
		connect.close();
		return "response.xhtml?faces-redirect=true";
	}

}
