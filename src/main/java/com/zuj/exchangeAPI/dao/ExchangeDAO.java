package com.zuj.exchangeAPI.dao;

import com.zuj.exchangeAPI.model.Exchange;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExchangeDAO extends MongoRepository<Exchange, String> {
}
