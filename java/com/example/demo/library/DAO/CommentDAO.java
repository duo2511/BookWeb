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
import com.example.demo.library.Comment;

public class CommentDAO extends DAO {

	public List<Comment> getCommentsByBook(String idbook) {
		String sql = "select * from comment where idbook=?";
		List<Comment> comments = new ArrayList<Comment>();

		try {
			Connection connection = this.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, idbook);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String iduser = resultSet.getString("iduser");
				String content = resultSet.getString("content");
				float point = resultSet.getFloat("point");
				String time = resultSet.getString("time");
				comments.add(new Comment(id, idbook, iduser, content, point,time));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return comments;
	}
	
	public boolean insertComment(Comment comment) {
		String sql = "INSERT INTO jdbc_demo.comment(idbook,iduser,content,point,time) VALUES(?,?,?,?,?)";
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, comment.getIdbook());
			preparedStatement.setString(2, comment.getIduser());
			preparedStatement.setString(3, comment.getContent());
			preparedStatement.setFloat(4, comment.getPoint());
			preparedStatement.setString(5, comment.getTime());
			
			preparedStatement.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}