package com.zuj.exchangeAPI.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuj.exchangeAPI.dto.PostDTO;
import com.zuj.exchangeAPI.model.Post;
import com.zuj.exchangeAPI.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;
	private final ObjectMapper mapper;

	public PostController(
			final PostService postService,
			final ObjectMapper mapper
	) {
		this.postService = postService;
		this.mapper = mapper;
	}

	@GetMapping("/{postId}")
	public ResponseEntity<Map<String,Object>> getPostById(@PathVariable String postId) {
		Map<String, Object> result = new HashMap<>();
		Optional<Post> post;
		try {
			post = postService.getPostByPostId(postId);
			result.put("Post", post);
		} catch (Exception e) {
			result.put("message", "post with id " + postId + " is not found");
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping()
	public ResponseEntity<Map<String, Object>> getAllPosts() {
		Map<String, Object> result = new HashMap<>();
		List<Post> posts = postService.getAllPosts();
		result.put("Posts", posts);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> createPost(@RequestBody PostDTO newPost) {
		Map<String, Object> result = new HashMap<>();
		Post post;
		try {
			post = postService.createPost(newPost);
			result.put("Post", post);
			return ResponseEntity.status(HttpStatus.CREATED).body(result);
		} catch (Exception e) {
			result.put("Message", "Failed to create post due to " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@PutMapping("/patch/{postId}")
	public ResponseEntity<Map<String, Object>> updatePost(@PathVariable String postId, @RequestBody String updatedPost) {
		Post post;
		Map<String, Object> currentMap, result = new HashMap<>();
		try {
			currentMap = mapper.readValue(updatedPost, new TypeReference<>() {});
			post = postService.patchPost(postId, currentMap);
			result.put("updated post", post);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			result.put("Message", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<Map<String, Object>> deletePost(@PathVariable String postId) {
		Map<String, Object> result = new HashMap<>();
		try {
			postService.deletePost(postId);
			result.put("Success", true);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			result.put("Message", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Map<String, Object>> getPostsForUser(@PathVariable String userId) {
		Map<String, Object> result = new HashMap<>();
		try {
			List<Post> posts = postService.getPostsForUser(userId);
			result.put("Posts", posts);
			return ResponseEntity.ok(result);
		} catch (Exception e ) {
			result.put("Message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}
	}
}
