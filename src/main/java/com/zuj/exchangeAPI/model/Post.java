package com.zuj.exchangeAPI.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Post {

	@Id
	private final String id;
	private final String postId;
	private final String userId;
	private final String BookId;
}
