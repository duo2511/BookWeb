package com.example.demo.library;


public class Order {
    
	int id;
	String title;
	String orderuser;
	int amount;
	String orderTime;
	
	



	
	public Order(int id, String title, String orderuser, int amount, String orderTime) {
		super();
		this.id = id;
		this.title = title;
		this.orderuser = orderuser;
		this.amount = amount;
		this.orderTime = orderTime;
	}

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOrderuser() {
		return orderuser;
	}
	public void setOrderuser(String orderuser) {
		this.orderuser = orderuser;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
	
}