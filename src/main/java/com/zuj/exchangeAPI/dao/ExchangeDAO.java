package com.zuj.exchangeAPI.dao;

import com.zuj.exchangeAPI.model.ExchangeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeDAO extends MongoRepository<ExchangeRequest, String> {
}
