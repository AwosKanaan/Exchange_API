package com.zuj.exchangeAPI.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Exchange {

	@Id
	private String id;
	private String exchangeId;
	@DBRef
	private User requester;
	@DBRef
	private User requestedFrom;
	@DBRef
	private Book book;
	private RequestStatus status;
}
