package com.zuj.exchangeAPI.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class ExchangeRequest {

	@Id
	private final String id;
	private final String exchangeId;
	private final String userId;
	private final String receiverId;
	private final String requestedBookId;
}
