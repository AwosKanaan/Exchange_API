package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.dto.PostDTO;
import com.zuj.exchangeAPI.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostService {

	List<Post> getAllPosts();
	Optional<Post> getPostByPostId(String postId) throws Exception;
	Post createPost(PostDTO postDTO) throws Exception;
	Post patchPost(String postId, Map<String, Object> updates);
	void deletePost(String postId) throws Exception;
	List<Post> getPostsForUser(String userId) throws Exception;
}
