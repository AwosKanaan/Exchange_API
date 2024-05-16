package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.dto.PostDTO;
import com.zuj.exchangeAPI.dto.UserDTO;
import com.zuj.exchangeAPI.model.Post;
import com.zuj.exchangeAPI.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

	User createUser(UserDTO userDTO) throws Exception;
	Optional<User> getUserByUserId(String userId) throws Exception;
	List<User> getAllUsers();
	User patchUser(String userId, Map<String, Object> updates);
	User updateUser(User user) throws Exception;
	void deleteUser(String userId) throws Exception;
}
