package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.model.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostService {

	List<Post> getAllPosts();
	Optional<Post> getPostById(String postId) throws Exception;
	Post createPost(Post post);
	Post patchPost(String postId, Map<String, Object> updates);
	ResponseEntity<Map<String, Object>> deletePost(String postId);
}
