package com.zuj.exchangeAPI.dao;

import com.zuj.exchangeAPI.model.BookRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomBookRequestDAOImpl implements CustomBookRequestDAO {

	private final MongoTemplate mongoTemplate;


	public CustomBookRequestDAOImpl(final MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public Optional<BookRequest> findLatestByRequestedFromId(String requestedFromId) {
		Query query = new Query(Criteria.where("requestedFromId").is(requestedFromId))
				.with(Sort.by(Sort.Order.desc("createdAt")))
				.limit(1);

		return Optional.ofNullable(mongoTemplate.findOne(query, BookRequest.class));
	}
}
