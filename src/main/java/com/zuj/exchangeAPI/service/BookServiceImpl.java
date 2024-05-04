package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.dao.BookDAO;
import com.zuj.exchangeAPI.model.Book;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

	private final BookDAO bookDAO;

	public BookServiceImpl(final BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	@Override
	public List<Book> getAllBooks() {
		return bookDAO.findAll();
	}

	@Override
	public Optional<Book> getBookById(String bookId) throws Exception {
		Optional<Book> booking = bookDAO.findById(bookId);
		if (booking.isPresent()) {
			return booking;
		} else {
			throw new Exception("Book with id: " + bookId + " is not found.");
		}
	}

	@Override
	public Book createBook(Book book) {
		return bookDAO.save(book);
	}

	public Book patchBook(String bookId, Map<String, Object> updates) {
		return bookDAO.findById(bookId)
				.map(book -> {
					updates.forEach((key, value) -> {
						Field field = null;
						try {
							field = Book.class.getDeclaredField(key);
							field.setAccessible(true);
							field.set(book, value);
						} catch (NoSuchFieldException | IllegalAccessException e) {
							assert field != null;
							throw new RuntimeException(field.getName() + " does not exist.", e);
						}
					});
					return bookDAO.save(book);
				})
				.orElseThrow(() -> new RuntimeException("Book with id " + bookId + " is not found"));
	}

	@Override
	public void deleteBook(String bookId) throws Exception {
		Optional<Book> booking = bookDAO.findById(bookId);
		if (booking.isPresent()) {
			bookDAO.deleteById(bookId);
		} else {
			throw new Exception("Book with id : " + bookId + " is not found.");
		}
	}
}
