
package com.example.demo.library;

import javax.validation.constraints.NotNull;

public class User {

	@NotNull
	public String username;
	@NotNull
	public String password;
	@NotNull
	public String name;
	@NotNull
	public String email;
	public String position;

	public User(@NotNull String username, @NotNull String password, @NotNull String name, @NotNull String email,
			String position) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
