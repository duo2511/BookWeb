package com.example.demo.library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.library.DAO.BookDAO;
import com.example.demo.library.DAO.CommentDAO;
import com.example.demo.library.DAO.UserDAO;

import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;  
@Controller
public class bookController {
	private BookDAO bookDAO = new BookDAO();
	private UserDAO userDAO = new UserDAO();
	private CommentDAO commentDAO = new CommentDAO();

	// pages before login
	@GetMapping("/books")
	public String getBooks(Model model) throws IOException {
		List<Book> books = bookDAO.getAllBooks();
		model.addAttribute("books", books);
		return "books";
	}

	// book home page
	@GetMapping("/books-home")

	public String getBookHomes(@ModelAttribute("user") User user, Model model) throws IOException {

		List<Book> books = bookDAO.getAllBooks();
		model.addAttribute("books", books);
		model.addAttribute("user", user);
		return "books-home";
	}

	// user book home page
	@GetMapping("/user-book-home/{iduser}")

	public String getUserBookHomes( Model model,@PathVariable String iduser) throws IOException {
 
		
		User user = userDAO.getUser(iduser);
		List<Book> books = bookDAO.getAllBooks();
		model.addAttribute("books", books);
		model.addAttribute("user", user);
		return "user-book-home";
	}

	// view detail page
	@GetMapping("/book-detail/{id}")
	public String getBook(Model model, @PathVariable int id) {
		model.addAttribute("id", id);
		Book book = bookDAO.getBookByID(Integer.valueOf(id));

		model.addAttribute("book", book);
		return "book-view";
	}

	@GetMapping("book-user-detail/{id}/{username}")
	public String getUserBook(Model model, @PathVariable int id, @PathVariable String username) {
		model.addAttribute("id", id);
		Book book = bookDAO.getBookByID(Integer.valueOf(id));

		model.addAttribute("username", username);
		User user = userDAO.getUser(username);

		List<Comment> comments = commentDAO.getCommentsByBook(book.getTitle());

		model.addAttribute("book", book);
		model.addAttribute("user", user);
		model.addAttribute("comments", comments);
		return "book-user-view";
	}

	@GetMapping("book-order/{id}/{username}")
	public String orderBook(Model model, @PathVariable int id, @PathVariable String username) {
		model.addAttribute("id", id);
		Book book = bookDAO.getBookByID(Integer.valueOf(id));

		model.addAttribute("username", username);
		User user = userDAO.getUser(username);

		model.addAttribute("book", book);
		model.addAttribute("user", user);
		return "book-order";
	}

	// new book page
	@GetMapping("book/{id}")
	public String getSavedBook(Model model, @PathVariable int id) {
		model.addAttribute("id", id);
		Book book = bookDAO.getBookByID(Integer.valueOf(id));

		model.addAttribute("book", book);
		return "book-save";
	}

	// edit book page
	@GetMapping("book-edit/{id}")
	public String getEditdBook(Model model, @PathVariable int id) {
		model.addAttribute("id", id);
		Book book = bookDAO.getBookByID(Integer.valueOf(id));

		model.addAttribute("book", book);
		return "book-edit";
	}

