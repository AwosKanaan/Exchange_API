package com.zuj.exchangeAPI.dao;

import com.zuj.exchangeAPI.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDAO extends MongoRepository<Book, String> {
}
