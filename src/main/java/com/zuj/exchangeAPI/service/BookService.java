package com.zuj.exchangeAPI.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zuj.exchangeAPI.dto.BookDTO;
import com.zuj.exchangeAPI.model.Book;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface BookService {

	List<Book> getAllBooks();
	Optional<Book> getBookByBookId(String bookId) throws Exception;
	Book createBook(BookDTO bookDTO) throws Exception;
	Book patchBook(String bookId, Map<String, Object> updates) throws Exception;
	void deleteBook(String bookId) throws Exception;
}
