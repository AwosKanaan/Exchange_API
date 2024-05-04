package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.dao.UserDAO;
import com.zuj.exchangeAPI.model.User;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private final UserDAO userDAO;

	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public User createUser(User user) {
		return userDAO.save(user);
	}

	@Override
	public Optional<User> getUserById(String userId) throws Exception {
		Optional<User> user = userDAO.findById(userId);
		if (user.isPresent()) {
			return user;
		} else {
			throw new Exception("User with id: " + userId + " is not found.");
		}
	}

	@Override
	public User patchUser(String userId, Map<String, Object> updates) {
		return userDAO.findById(userId)
				.map(user -> {
					updates.forEach((key, value) -> {
						Field field = null;
						try {
							field = User.class.getDeclaredField(key);
							field.setAccessible(true);
							field.set(user, value);
						} catch (NoSuchFieldException | IllegalAccessException e) {
							assert field != null;
							throw new RuntimeException(field.getName() + " does not exist.", e);
						}
					});
					return userDAO.save(user);
				})
				.orElseThrow(() -> new RuntimeException("User not found with id " + userId));
	}

	@Override
	public void deleteUser(String userId) {
		userDAO.deleteById(userId);
	}
}
