package model;

import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import dao.BookDAO;
import dao.BookDAOImpl;

@ManagedBean
@RequestScoped
public class BookBean {

	private int bookId;
	private String bookName;
	private String bookEditor;
	private List<BookBean> listAllBooks;
	private List<BookBean> listAllBooksForUsers;
	private List<BookBean> listMyBooks;
	
	
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookEditor() {
		return bookEditor;
	}

	public void setBookEditor(String bookEditor) {
		this.bookEditor = bookEditor;
	}
	
	public void setListAllBooks(List<BookBean> listAllBooks) {
		this.listAllBooks = listAllBooks;
	}

	public List<BookBean> getListAllBooks() throws ClassNotFoundException, SQLException {
		BookDAO books = new BookDAOImpl();
		
		return books.listAllBooks();
	}
	
	

	

	public List<BookBean> getListMyBooks() throws ClassNotFoundException, SQLException {
		BookDAO books = new BookDAOImpl();
		return books.listMyBooks();
	}

	public void setListMyBooks(List<BookBean> listMyBooks) {
		this.listMyBooks = listMyBooks;
	}

	public List<BookBean> getListAllBooksForUsers() throws ClassNotFoundException, SQLException {
		BookDAO books = new BookDAOImpl();
		return books.listAllBooksForUsers();
	}

	public void setListAllBooksForUsers(List<BookBean> listAllBooksForUsers) {
		this.listAllBooksForUsers = listAllBooksForUsers;
	}

	public String addBook() throws ClassNotFoundException, SQLException
	{
		BookDAO books = new BookDAOImpl();
		boolean result = books.bookAdd(bookName,bookEditor);
		return result ? "response.xhtml?faces-redirect=true" : "addBook.xhtml";
	}
	
	public String delete(int id) throws ClassNotFoundException, SQLException
	{
		BookDAO books = new BookDAOImpl();
		String result = books.bookDelete(id);
		return result;		
	}
	
	public String update(int id) throws ClassNotFoundException,SQLException
	{
		BookDAO books = new BookDAOImpl();
		String result = books.findBookWithId(id);
		return result;
	}
	
	public String edit(BookBean b) throws ClassNotFoundException, SQLException
	{
		BookDAO books = new BookDAOImpl();
		String result = books.editBook(b);
		return result;
	}
	
}
