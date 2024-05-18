package com.zuj.exchangeAPI.dao;

import com.zuj.exchangeAPI.model.BookRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRequestDAO extends MongoRepository<BookRequest, String> {
	List<BookRequest> findAllByPostId(String postId);
	Optional<BookRequest> findBookRequestByRequesterId(String requesterId);
	Optional<BookRequest> findLatestByRequestedFromId(String requestedFromId);
	void deleteBookRequestByBookRequestId(String bookRequestId);
}
