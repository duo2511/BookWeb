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

import com.example.demo.library.Book;
import com.example.demo.library.User;

public class UserDAO extends DAO {

	public boolean checkLogin(User user) {
		String sql = "select * from user";
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
					return true;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	// get a user
	public User getUser(String username1) {
		String sql = "select * from user where username=?";
		 User u= new User();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username1);
            ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String position = resultSet.getString("position");
                 u = new User(username,password,name,email,position);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}

	public String getRole(User user) {
		String sql = "select * from user where username= ?";
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				user.setPosition(rs.getString("position"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user.getPosition();
	}

	public boolean insertUser(User user) {
		String sql = "INSERT INTO jdbc_demo.user(name,email,username,password,position) VALUES(?,?,?,?,?)";
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getPosition());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean isValid(String username) {
		String sql = "SELECT username FROM jdbc_demo.user WHERE username=?";
		boolean res = true;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				boolean duma = Objects.equals(rs.getString("username"), username);
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

}