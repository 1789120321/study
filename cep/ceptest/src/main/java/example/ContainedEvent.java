package example;

import java.util.List;

/**
 * Created by Luonanqin on 5/10/14.
 */
public class ContainedEvent {
	static class MediaOrder {
		private int orderId;
		private List<Item> items;
		private List<Book> books;

		public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
		}

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}

		public List<Book> getBooks() {
			return books;
		}

		public void setBooks(List<Book> books) {
			this.books = books;
		}

		public String toString() {
			return "MediaOrder{" + "orderId=" + orderId + ", items=" + items + ", books=" + books + '}';
		}
	}

	static class Item {
		private int itemId;
		private int productId;
		private int amount;
		private double price;

		public int getItemId() {
			return itemId;
		}

		public void setItemId(int itemId) {
			this.itemId = itemId;
		}

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public String toString() {
			return "Item{" + "itemId=" + itemId + ", productId=" + productId + ", amount=" + amount + ", price=" + price + '}';
		}
	}

	static class Book {
		private int bookId;
		private String author;
		private Review review;

		public int getBookId() {
			return bookId;
		}

		public void setBookId(int bookId) {
			this.bookId = bookId;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public Review getReview() {
			return review;
		}

		public void setReview(Review review) {
			this.review = review;
		}

		public String toString() {
			return "Book{" + "bookId=" + bookId + ", author='" + author + '\'' + ", review=" + review + '}';
		}
	}

	static class Review {
		private int reviewId;
		private String comment;

		public int getReviewId() {
			return reviewId;
		}

		public void setReviewId(int reviewId) {
			this.reviewId = reviewId;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String toString() {
			return "Review{" + "reviewId=" + reviewId + ", comment='" + comment + '\'' + '}';
		}
	}
}
