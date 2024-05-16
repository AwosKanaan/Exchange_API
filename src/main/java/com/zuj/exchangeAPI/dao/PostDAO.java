package com.zuj.exchangeAPI.dao;

import com.zuj.exchangeAPI.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostDAO extends MongoRepository<Post, String> {

	Optional<Post> findByPostId(String postId);
	void deleteByPostId(String postId);
	List<Post> getPostsByUserId(String userId);
}
