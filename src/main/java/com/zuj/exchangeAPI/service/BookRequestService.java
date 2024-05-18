package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.model.BookRequest;
import com.zuj.exchangeAPI.model.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookRequestService {

	void completeRequestByRequester(String requesterId) throws Exception;
	void completeRequestByRequestedFrom(String requestedFromId) throws Exception;
	List<BookRequest> getAllBookRequests();
	Optional<BookRequest> getBookRequestByBookRequestId(String bookRequestId) throws Exception;
	BookRequest createBookRequest(BookRequest exchange) throws Exception;
	BookRequest patchBookRequest(String bookRequestId, Map<String, Object> updates);
	void deleteBookRequest(String bookRequestId) throws Exception;
}
