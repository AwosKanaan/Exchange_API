package com.zuj.exchangeAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@NoArgsConstructor(force = true)
public class User {

	@Id
	private String id;
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	private String userId;
	private String userName;
	private String email;
	private double rating;
	private String phoneNumber;
	@DBRef
	private List<Post> posts;
	@DBRef
	private List<Book> requestedBooks;
}
