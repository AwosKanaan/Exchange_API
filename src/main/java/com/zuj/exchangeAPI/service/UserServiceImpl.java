package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.dao.UserDAO;
import com.zuj.exchangeAPI.dto.UserDTO;
import com.zuj.exchangeAPI.model.User;
import com.zuj.exchangeAPI.utils.Utils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private final UserDAO userDAO;
	private final BookService bookService;
	private final SequenceGeneratorService sequenceGenerator;

	public UserServiceImpl(
			final UserDAO userDAO,
			final BookService bookService,
			final SequenceGeneratorService sequenceGenerator
	) {
		this.userDAO = userDAO;
		this.bookService = bookService;
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public User createUser(UserDTO userDTO) throws Exception {
		if (userDAO.findByEmail(userDTO.email()).isPresent()) {
			throw new Exception("email: " + userDTO.email() + " already exists");
		}
		if (userDAO.findByPhoneNumber(userDTO.phoneNumber()).isPresent()) {
			throw new Exception("phone number: " + userDTO.phoneNumber() + " already exists");
		}
		User user = Utils.convertDtoToModel(userDTO, User.class);
		assert user != null;
		user.setUserId(String.valueOf(sequenceGenerator.generateSequence(User.SEQUENCE_NAME, "userId")));
		user.setCreatedAt(LocalDateTime.now());
		return userDAO.save(user);
	}

	@Override
	public Optional<User> getUserByUserId(String userId) throws Exception {
		Optional<User> user = userDAO.findByUserId(userId);
		if (user.isPresent()) {
			return user;
		} else {
			throw new Exception("User with id: " + userId + " is not found.");
		}
	}

	@Override
	public List<User> getAllUsers() {
		return userDAO.findAll();
	}

	@Override
	public User patchUser(String userId, Map<String, Object> updates) {
		return userDAO.findByUserId(userId)
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
					user.setUpdatedAt(LocalDateTime.now());
					return userDAO.save(user);
				})
				.orElseThrow(() -> new RuntimeException("User not found with id " + userId));
	}

	@Override
	public User updateUser(User user) throws Exception {
		Optional<User> optionalUser = userDAO.findByUserId(user.getUserId());
		if (optionalUser.isEmpty()) {
			throw new Exception("User with id: " + user.getUserId() + " is not found");
		}
		return userDAO.save(user);
	}

	@Override
	public void deleteUser(String userId) throws Exception {
		Optional<User> user = userDAO.findByUserId(userId);
		if (user.isPresent()) {
			userDAO.deleteByUserId(userId);
		} else {
			throw new Exception("User with id: " + userId + " is not found");
		}
	}
}
