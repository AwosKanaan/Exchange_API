package com.zuj.exchangeAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Post {

	@Id
	private String id;
	@Transient
	public static final String SEQUENCE_NAME = "posts_sequence";
	private String postId;
	private String userId;
	private String bookId;
	@Transient
	private User user;
	@Transient
	private Book book;
}
