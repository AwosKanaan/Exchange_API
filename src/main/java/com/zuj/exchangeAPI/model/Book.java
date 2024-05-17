package com.zuj.exchangeAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

	@Id
	private String id;
	@Transient
	public static final String SEQUENCE_NAME = "books_sequence";
	private String bookId;
	private String title;
	private String description;
	private BookCondition condition;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	@DBRef
	private List<Image> encodedImages;
}