	// update book
	@PutMapping("/book/update/{id}")
	public String updateBook(Model model, @RequestParam @Nullable String title, @RequestParam @Nullable String author,
			@RequestParam @Nullable String description, @RequestParam @Nullable String releaseDate,
			@RequestParam @Nullable int numberOfPages, @RequestParam @Nullable String category,
			@RequestParam @Nullable int price, @RequestParam("imageLink") MultipartFile multipartFile,
			@PathVariable int id) throws SQLException {
		final String UPLOAD_DIRECTORY_BOOK = System.getProperty("user.dir") + "/resource/books";
		Book book = new Book(id, title, author, description, releaseDate, numberOfPages, category, price,
				multipartFile.getOriginalFilename());

		int newBookID = bookDAO.updateBook(book, id);
		book = null;
		book = bookDAO.getBookByID(newBookID);
		String fileExtension = null;
		fileExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
		System.out.print("ok" + fileExtension);
		if (fileExtension != null) {
			try {
				String filesavename = "" + newBookID + "." + fileExtension;
				Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY_BOOK, (newBookID + "." + fileExtension));
				Files.write(fileNameAndPath, multipartFile.getBytes());
				bookDAO.updateImagePath(newBookID, filesavename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute(book);
		return "redirect:/books-home";

	}

	@RequestMapping(path = "/book/delete/{id}", method = RequestMethod.GET)
	public String deleteBook(Book book, @PathVariable int id) throws SQLException, IOException {

		String UPLOAD_DIRECTORY_BOOK = System.getProperty("user.dir") + "/resource/books";
		Path path = Paths.get(UPLOAD_DIRECTORY_BOOK);
		String path1 = bookDAO.getImagePath(id);
		if (Files.exists(Path.of(path + "/" + path1))) {
			Files.delete(Path.of(path + "/" + path1));
		}

		bookDAO.deleteBook(id);

		return ("redirect:/books-home");
	}

	@RequestMapping(path = "/book-img", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Resource> image(@RequestParam int id) throws IOException, SQLException {
		String path = bookDAO.getImagePath(id);
		final String UPLOAD_DIRECTORY_BOOK = System.getProperty("user.dir") + "/resource/books";

		final ByteArrayResource inputStream = new ByteArrayResource(
				Files.readAllBytes(Paths.get(UPLOAD_DIRECTORY_BOOK + "/" + path)));
		MediaType type = null;
		String fileExtension = StringUtils.getFilenameExtension(path);
		if (Objects.equals(fileExtension, "jpeg")) {
			type = MediaType.IMAGE_JPEG;
		} else if (Objects.equals(fileExtension, "png")) {
			type = MediaType.IMAGE_PNG;
		} else if (Objects.equals(fileExtension, "gif")) {
			type = MediaType.IMAGE_GIF;
		}
		assert type != null;
		return ResponseEntity.status(HttpStatus.OK).contentLength(inputStream.contentLength()).contentType(type)
				.body(inputStream);
	}

	@RequestMapping(path = "/add-new-book", method = RequestMethod.POST)
	public String addBook(Model model, @RequestParam @Nullable String title, @RequestParam @Nullable String author,
			@RequestParam @Nullable String description, @RequestParam @Nullable String releaseDate,
			@RequestParam @Nullable int numberOfPages, @RequestParam @Nullable String category,
			@RequestParam @Nullable int price, @RequestParam("imageLink") MultipartFile multipartFile)
			throws SQLException {
		System.out.print(multipartFile.getOriginalFilename());
		final String UPLOAD_DIRECTORY_BOOK = System.getProperty("user.dir") + "/resource/books";
		Book book = new Book(0, title, author, description, releaseDate, numberOfPages, category, price,
				multipartFile.getOriginalFilename());
		if (bookDAO.isValidTitle(title) == true) {
			int newBookID = bookDAO.insertBook(book);
			book = null;
			book = bookDAO.getBookByID(newBookID);
			String fileExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
			try {
				String filesavename = "" + newBookID + "." + fileExtension;
				Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY_BOOK, (newBookID + "." + fileExtension));
				Files.write(fileNameAndPath, multipartFile.getBytes());
				bookDAO.updateImagePath(newBookID, filesavename);
			} catch (IOException e) {
				e.printStackTrace();
			}
			model.addAttribute(book);
			return "redirect:/books-home";
		} else {
			model.addAttribute("invalid", true);
			return "book-save";
		}
	}

	@PostMapping("/add-order-book/{username}")
	public String addOrderBook(Model model, @RequestParam String title, @RequestParam String orderuser,
			@RequestParam int amount, @PathVariable String username) {
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");  
        String orderTime = dateFormat.format(date);  
        
		Order od = new Order(0, title, orderuser, amount,orderTime);

		if (amount <= 0) {

			User user = userDAO.getUser(username);
			Book book = bookDAO.getBookByTitle(title);
			model.addAttribute("user", user);
			model.addAttribute("book", book);
			model.addAttribute("invalid", true);
			return "book-order";
		}
		
		bookDAO.insertOrderBook(od);
		User u = userDAO.getUser(username);
		List<Book> books = bookDAO.getAllBooks();
		model.addAttribute("user", u);
		model.addAttribute("books", books);
		return "user-book-home";
	}

	@GetMapping("/book-cart/{username}")
	public String addOrderBook(Model model, @PathVariable String username) {

		List<Order> orders = bookDAO.getOrder(username);
		User u = userDAO.getUser(username);
		model.addAttribute("orders", orders);
		model.addAttribute("user", u);
		return "list-book-order";
	}

	@GetMapping("/order/delete/{id}/{username}")
	public String addOrderBook(Model model, @PathVariable int id, @PathVariable String username) {

		bookDAO.deleteOrder(id);

		User u = userDAO.getUser(username);
		List<Order> orders = bookDAO.getOrder(username);
		model.addAttribute("user", u);
		model.addAttribute("orders", orders);
		return "list-book-order";
	}

	@GetMapping("/detail-order/{id}")
	public String getAOrrder(Model model, @PathVariable int id) {

		Order o = bookDAO.getOrderByID(id);
		Book b = bookDAO.getBookByTitle(o.getTitle());
		int totalPrice = o.getAmount() * b.getPrice();

		model.addAttribute("book", b);
		model.addAttribute("order", o);
		model.addAttribute("totalPrice", totalPrice);
		return "detail-order";
	}

	@PostMapping("/comment/{id}/{username}")
	public String createComment(Model model, @PathVariable int id, @PathVariable String username,
			@RequestParam String content, @RequestParam String rating) {
		model.addAttribute("id", id);
		Book book = bookDAO.getBookByID(Integer.valueOf(id));

		model.addAttribute("username", username);
		User user = userDAO.getUser(username);

		Comment comment = new Comment();
		comment.setId(0);
		comment.setIdbook(book.getTitle());
		comment.setIduser(username);
		comment.setContent(content);
		comment.setPoint(Float.parseFloat(rating));
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");  
        String time = dateFormat.format(date);  
        comment.setTime(time);
		commentDAO.insertComment(comment);
		List<Comment> comments = commentDAO.getCommentsByBook(book.getTitle());

		model.addAttribute("book", book);
		model.addAttribute("user", user);
		model.addAttribute("comments", comments);
		return "book-user-view";
	}
}