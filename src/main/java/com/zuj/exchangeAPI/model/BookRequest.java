package com.zuj.exchangeAPI.model;

import com.zuj.exchangeAPI.model.Post;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class BookRequest {
	@Id
	private String id;
	private String bookRequestId;
	@Transient
	public static final String SEQUENCE_NAME = "requests_sequence";

	@DBRef
	private Post post;

	private String requesterId;
	private String requestedFromId; // User from whom the book is being requested

	private boolean requesterComplete;
	private boolean requestedFromComplete;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
