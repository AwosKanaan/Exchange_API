package com.zuj.exchangeAPI.dao;

import com.zuj.exchangeAPI.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDAO extends MongoRepository<Image, String> {

	Image getImageById(String id);
}
