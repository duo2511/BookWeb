package com.example.demo.library;

public class Comment {
	int id;
	String idbook;
	String iduser;
	String content;
	float point;
	String time;

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(int id, String idbook, String iduser, String content, float point, String time) {
		super();
		this.id = id;
		this.idbook = idbook;
		this.iduser = iduser;
		this.content = content;
		this.point = point;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdbook() {
		return idbook;
	}

	public void setIdbook(String idbook) {
		this.idbook = idbook;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getPoint() {
		return point;
	}

	public void setPoint(float point) {
		this.point = point;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}