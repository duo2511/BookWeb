package com.example.demo.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.library.DAO.BookDAO;
import com.example.demo.library.DAO.UserDAO;

@Controller
public class UserController {
	private UserDAO userDAO = new UserDAO();
	private BookDAO bookDAO = new BookDAO();

	@RequestMapping("/login")
	public String showLogin() {

		return "login";
	}

	@RequestMapping("/register")
	public String showRegister() {

		return "register";
	}

	@PostMapping("/checklogin")
	public String checkLogin(User user, Model model) {
		List<Book> books = bookDAO.getAllBooks();
		boolean res = userDAO.checkLogin(user);
		User u= userDAO.getUser(user.getUsername());
		if (res && userDAO.getRole(user).equals("admin")) {
			model.addAttribute("user", u);
			model.addAttribute("books", books);
			return "books-home";
		} else if (res && userDAO.getRole(user).equals("user")) {
			model.addAttribute("user",u);
			model.addAttribute("books", books);
			return "user-book-home";
		}

		else {
			model.addAttribute("invalid", true);
			return "login"; }
	}

	@PostMapping("/sign-in")
	public String SignIn(@ModelAttribute(name = "user") User user, Model model) {

		boolean res = userDAO.isValid(user.getUsername());

		if (res) {
			userDAO.insertUser(user);
			return "login";
		}

		else {
			model.addAttribute("invalid", true);
			return "register";
		}
	}

}