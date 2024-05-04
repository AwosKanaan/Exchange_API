package com.zuj.exchangeAPI.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuj.exchangeAPI.model.User;
import com.zuj.exchangeAPI.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
			user = userService.getUserById(userId);
			result.put("User", user);
		} catch (Exception e) {
			result.put("message", "user with id " + userId + " is not found");
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> createUser(@RequestBody User newUser) {
		Map<String, Object> result = new HashMap<>();
		User user;
		try {
			user = userService.createUser(newUser);
			result.put("User", user);
		} catch (Exception e) {
			result.put("Message", "Failed to create user due to " + e.getMessage());
		}
		return ResponseEntity.ok(result);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<Map<String, Object>> updateUser(@PathVariable String userId, @RequestParam String updatedUser) {
		User user;
		Map<String, Object> currenMap, result = new HashMap<>();
		try {
			currenMap = mapper.readValue(updatedUser, new TypeReference<>() {
			});
			user = userService.patchUser(userId, currenMap);
			result.put("updated user", user);
		} catch (Exception e) {
			result.put("Message", e.getMessage());
		}
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{userId}")
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