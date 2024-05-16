package com.zuj.exchangeAPI.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuj.exchangeAPI.dto.PostDTO;
import com.zuj.exchangeAPI.dto.UserDTO;
import com.zuj.exchangeAPI.model.Post;
import com.zuj.exchangeAPI.model.User;
import com.zuj.exchangeAPI.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final ObjectMapper mapper;

	public UserController(final UserService userService, final ObjectMapper mapper) {
		this.userService = userService;
		this.mapper = mapper;
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Map<String, Object>> getUserById(@PathVariable String userId) {
		Map<String, Object> result = new HashMap<>();
		Optional<User> user;
		try {
			user = userService.getUserByUserId(userId);
			result.put("User", user);
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch (Exception e) {
			result.put("message", "user with id " + userId + " is not found");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@GetMapping()
	public ResponseEntity<Map<String, Object>> getAllUsers() {
		Map<String, Object> result = new HashMap<>();
		List<User> users = userService.getAllUsers();
		result.put("Users", users);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDTO newUser) {
		Map<String, Object> result = new HashMap<>();
		User user;
		try {
			user = userService.createUser(newUser);
			result.put("User", user);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			result.put("Message", "Failed to create user due to " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@PutMapping("/patch/{userId}")
	public ResponseEntity<Map<String, Object>> updateUser(@PathVariable String userId, @RequestBody String updatedUser) {
		User user;
		Map<String, Object> currentMap, result = new HashMap<>();
		try {
			currentMap = mapper.readValue(updatedUser, new TypeReference<>() {});
			user = userService.patchUser(userId, currentMap);
			result.put("updated user", user);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			result.put("Message", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String userId) {
		Map<String, Object> result = new HashMap<>();
		try {
			userService.deleteUser(userId);
			result.put("Deleted user with id", userId);
		} catch (Exception e) {
			result.put("Message", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}
}