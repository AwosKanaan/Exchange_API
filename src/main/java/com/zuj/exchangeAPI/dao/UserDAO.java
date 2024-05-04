package com.zuj.exchangeAPI.dao;

import com.zuj.exchangeAPI.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends MongoRepository<User, String> {
}
