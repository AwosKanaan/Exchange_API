package com.zuj.exchangeAPI.dao;

import com.zuj.exchangeAPI.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends MongoRepository<User, String> {
	Optional<User> findByUserId(String userId);
	Optional<User> findByEmail(String email);
	Optional<User> findByPhoneNumber(String phoneNumber);
	void deleteByUserId(String userId);
}
