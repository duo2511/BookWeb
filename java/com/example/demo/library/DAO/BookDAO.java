package com.example.demo.library.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.CloseableThreadContext.Instance;

import com.example.demo.library.Book;
import com.example.demo.library.Order;

public class BookDAO extends DAO {

	// get all book
	public List<Book> getAllBooks() {
		String sql = "select * from book2";
		List<Book> books = new ArrayList<Book>();

		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String description = resultSet.getString("description");
				String releaseDate = resultSet.getString("releaseDate");
				int numberOfPages = resultSet.getInt("numberOfPages");
				String category = resultSet.getString("category");
				int price = resultSet.getInt("price");
				String imageLink = resultSet.getString("imageLink");

				books.add(new Book(id, title, author, description, releaseDate, numberOfPages, category, price,
						imageLink));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	// create a book
	public int insertBook(Book book) {
		String sql = "INSERT INTO jdbc_demo.book2(title,author,description,releaseDate,numberOfPages,category,price) VALUES(?,?,?,?,?,?,?)";
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, book.getTitle());
			preparedStatement.setString(2, book.getAuthor());
			preparedStatement.setString(3, book.getDescription());
			preparedStatement.setString(4, book.getReleaseDate());
			preparedStatement.setInt(5, book.getNumberOfPages());
			preparedStatement.setString(6, book.getCategory());
			preparedStatement.setInt(7, book.getPrice());
			preparedStatement.executeUpdate();
			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				book.setId(generatedKeys.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return book.getId();
	}

	// read a book

	public Book getBookByID(int idbook) {
		Book book = new Book();
		final String sql = "SELECT * FROM jdbc_demo.book2 WHERE id=?";
		try {
			Connection connection = this.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idbook);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String description = resultSet.getString("description");
				String release = resultSet.getString("releaseDate");
				int pageNumber = resultSet.getInt("numberOfPages");
				String category = resultSet.getString("category");
				int price = resultSet.getInt("price");
				book.setTitle(title);
				book.setAuthor(author);
				book.setDescription(description);
				book.setReleaseDate(release);
				book.setNumberOfPages(pageNumber);
				book.setCategory(category);
				book.setPrice(price);
			}
			book.setId(idbook);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}

	public Book getBookByTitle(String title) {
		Book book = new Book();
		final String sql = "SELECT * FROM jdbc_demo.book2 WHERE title=?";
		try {
			Connection connection = this.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, title);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String author = resultSet.getString("author");
				String description = resultSet.getString("description");
				String release = resultSet.getString("releaseDate");
				int pageNumber = resultSet.getInt("numberOfPages");
				String category = resultSet.getString("category");
				int price = resultSet.getInt("price");
				book.setId(id);
				book.setAuthor(author);
				book.setDescription(description);
				book.setReleaseDate(release);
				book.setNumberOfPages(pageNumber);
				book.setCategory(category);
				book.setPrice(price);
			}
			book.setTitle(title);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}

	// update a book
	public int updateBook(Book book, int id) {
		String sql = "UPDATE jdbc_demo.book2 SET  title=?,author=?,description =?,releaseDate=?,numberOfPages =?,category=?,price=? WHERE id = ?";

		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, book.getTitle());
			preparedStatement.setString(2, book.getAuthor());
			preparedStatement.setString(3, book.getDescription());
			preparedStatement.setString(4, book.getReleaseDate());
			preparedStatement.setInt(5, book.getNumberOfPages());
			preparedStatement.setString(6, book.getCategory());
			preparedStatement.setInt(7, book.getPrice());
			preparedStatement.setInt(8, Integer.valueOf(id));
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book.getId();
	}

	// delete a book
	public boolean deleteBook(int id) {
		String sql = "delete from jdbc_demo.book2 where id= ?";
		boolean result = false;

		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.valueOf(id));
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateImagePath(int id, String path) {
		String sql = "UPDATE jdbc_demo.book2 SET imageLink = ? WHERE id = ?";
		boolean result = false;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, path);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getImagePath(int idbook) throws SQLException {
		String sql = "select imageLink from jdbc_demo.book2 where id=?";
		String res = "";
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idbook);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				System.out.println(res);
				res = rs.getString("imageLink");
			}
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.print(res);
		return res;
	}

	public boolean isValidTitle(String title) {
		String sql = "SELECT title FROM jdbc_demo.book2 WHERE title=?";
		boolean res = true;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, title);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				boolean duma = Objects.equals(rs.getString("title"), title);
				if (duma) {
					res = false;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	// create a oder
	public boolean insertOrderBook(Order ob) {
		String sql = "INSERT INTO jdbc_demo.order(title,orderuser,amount,orderTime) VALUES(?,?,?,?)";

		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, ob.getTitle());
			preparedStatement.setString(2, ob.getOrderuser());
			preparedStatement.setInt(3, ob.getAmount());
			preparedStatement.setString(4, ob.getOrderTime());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	// get list order by user name
	public List<Order> getOrder(String username) {

		String sql = "select * from jdbc_demo.order where orderuser=?";
		List<Order> orders = new ArrayList<Order>();
		try {
			Connection connection = this.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String orderuser = resultSet.getString("orderuser");
				int amount = resultSet.getInt("amount");
				String orderTime = resultSet.getString("orderTime");

				orders.add(new Order(id, title, orderuser, amount, orderTime));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	// delete a order

	public boolean deleteOrder(int id) {
		String sql = "delete from jdbc_demo.order where id= ?";
		boolean result = false;

		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.valueOf(id));
			preparedStatement.executeUpdate();
			preparedStatement.close();
			connection.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// get a order
	public Order getOrderByID(int id) {
		Order order = new Order();
		final String sql = "SELECT * FROM jdbc_demo.order WHERE id=?";
		try {
			Connection connection = this.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String title = resultSet.getString("title");
				String orderuser = resultSet.getString("orderuser");
				int amount = resultSet.getInt("amount");
				String orderTime = resultSet.getString("orderTime");
				order.setTitle(title);
				order.setOrderuser(orderuser);
				order.setAmount(amount);
				order.setOrderTime(orderTime);
			}
			order.setId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}
}
