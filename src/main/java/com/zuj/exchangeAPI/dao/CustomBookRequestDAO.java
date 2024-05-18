package com.zuj.exchangeAPI.dao;

import com.zuj.exchangeAPI.model.BookRequest;

import java.util.Optional;

public interface CustomBookRequestDAO {
		Optional<BookRequest> findLatestByRequestedFromId(String requestedFromId);
}
