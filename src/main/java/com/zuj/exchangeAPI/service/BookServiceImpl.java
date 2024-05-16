package com.zuj.exchangeAPI.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuj.exchangeAPI.dao.BookDAO;
import com.zuj.exchangeAPI.dto.BookDTO;
import com.zuj.exchangeAPI.model.Book;
import com.zuj.exchangeAPI.model.BookCondition;
import com.zuj.exchangeAPI.utils.Utils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

	private final BookDAO bookDAO;
	private final SequenceGeneratorService sequenceGenerator;
	private final ObjectMapper mapper;

	public BookServiceImpl(
			final BookDAO bookDAO,
			final ObjectMapper mapper,
			final SequenceGeneratorService sequenceGenerator
	) {
		this.bookDAO = bookDAO;
		this.sequenceGenerator = sequenceGenerator;
		this.mapper = mapper;
	}

	@Override
	public List<Book> getAllBooks() {
		return bookDAO.findAll();
	}

	@Override
	public Optional<Book> getBookByBookId(String bookId) throws Exception {
		Optional<Book> booking = bookDAO.findByBookId(bookId);
		if (booking.isPresent()) {
			return booking;
		} else {
			throw new Exception("Book with id: " + bookId + " is not found.");
		}
	}

	@Override
	public Book createBook(BookDTO bookDTO) {
		Book book = Utils.convertDtoToModel(bookDTO, Book.class);
		assert book != null;
		book.setBookId(String.valueOf(sequenceGenerator.generateSequence(Book.SEQUENCE_NAME, "bookId")));
		return bookDAO.save(book);
	}

	public Book patchBook(String bookId, Map<String, Object> updates) throws Exception {
		if (updates.isEmpty()) {
			throw new Exception("No updates provided");
		}
		if (updates.containsKey("condition")) {
			BookCondition condition = BookCondition.valueOf((String) updates.get("condition"));
			updates.put("condition", condition);
		}
		// add invalid condition exception in custom exception handling instead of throwing runtimeExceptions
		return bookDAO.findByBookId(bookId)
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
		Optional<Book> booking = bookDAO.findByBookId(bookId);
		if (booking.isPresent()) {
			bookDAO.deleteByBookId(bookId);
		} else {
			throw new Exception("Book with id : " + bookId + " is not found.");
		}
	}
}
