package com.zuj.exchangeAPI.controller;

import com.zuj.exchangeAPI.model.BookRequest;
import com.zuj.exchangeAPI.service.BookRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bookRequests")
public class BookRequestController {

	private final BookRequestService bookRequestService;

	public BookRequestController(final BookRequestService bookRequestService) {
		this.bookRequestService = bookRequestService;
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> createBookRequest(@RequestBody BookRequest bookRequest) {
		Map<String, Object> result = new HashMap<>();
		try {
			BookRequest savedBookRequest = bookRequestService.createBookRequest(bookRequest);
			result.put("BookRequest", savedBookRequest);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			result.put("Message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}
	}

	@PatchMapping("/update/{bookRequestId}")
	public BookRequest patchBookRequest(
			@PathVariable String bookRequestId,
			@RequestBody Map<String, Object> updates
	) {
		return bookRequestService.patchBookRequest(bookRequestId, updates);
	}

	@DeleteMapping("/delete/{bookRequestId}")
	public void deleteBookRequest(@PathVariable String bookRequestId) throws Exception {
		bookRequestService.deleteBookRequest(bookRequestId);
	}

//	@PostMapping("/complete-by-requester/{requesterId}")
//	public void completeRequestByRequester(@PathVariable String requesterId) throws Exception {
//		bookRequestService.completeRequestByRequester(requesterId);
//	}

	@PostMapping("/complete-by-requested-from/{requestedFromId}")
	public void completeRequestByRequestedFrom(@PathVariable String requestedFromId) throws Exception {
		bookRequestService.completeRequestByRequestedFrom(requestedFromId);
	}
}
