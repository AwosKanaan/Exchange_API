package com.zuj.exchangeAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class User {

	@Id
	private final String id;
	private final String userId;
	private final String userName;
	private final String email;
	private float rating;
	private String phoneNumber;
}
