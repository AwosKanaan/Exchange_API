package com.zuj.exchangeAPI.dao;

import com.zuj.exchangeAPI.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDAO extends MongoRepository<Post, String> {
}
