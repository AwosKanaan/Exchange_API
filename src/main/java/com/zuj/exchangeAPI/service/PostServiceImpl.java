package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.dao.PostDAO;
import com.zuj.exchangeAPI.model.Post;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PostServiceImpl implements PostService {

	private final PostDAO postDAO;

	public PostServiceImpl(final PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	@Override
	public List<Post> getAllPosts() {
		return postDAO.findAll();
	}

	@Override
	public Optional<Post> getPostById(String postId) throws Exception {
		Optional<Post> post = postDAO.findById(postId);
		if (post.isPresent()) {
			return post;
		} else {
			throw new Exception("Post with id: " + postId + " is not found.");
		}
	}

	@Override
	public Post createPost(Post post) {
		return postDAO.save(post);
	}

	@Override
	public Post patchPost(String postId, Map<String, Object> updates) {
		return postDAO.findById(postId)
				.map(post -> {
					updates.forEach((key, value) -> {
						Field field = null;
						try {
							field = Post.class.getDeclaredField(key);
							field.setAccessible(true);
							field.set(post, value);
						} catch (NoSuchFieldException | IllegalAccessException e) {
							assert field != null;
							throw new RuntimeException(field.getName() + " does not exist.", e);
						}
					});
					return postDAO.save(post);
				})
				.orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
	}

	@Override
	public ResponseEntity<Map<String, Object>> deletePost(String postId) {
		Map<String, Object> result = new HashMap<>();
		Optional<Post> post = postDAO.findById(postId);
		if (post.isPresent()) {
			postDAO.deleteById(postId);
			result.put("Success", true);
		} else {
			result.put("Success", false);
		}
		return ResponseEntity.ok(result);
	}
}
