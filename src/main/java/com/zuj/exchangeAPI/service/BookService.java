package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.model.Book;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface BookService {

	List<Book> getAllBooks();
	Optional<Book> getBookById(String bookId) throws Exception;
	Book createBook(Book book);
	Book patchBook(String bookId, Map<String, Object> updates);
	void deleteBook(String bookId) throws Exception;
}
