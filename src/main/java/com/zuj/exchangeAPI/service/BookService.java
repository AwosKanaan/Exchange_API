package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.dto.BookDTO;
import com.zuj.exchangeAPI.model.Book;
import com.zuj.exchangeAPI.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookService {

	List<Book> getAllBooks();
	Optional<Book> getBookByBookId(String bookId) throws Exception;
	Book createBook(BookDTO bookDTO, MultipartFile image) throws Exception;
	Book patchBook(String bookId, Map<String, Object> updates) throws Exception;
	void deleteBook(String bookId) throws Exception;
}
