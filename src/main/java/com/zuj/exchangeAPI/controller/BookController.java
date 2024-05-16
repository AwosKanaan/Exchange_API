package com.zuj.exchangeAPI.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuj.exchangeAPI.dto.BookDTO;
import com.zuj.exchangeAPI.model.Book;
import com.zuj.exchangeAPI.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
			book = bookService.getBookByBookId(bookId);
			result.put("Book", book);
		} catch (Exception e) {
			result.put("message", "book with id " + bookId + " is not found");
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping()
	public ResponseEntity<Map<String, Object>> getAllBooks() {
		Map<String, Object> result = new HashMap<>();
		List<Book> books = bookService.getAllBooks();
		result.put("Books", books);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> createBook(@RequestBody BookDTO newBook) {
		Map<String, Object> result = new HashMap<>();
		Book book;
		try {
			book = bookService.createBook(newBook);
			result.put("Book", book);
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch (Exception e) {
			result.put("Message", "Failed to create book due to " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@PutMapping("/patch/{bookId}")
	public ResponseEntity<Map<String, Object>> updateBook(@PathVariable String bookId, @RequestBody String updatedBook) {
		Book book;
		Map<String, Object> currentMap, result = new HashMap<>();
		try {
			currentMap = mapper.readValue(updatedBook, new TypeReference<>() {});
			book = bookService.patchBook(bookId, currentMap);
			result.put("updated book", book);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			result.put("Message", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@DeleteMapping("/delete/{bookId}")
	public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable String bookId) {
		Map<String, Object> result = new HashMap<>();
		try {
			bookService.deleteBook(bookId);
			result.put("Success", true);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			result.put("Message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}
	}
}
