package com.zuj.exchangeAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

enum BookCondition {
	NEW,
	LIKE_NEW,
	GOOD,
	ACCEPTABLE
}

@Document
@Data
@AllArgsConstructor
public class Book {

	@Id
	private final String id;
	private final String bookId;
	private final String title;
	private final String authorId;
	private BookCondition condition;
}
