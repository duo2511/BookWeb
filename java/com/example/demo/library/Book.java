
package com.example.demo.library;

public class Book {

	public int id;
	public String title;
	public String author;
	public String description;
	public String releaseDate;
	public int numberOfPages;
	public String category;
	public int price;
	public String imageLink;

	public Book() {

	}

	public Book(int id, String title, String author, String description, String releaseDate, int numberOfPages,
			String category, int price, String imageLink) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.description = description;
		this.releaseDate = releaseDate;
		this.numberOfPages = numberOfPages;
		this.category = category;
		this.price = price;
		this.imageLink = imageLink;

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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
