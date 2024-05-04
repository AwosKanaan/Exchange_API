package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {

	User createUser(User user);

	Optional<User> getUserById(String userId) throws Exception;

	User patchUser(String userId, Map<String, Object> updates);

	void deleteUser(String userId);
}
