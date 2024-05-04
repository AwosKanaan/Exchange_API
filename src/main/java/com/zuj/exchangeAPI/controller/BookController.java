package com.zuj.exchangeAPI.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuj.exchangeAPI.model.Book;
import com.zuj.exchangeAPI.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;
	private final ObjectMapper mapper;

	public BookController(
			final BookService bookService,
			final ObjectMapper mapper
	) {
		this.bookService = bookService;
		this.mapper = mapper;
	}

	@GetMapping("/{bookId}")
	public ResponseEntity<Map<String,Object>> getBookById(@PathVariable String bookId) {
		Map<String, Object> result = new HashMap<>();
		Optional<Book> book;
		try {
			book = bookService.getBookById(bookId);
			result.put("Book", book);
		} catch (Exception e) {
			result.put("message", "book with id " + bookId + " is not found");
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("/save")
	public ResponseEntity<Book> createBooking(@RequestBody Book newBook) {
		Book book;
		try {
			book = bookService.createBook(newBook);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(book, HttpStatus.CREATED);
	}

	@PutMapping("/{bookId}")
	public ResponseEntity<Map<String, Object>> updateBook(@PathVariable String bookId, @RequestParam String updatedBook) {
		Book book;
		Map<String, Object> currenMap, result = new HashMap<>();
		try {
			currenMap = mapper.readValue(updatedBook, new TypeReference<>() {});
			book = bookService.patchBook(bookId, currenMap);
			result.put("updated book", book);
		} catch (Exception e) {
			result.put("Message", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{bookId}")
	public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
		try {
			bookService.deleteBook(bookId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
